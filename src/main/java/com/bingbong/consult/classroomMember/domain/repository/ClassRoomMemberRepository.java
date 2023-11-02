package com.bingbong.consult.classroomMember.domain.repository;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroomMember.domain.ClassRoomMember;
import com.bingbong.consult.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomMemberRepository extends JpaRepository<ClassRoomMember, Long> {
    List<ClassRoomMember> findByMemberId(Long memberId);
    List<ClassRoomMember> findByClassRoomId(Long classId);
    ClassRoomMember findByMemberAndClassRoom(Member member, ClassRoom classRoom);
    List<ClassRoomMember> findAllByMember(Member member);
    List<ClassRoomMember> findAllByClassRoomId(Long classId);

    @Query("select c from ClassRoomMember c where c.classRoom = :classRoom and c.member.role = 'parent'")
    List<ClassRoomMember> findAllByClassRoomAndParent(ClassRoom classRoom);
}
