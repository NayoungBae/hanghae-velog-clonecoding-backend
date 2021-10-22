package com.hanghae.velog.dbSet;

import com.hanghae.velog.dto.CommentRequestDto;
import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.dto.SignupRequestDto;
import com.hanghae.velog.service.CommentService;
import com.hanghae.velog.service.PostingService;
import com.hanghae.velog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class SetTestDb {
    private final UserService userService;
    private final PostingService postService;
    private final CommentService commentService;
    public void dbset(){
        userSet();
        postSet();
    }
    public void dbset2(){

        commentSet();
    }

    @Transactional
    public void userSet()
    {
        SignupRequestDto user = new SignupRequestDto();
        user.setUserId("bbb@bbb.com");
        user.setUserName("bbb");
        user.setPassword("abab1234");
        user.setProfileImage("/Profileimages/basic_profile");
        userService.signup(user);

        SignupRequestDto user2 = new SignupRequestDto();
        user2.setUserId("aaa@aaa.com");
        user2.setUserName("aaa");
        user2.setPassword("abab1234");
        user2.setProfileImage("/Profileimages/basic_profile");
        userService.signup(user2);
    }

    @Transactional
    public void postSet()
    {
        PostingRequestDto reqdto = new PostingRequestDto();
        reqdto.setTitle("제목1");
        reqdto.setContent("내용");
        reqdto.setContentTag("테스트");
        reqdto.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto,"bbb");

        PostingRequestDto reqdto2 = new PostingRequestDto();
        reqdto2.setTitle("제목2");
        reqdto2.setContent("내용");
        reqdto2.setContentTag("테스트");
        reqdto2.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto2,"bbb");
        PostingRequestDto reqdto3 = new PostingRequestDto();
        reqdto3.setTitle("제목3");
        reqdto3.setContent("내용");
        reqdto3.setContentTag("테스트");
        reqdto3.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto3,"bbb");
        PostingRequestDto reqdto4 = new PostingRequestDto();
        reqdto4.setTitle("제목4");
        reqdto4.setContent("내용");
        reqdto4.setContentTag("테스트");
        reqdto4.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto4,"bbb");
        PostingRequestDto reqdto5 = new PostingRequestDto();
        reqdto5.setTitle("제목5");
        reqdto5.setContent("내용");
        reqdto5.setContentTag("테스트");
        reqdto5.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto5,"bbb");
        PostingRequestDto reqdto6 = new PostingRequestDto();
        reqdto6.setTitle("제목6");
        reqdto6.setContent("내용");
        reqdto6.setContentTag("테스트");
        reqdto6.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto6,"bbb");
        PostingRequestDto reqdto7 = new PostingRequestDto();
        reqdto7.setTitle("제목7");
        reqdto7.setContent("내용");
        reqdto7.setContentTag("테스트");
        reqdto7.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto7,"bbb");
        PostingRequestDto reqdto8 = new PostingRequestDto();
        reqdto8.setTitle("제목8");
        reqdto8.setContent("내용");
        reqdto8.setContentTag("테스트");
        reqdto8.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto8,"bbb");
        PostingRequestDto reqdto9 = new PostingRequestDto();
        reqdto9.setTitle("제목9");
        reqdto9.setContent("내용");
        reqdto9.setContentTag("테스트");
        reqdto9.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto9,"aaa");
        PostingRequestDto reqdto10 = new PostingRequestDto();
        reqdto10.setTitle("제목10");
        reqdto10.setContent("내용");
        reqdto10.setContentTag("테스트");
        reqdto10.setImageFile("/images/basic.jpg");
        postService.createPosting(reqdto10,"bbb");
    }

    @Transactional
    public void commentSet()
    {
        CommentRequestDto com1 = new CommentRequestDto();
        com1.setComment("내용1");
        com1.setUserName("bbb");
        com1.setPostingId(3L);
        commentService.createComment(com1);
        CommentRequestDto com2 = new CommentRequestDto();
        com2.setComment("내용2");
        com2.setUserName("bbb");
        com2.setPostingId(3L);
        commentService.createComment(com2);
        CommentRequestDto com3 = new CommentRequestDto();
        com3.setComment("내용3");
        com3.setUserName("bbb");
        com3.setPostingId(3L);
        commentService.createComment(com3);
        CommentRequestDto com4 = new CommentRequestDto();
        com4.setComment("내용4");
        com4.setUserName("aaa");
        com4.setPostingId(3L);
        commentService.createComment(com4);
        CommentRequestDto com5 = new CommentRequestDto();
        com5.setComment("내용5");
        com5.setUserName("aaa");
        com5.setPostingId(3L);
        commentService.createComment(com5);
        CommentRequestDto com6 = new CommentRequestDto();
        com6.setComment("내용6");
        com6.setUserName("aaa");
        com6.setPostingId(3L);
        commentService.createComment(com6);

    }

}
