package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostingResponseDto {
    private Long postingId;
    private String userName;
    private String title;
    private String content;
    private String imageFile;
    private String dayBefore;
    private int commentCnt;
}

//postingId": "1",
//        "imageFile": "/images/basic.jpg",
//        "title": "제목",
//        "content": "내용",
//        "dayBefore": "7일 전",
//        "commentCnt": "2개",
//        "userImage": "/image/basic.jpg",
//        "userName": "정영호"