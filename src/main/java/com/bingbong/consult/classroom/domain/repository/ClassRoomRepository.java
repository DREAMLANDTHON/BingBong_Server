package com.bingbong.consult.classroom.domain.repository;

import com.bingbong.consult.classroom.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    Optional<ClassRoom> findByGroupCode(String groupCode);
}