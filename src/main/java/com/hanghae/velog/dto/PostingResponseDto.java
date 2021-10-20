package com.hanghae.velog.dto;

import com.hanghae.velog.model.Posting;

import java.util.List;

public class PostingResponseDto {
    private Long postingId;
    private String userName;
    private String title;
    private String content;
    private String imageFile;
    private String dayBefore;
}

//postingId": "1",
//        "imageFile": "/images/basic.jpg",
//        "title": "제목",
//        "content": "내용",
//        "dayBefore": "7일 전",
//        "commentCnt": "2개",
//        "userImage": "/image/basic.jpg",
//        "userName": "정영호"