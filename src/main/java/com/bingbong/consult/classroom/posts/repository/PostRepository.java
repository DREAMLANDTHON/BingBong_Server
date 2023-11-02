package com.bingbong.consult.classroom.posts.repository;

import com.bingbong.consult.classroom.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

