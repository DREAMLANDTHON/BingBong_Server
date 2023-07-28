package com.bingbong.consult.classroom.application;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.classroom.presentation.request.ClassRoomRequest;
import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    private final MemberRepository memberRepository;

    public Long create(ClassRoomRequest form) {
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
}
