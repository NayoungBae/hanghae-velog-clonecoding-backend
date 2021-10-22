package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long postingId;                     //게시물 ID
    private Long commentId;                     //댓글 ID
    private String userName;                    //댓글 작성자 닉네임
    private String comment;                     //댓글 내용
//    private Long userIndex;                     //....??
    private LocalDateTime createdAtComment;    //댓글 생성일
    private LocalDateTime modifiedAtComment;   //댓글 수정일
}
