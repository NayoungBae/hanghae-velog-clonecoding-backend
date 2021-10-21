package com.hanghae.velog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostingRequestDto {
    private String title; // 제목
    private String content; // 내용
    private String contentTag;
    private String imageFile; // 게시물 내에 등록할 이미지
    private String imageUrl; // 추가
}
