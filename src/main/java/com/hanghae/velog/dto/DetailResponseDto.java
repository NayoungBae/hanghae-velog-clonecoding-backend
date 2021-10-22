package com.hanghae.velog.dto;

import com.hanghae.velog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String filePath;
    private String imageUrl;
    private String dayBefore;
    private List<CommentResponseDto> commentList;
}
