package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyPostingResponseDto {
    private Long postingId;     //게시글 번호
    private String thumbNail;   //게시글 이미지
    private String title;       //제목
    private String content;     //내용
    private String dayBefore;   //n일 전
    private int commentCnt;     //댓글 개수
    //private userImage;          //사용자 이미지
    private String userName;    //닉네임
}
