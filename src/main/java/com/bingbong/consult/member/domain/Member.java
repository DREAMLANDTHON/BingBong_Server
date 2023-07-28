package com.bingbong.consult.member.domain;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroomMember.domain.ClassRoomMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, name="member_id")
    private Long id;
    private String name;
    private String email;
    private String childName;
    private String kakaoKey;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn( = "class_room_id")
//    private List<ClassRoomMember> classRoomMember;
}
