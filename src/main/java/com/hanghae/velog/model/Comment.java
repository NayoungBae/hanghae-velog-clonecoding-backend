package com.hanghae.velog.model;

import com.hanghae.velog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long postingId;


    @ManyToOne(fetch = FetchType.LAZY,cascade =CascadeType.ALL )
    @JoinColumn(name = "postId")
    private Posting post;

    public Comment(CommentRequestDto reqDto, String userName) {
        this.userName = userName;
        this.comment = reqDto.getComment();
        this.postingId = reqDto.getPostingId();
    }

    public Comment(CommentRequestDto reqDto,Posting post) {
        this.userName = reqDto.getUserName();
        this.comment = reqDto.getComment();
        this.postingId = reqDto.getPostingId();
        this.post = post;
    }

    public Comment(CommentRequestDto reqDto) {
        this.userName = reqDto.getUserName();
        this.comment = reqDto.getComment();
        this.postingId = reqDto.getPostingId();
    }

    public void update(CommentRequestDto reqDto) {
        this.comment = reqDto.getComment();
    }
}
