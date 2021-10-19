package com.hanghae.velog.repository;

import com.hanghae.velog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostingIdOrderByCreatedAtDesc(Long postingId);
}
