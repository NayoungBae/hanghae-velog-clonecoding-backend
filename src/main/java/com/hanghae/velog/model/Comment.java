package com.hanghae.velog.model;

import com.hanghae.velog.dto.CommentDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Comment {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String userNick;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long postId;

    public Comment(CommentDto reqDto) {
        this.userNick = reqDto.getUserNick();
        this.content = reqDto.getContent();
        this.postId = reqDto.getPostId();
    }

    public Comment(CommentDto reqDto,Long userId,String content)
    {
        this.id = userId;
        this.userNick = reqDto.getUserNick();
        this.content = content;
    }
    public void update(CommentDto reqDto) {
        this.content = reqDto.getContent();
    }
}
