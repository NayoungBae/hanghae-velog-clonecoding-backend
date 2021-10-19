package com.hanghae.velog.service;

import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.model.Timestamped;
import com.hanghae.velog.repository.PostingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostingService {
    private final PostingRepository postingRepository;
    private final Timestamped timestamped;

    @Transactional
    public Posting createPosting(PostingRequestDto requestDto, String userName) {
        // 게시글의 작성자닉네임, 제목, 내용, 첨부한 사진 내용만 있음
        Long commentCnt =

        Posting posting = new Posting(requestDto, userName);


        PostingRepository.save(posting);
        return posting;
    }
}
