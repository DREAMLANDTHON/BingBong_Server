package com.bingbong.consult.classroom.application;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    private final MemberRepository memberRepository;

    public Long create(ClassRoomRequest form) {
        if(duplicateClassRoomCheck(form.getGroupCode())) {
            throw new RuntimeException("중복된 반 코드입니다.");
        }
        Member teacher = memberRepository.findById(form.getTeacherId()).orElseThrow(() -> new RuntimeException("존재하지 않는 선생님입니다."));

        ClassRoom classRoom = ClassRoom.builder()
                .classRoomName(form.getClassRoomName())
                .description(form.getDescription())
                .groupCode(form.getGroupCode())
                .year(form.getYear())
                .teacher(teacher)
                .build();
        classRoomRepository.save(classRoom);
        Optional<ClassRoom> foundClassRoom = classRoomRepository.findById(classRoom.getId());
        foundClassRoom.orElseThrow(() -> new RuntimeException("반 생성 실패"));

        return foundClassRoom.get().getId();
    }

    private Boolean duplicateClassRoomCheck(String groupCode) {
        Optional<ClassRoom> byGroupCode = classRoomRepository.findByGroupCode(groupCode);
        return byGroupCode.isPresent();
    }

    public ClassRoomResponse findClassRoom(Long id) {
        Optional<ClassRoom> findById = classRoomRepository.findById(id);
        if(findById.isPresent()){
            ClassRoom classRoom = findById.get();
            return ClassRoomResponse.builder()
                    .classRoomName(classRoom.getClassRoomName())
                    .description(classRoom.getDescription())
                    .year(classRoom.getYear())
                    .teacher(classRoom.getTeacher().getName())
                    .build();
        }
        else throw new RuntimeException("존재하지 않는 반입니다.");

    }
}
