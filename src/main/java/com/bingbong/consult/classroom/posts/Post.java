package com.bingbong.consult.classroom.posts;

import com.bingbong.consult.classroom.domain.ClassRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    @CreationTimestamp
    private Timestamp postTime;

    @ManyToOne
    @JoinColumn(name = "class_room_id")
    private ClassRoom classRoom;
}
