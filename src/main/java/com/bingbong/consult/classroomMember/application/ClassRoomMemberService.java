package com.bingbong.consult.classroomMember.application;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import com.bingbong.consult.classroomMember.domain.ClassRoomMember;
import com.bingbong.consult.classroomMember.domain.repository.ClassRoomMemberRepository;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassRoomMemberService {
    private final ClassRoomMemberRepository classRoomMemberRepository;
    private final ClassRoomRepository classRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepo chatRoomRepo;

    public Long joinClass(String code, Long memberId) {
        Optional<ClassRoom> classByGroupCode = classRoomRepository.findByGroupCode(code);
        Optional<Member> memberById = memberRepository.findById(memberId);

        if(classByGroupCode.isPresent() && memberById.isPresent()){
            ClassRoomMember duplicateJoin = classRoomMemberRepository.findByMemberAndClassRoom(memberById.get(), classByGroupCode.get());
            if(duplicateJoin != null) throw new RuntimeException("이미 가입한 반입니다.");
            ClassRoomMember classRoomMember = ClassRoomMember.from(memberById.get(), classByGroupCode.get());
            classRoomMemberRepository.save(classRoomMember);

            Optional<ClassRoomMember> saved = classRoomMemberRepository.findById(classRoomMember.getId());
            if(saved.isPresent()){
                ClassRoom classroom = saved.get().getClassRoom();
                if(saved.get().getMember().getRole() != "teacher") {
                    Member parent = saved.get().getMember();
                    chatRoomRepo.save(ChatRoom.from(classroom, parent));
                }
                return saved.get().getId();
            }
            else throw new RuntimeException("저장 실패");
        }
        else throw new RuntimeException("저장 실패 - 존재하지 않는 그룹코드 또는 멤버");

    }

    public List<ClassRoomResponse> findMyClassRoom(Long memberId) {
        List<ClassRoomMember> byMemberId = classRoomMemberRepository.findByMemberId(memberId);
        List<ClassRoomResponse> classRooms = new ArrayList<>();
        for (ClassRoomMember classRoomMember : byMemberId) {
            ClassRoom classRoom = classRoomMember.getClassRoom();
            Member teacher = classRoom.getTeacher();
            classRooms.add(ClassRoomResponse.builder()
                            .id(classRoom.getId())
                            .classRoomName(classRoom.getClassRoomName())
                            .description(classRoom.getDescription())
                            .year(classRoom.getYear())
                            .teacher(teacher.getName())
                            .build());
        }
        return classRooms;
    }

    public List<MemberDto> findClassRoomMembers(Long classId) {
        List<ClassRoomMember> byClassId = classRoomMemberRepository.findByClassRoomId(classId);
        List<MemberDto> members = new ArrayList<>();
        for (ClassRoomMember classRoomMember : byClassId) {
            Member member = classRoomMember.getMember();
            members.add(MemberDto.builder()
                            .name(member.getName())
                            .email(member.getEmail())
                            .childName(member.getChildName())
                            .role(member.getRole())
                            .build());
        }
        return members;
    }

    public List<MemberDto> findClassRoomMembersByClassRoomId(Long classRoomId) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ClassRoom입니다."));
        List<ClassRoomMember> allByClassRoom = classRoomMemberRepository.findAllByClassRoomAndParent(classRoom);
        List<MemberDto> members = new ArrayList<>();
        for (ClassRoomMember classRoomMember : allByClassRoom) {
            Member member = classRoomMember.getMember();
            members.add(MemberDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .childName(member.getChildName())
                    .role(member.getRole())
                    .build());
        }
        return members;
    }
    public ResponseEntity<?> deleteMemberInClassRoom(String code, Long memberId){
        Optional<ClassRoom> classByGroupCode = classRoomRepository.findByGroupCode(code);
        Optional<Member> memberById = memberRepository.findById(memberId);
        if(!memberById.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        ClassRoomMember classRoomMember = classRoomMemberRepository.getReferenceById(memberId);
        classRoomMemberRepository.delete(classRoomMember);
        return null;
    }

}
