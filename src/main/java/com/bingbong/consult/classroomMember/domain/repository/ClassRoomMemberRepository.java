package com.bingbong.consult.classroomMember.domain.repository;

import com.bingbong.consult.classroomMember.domain.ClassRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomMemberRepository extends JpaRepository<ClassRoomMember, Long> {
    List<ClassRoomMember> findByMemberId(Long memberId);
    List<ClassRoomMember> findByClassRoomId(Long classId);
}
