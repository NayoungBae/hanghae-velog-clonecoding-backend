package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostingResponseDto {
    private Long postingId;     //게시글 번호
    private String userName;    //닉네임
    private String title;       //제목
    private String content;     //내용
    private String imageFile;   //게시글 이미지
    private String imageUrl;
    private String dayBefore;   //n일 전
    private int commentCnt;     //댓글 개수

    //private userImage;          //사용자 이미지
}

//postingId": "1",
//        "imageFile": "/images/basic.jpg",
//        "title": "제목",
//        "content": "내용",
//        "dayBefore": "7일 전",
//        "commentCnt": "2개",
//        "userImage": "/image/basic.jpg",
//        "userName": "정영호"