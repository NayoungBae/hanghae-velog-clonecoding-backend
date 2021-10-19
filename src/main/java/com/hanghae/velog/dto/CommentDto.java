package com.hanghae.velog.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String content;
    private String username;
    private Long postId;
}
