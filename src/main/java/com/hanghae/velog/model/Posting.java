package com.hanghae.velog.model;

import com.hanghae.velog.dto.PostingRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Posting {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long postingId; // 게시글 고유 번호

    @Column(nullable = false)
    private String userName; // 게시글 작성자의 닉네임, 중복 허용X

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String content; // 게시글 내용

    @Column(nullable = false)
    private String contentTag; // 게시글 단어 태그

    @Column(nullable = false)
    private Long dayBefore; // 게시글 등록 날짜(계산) : 7일 전까지 '며칠 전', 7일 후부터는 생성날짜로 적기

    @Column(nullable = false)
    private Long commentCnt; // 게시글에 등록된 댓글 갯수

    @Column(nullable = false)
    private String imageFile; // 메인페이지에서 보여질 게시글의 썸네일 이미지(유저가 게시글에 등록한 이미지)

//    @Column(nullable = false)
//    private String userImage; // 게시글 작성자의 프로필 사진

    @Column(nullable = false)
    @OneToMany
    @JoinColumn(name = "commentList")
    private List<Comment> comments;

    // 메인페이지 게시글 전체 조회 생성자
    public Posting(PostingRequestDto requestDto, String userName) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.contentTag = requestDto.getContentTag();
        this.imageFile = requestDto.getImageFile();
        this.userName = userName;
    }

    // 개별 게시글 상세조회 페이지 생성자
    public Posting()


}
