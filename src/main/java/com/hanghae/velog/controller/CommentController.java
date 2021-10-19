package com.hanghae.velog.controller;


import com.hanghae.velog.dto.CommentDto;
import com.hanghae.velog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;


    //토큰 받아와서 유저정보추가하기
    @PostMapping("/api/comment")
    public String createComment(
            @RequestBody CommentDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        CommentService.createComment(requestDto,userDetails);
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
