package com.hanghae.velog.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    private String comment;
    private Long postingId;
    private String userName;
}
