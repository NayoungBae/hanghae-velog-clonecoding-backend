package com.hanghae.velog.model;

import com.hanghae.velog.dto.PostingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Posting extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long postingId; // 게시글 고유 번호

    @Column(nullable = false)
    private String userName; // 게시글 작성자의 닉네임, 중복 허용X

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용
//
//    @Column(nullable = false)
//    private String contentTag;

//    @Column(nullable = false)
//    private Long dayBefore; // 게시글 등록 날짜(계산)

//    @Column(nullable = false)
//    private Long commentCnt; // 게시글에 등록된 댓글 갯수

    @Column(nullable = false)
    private String filePath; // 메인페이지에서 보여질 게시글의 썸네일 이미지(유저가 게시글에 등록한 이미지)

//    @Column(nullable = false)
//    private String userImage; // 게시글 작성자의 프로필 사진
    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        return;
    }
    // 메인페이지 게시글 전체 조회 생성자
    public Posting(PostingRequestDto requestDto, String userName) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
//        this.contentTag = requestDto.getContentTag();
        this.filePath = requestDto.getFilePath();
        this.userName = userName;
    }

    //게시글 수정
    public void updatePosting(PostingRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
//        this.contentTag = requestDto.getContentTag();
        this.filePath = requestDto.getFilePath();
        this.imageUrl = requestDto.getImageUrl(); // 추가됨
    }
    // 개별 게시글 상세조회 페이지 생성자


}
