package com.hanghae.velog.service;

import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.model.Timestamped;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;

    @Transactional
    public Posting createPosting(PostingRequestDto requestDto, String userName) {
        // 게시글의 작성자닉네임, 제목, 내용, 첨부한 사진 내용만 있음
        Posting posting = new Posting(requestDto, userName);

        postingRepository.save(posting);
        return posting;
    }

    @Transactional
    public void updatePosting(Long postingId, PostingRequestDto requestDto, UserDetailsImpl userDetails) {
        String currentLogineduserName = userDetails.getUsername();    //닉네임
        String postedUserName = "";

        Posting posting = postingRepository.findByPostingId(postingId);     //수정할 게시글이 있는지 확인
        if(posting != null) {   //DB에서 검색한 결과가 있으면
            postedUserName = posting.getUserName();  //DB데이터에서 닉네임 가져옴

            if(!currentLogineduserName.equals(postedUserName)) {    //글 작성자와 로그인한 계정이 다를 경우
                throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다.");   //수정 못함
            }

            posting.updatePosting(requestDto);

        } else {    //DB 데이터가 없으면
            throw new NullPointerException("해당하는 게시글이 없습니다.");
        }

    }
}
