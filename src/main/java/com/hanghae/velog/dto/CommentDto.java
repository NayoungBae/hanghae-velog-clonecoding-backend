package com.hanghae.velog.dto;


import com.hanghae.velog.model.Posting;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    private String comment;
    private Long postingId;
    private String userName;
}
