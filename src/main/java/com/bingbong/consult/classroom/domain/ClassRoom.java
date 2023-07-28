package com.bingbong.consult.classroom.domain;

import com.bingbong.consult.member.domain.Member;
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
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String classRoomName;
    private String description;
    private String groupCode;
    private Integer year;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id")
    private Member parent;
}
