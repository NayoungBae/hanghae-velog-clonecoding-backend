package com.hanghae.velog.controller;

import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import com.hanghae.velog.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {

    private final PostingService postingService;
    private final PostingRepository postingRepository;

    //메인페이지 게시글 전체 조회
    @GetMapping("/api/posting")
    public List<Posting> getPostings() {
        List<Posting> postingList = postingRepository.findAllByOrderByCreatedAtDesc();
        return postingList;
    }

    @PostMapping("/api/posting")
    public Posting createPosting(@RequestBody PostingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // token에서 로그인한 회원의 닉네임 빼오기
        String userName = userDetails.getUsername();
        // 게시글 만들어서 게시글 생성
        Posting posting = PostingService.createPosting(requestDto, userName);
        return posting;
    }
}
