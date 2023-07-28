package com.bingbong.consult.member.domain;

import com.bingbong.consult.classroom.domain.ClassRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String name;
    private String email;
    private String childName;
    private String kakaoKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "class_room_id")
    private ClassRoom classRoom;
}
