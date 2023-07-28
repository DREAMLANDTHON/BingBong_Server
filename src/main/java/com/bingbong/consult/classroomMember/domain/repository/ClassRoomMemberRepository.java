package com.bingbong.consult.classroomMember.domain.repository;

import com.bingbong.consult.classroomMember.domain.ClassRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomMemberRepository extends JpaRepository<ClassRoomMember, Long> {
}
