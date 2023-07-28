package com.bingbong.consult.classroomMember.domain;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;

    public static ClassRoomMember from(Member member, ClassRoom classRoom) {
        return ClassRoomMember.builder()
                .member(member)
                .classRoom(classRoom)
                .build();
    }

}
