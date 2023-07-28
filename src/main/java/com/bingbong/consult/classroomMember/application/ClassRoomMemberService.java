package com.bingbong.consult.classroomMember.application;

import com.bingbong.consult.classroomMember.domain.repository.ClassRoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassRoomMemberService {
    private final ClassRoomMemberRepository classRoomMemberRepository;
}
