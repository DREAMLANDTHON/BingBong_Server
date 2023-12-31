package com.bingbong.consult.classroom.application;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.classroom.posts.Post;
import com.bingbong.consult.classroom.posts.repository.PostRepository;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.classroom.presentation.request.PostRequest;
import com.bingbong.consult.classroom.presentation.response.ClassRoomResponse;
import com.bingbong.consult.classroomMember.domain.repository.ClassRoomMemberRepository;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    private final MemberRepository memberRepository;
    private final ClassRoomMemberRepository classRoomMemberRepository;
    private final PostRepository postRepository;

    @Transactional
    public ClassRoom findById(Long classId) {
        return classRoomRepository.findById(classId).get();
    }

    public String create(ClassRoomRequest form) {
        String groupCode = form.getGroupCode();
        if (groupCode == null) {
            groupCode = ""; // null 대신 빈 문자열로 설정
        }
        while(true) {
            if (groupCode.isEmpty()) {
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                for (int i = 0; i < 10; i++) {
                    double randomIndex = Math.floor(Math.random() * characters.length());
                    groupCode += characters.charAt((int) randomIndex);
                }
            }
            else if(!duplicateClassRoomCheck(groupCode)) {
                break;
            }
        }

        Member teacher = memberRepository.findById(form.getTeacherId()).orElseThrow(() -> new RuntimeException("존재하지 않는 선생님입니다."));

        ClassRoom classRoom = ClassRoom.builder()
                .classRoomName(form.getClassRoomName())
                .description(form.getDescription())
                .groupCode(groupCode)
                .year(form.getYear())
                .teacher(teacher)
                .build();
        classRoomRepository.save(classRoom);
        Optional<ClassRoom> foundClassRoom = classRoomRepository.findById(classRoom.getId());
        foundClassRoom.orElseThrow(() -> new RuntimeException("반 생성 실패"));

        return foundClassRoom.get().getGroupCode();
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
                    .id(classRoom.getId())
                    .classRoomName(classRoom.getClassRoomName())
                    .description(classRoom.getDescription())
                    .year(classRoom.getYear())
                    .groupCode(classRoom.getGroupCode())
                    .teacher(classRoom.getTeacher().getName())
                    .build();
        }
        else throw new RuntimeException("존재하지 않는 반입니다.");

    }

    public List<ClassRoomResponse> findClassRoomByTeacherId(Long teacherId) {
        Optional<Member> teacher = memberRepository.findById(teacherId);
        teacher.orElseThrow(() -> new RuntimeException("존재하지 않는 선생님입니다."));
        List<ClassRoom> allByTeacher = classRoomRepository.findAllByTeacher(teacher.get());
        return allByTeacher.stream().map(classRoom -> ClassRoomResponse.builder()
                .id(classRoom.getId())
                .classRoomName(classRoom.getClassRoomName())
                .description(classRoom.getDescription())
                .year(classRoom.getYear())
                .teacher(classRoom.getTeacher().getName())
                .groupCode(classRoom.getGroupCode())
                .build()).collect(Collectors.toList());
    }

    public List<ClassRoomResponse> findClassRoomByParentId(Long parentId) {
        Optional<Member> parent = memberRepository.findById(parentId);
        parent.orElseThrow(() -> new RuntimeException("존재하지 않는 학부모입니다."));
        return classRoomMemberRepository.findAllByMember(parent.get())
                .stream().map(classRoomMember -> {
                    ClassRoom classRoom = classRoomMember.getClassRoom();
                    return ClassRoomResponse.builder()
                            .id(classRoom.getId())
                            .classRoomName(classRoom.getClassRoomName())
                            .description(classRoom.getDescription())
                            .year(classRoom.getYear())
                            .teacher(classRoom.getTeacher().getName())
                            .groupCode(classRoom.getGroupCode())
                            .build();

                })
                .collect(Collectors.toList());
    }

    public ClassRoomResponse findClassRoomByGroupCode(String groupCode) {
        Optional<ClassRoom> byGroupCode = classRoomRepository.findByGroupCode(groupCode);
        byGroupCode.orElseThrow(() -> new RuntimeException("존재하지 않는 반입니다."));
        return ClassRoomResponse.builder()
                .id(byGroupCode.get().getId())
                .classRoomName(byGroupCode.get().getClassRoomName())
                .description(byGroupCode.get().getDescription())
                .year(byGroupCode.get().getYear())
                .teacher(byGroupCode.get().getTeacher().getName())
                .groupCode(byGroupCode.get().getGroupCode())
                .build();
    }


    public List<Post> getClassRoomPost(Long classRoomId) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(classRoomId);
        classRoom.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 classRoom 입니다"));
//        classRoom 가져와서 해당 classRoom의 post를 가져와야 한다
        List<Post> posts = classRoom.get().getPosts();
        return posts.stream().map(post1 -> Post.builder().
                id(post1.getId()).
                title(post1.getTitle()).
                content(post1.getContent()).
                postTime(post1.getPostTime()).build()).collect(Collectors.toList());
    }
    @Transactional
    public Post addClassPost(Long classRoomId, PostRequest postRequest) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 classRoom입니다!!"));
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .classRoom(classRoom)
                .build();

        post = postRepository.save(post);
        classRoom.getPosts().add(post);
        return post;
    }
}
