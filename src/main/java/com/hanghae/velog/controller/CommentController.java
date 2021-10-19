package com.hanghae.velog.controller;


import com.hanghae.velog.dto.CommentDto;
import com.hanghae.velog.model.Comment;
import com.hanghae.velog.repository.CommentRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import com.hanghae.velog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    private String msg = "success";

    @GetMapping("/api/comment/{id}")
    public List<Comment> getComment(@PathVariable Long postId)
    {
        return commentService.getComment(postId);
    }

    //토큰 받아와서 유저정보추가하기
    @PostMapping("/api/comment")
    public String createComment(
            @RequestBody CommentDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if (userDetails != null){
            return msg+"에러";
        }
        commentService.createComment(requestDto);
        return msg;
    }

    @PutMapping("/api/comment/{id}")
    public String editComment(
            @PathVariable Long id,
            @RequestBody CommentDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        commentService.editComment(id,requestDto,userDetails);
        return msg;
    }


    @DeleteMapping("/api/comment/{id}")
    public String editComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        commentService.deleteComment(id,userDetails);
        return msg;
    }

}
