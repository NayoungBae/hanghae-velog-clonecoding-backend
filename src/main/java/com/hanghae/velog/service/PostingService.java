package com.hanghae.velog.service;

import com.hanghae.velog.dto.DetailResponseDto;
import com.hanghae.velog.dto.MyPostingResponseDto;
import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.dto.GetMyPostsResponseDto;
import com.hanghae.velog.model.Comment;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

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

    public DetailResponseDto getPostingDetail(Long postingId) {
        Posting posting = postingRepository.findByPostingId(postingId);

        String title = posting.getTitle();
        String content = posting.getContent();
        String imageFile = posting.getImageFile();
        String modifiedAt = posting.getModifiedAt().toString();
        List<Comment> comments = posting.getComments();

        DetailResponseDto detailResponseDto = new DetailResponseDto(
                postingId, title, content, imageFile, modifiedAt, comments
        );

        return detailResponseDto;
    }

    //내가 작성한 게시글 목록 조회
    public GetMyPostsResponseDto getMyPosts(UserDetailsImpl userDetails) {
        String loginedUserName = userDetails.getUsername();    //현재 로그인한 닉네임

        List<Posting> posts = postingRepository.findByUserName(loginedUserName);     //로그인한 사용자가 작성한 글 목록 조회

        List<MyPostingResponseDto> data = new ArrayList<>();

        for(int i=0; i<posts.size(); i++) {
            Posting post = posts.get(i);

            Long postingId = post.getPostingId();
            String thumbNail = post.getImageFile();
            String title = post.getTitle();
            String content = post.getContent();

            //날짜 차이 계산
            LocalDate currentDate = LocalDateTime.now().toLocalDate();
            LocalDate createdAt = post.getCreatedAt().toLocalDate();
            Period period = Period.between(createdAt, currentDate);

            String dayBefore = Long.toString(period.getDays()) + "일 전";

            int commentCnt = post.getComments().size();
            //String userImage =
            String userName = post.getUserName();

            MyPostingResponseDto posting =
                    new MyPostingResponseDto(postingId, thumbNail, title, content,
                                             dayBefore, commentCnt, userName);
            data.add(posting);
        }

        GetMyPostsResponseDto getMyPostsResponseDto = new GetMyPostsResponseDto(data);

        return getMyPostsResponseDto;
    }


//    public Posting getDayBefore(Posting posting) {
//        LocalDate now = LocalDate.now(); // 2021-10-19
//        int year = now.getYear(); // 2021
//        int monthValue = now.getMonthValue(); // 10
//        int dayofMonth = now.getDayOfMonth(); // 19
//
//        LocalDateTime CreatedAt = posting.getCreatedAt();
//
//        if ()
//
//        //DayBefore 계산
//        int DayBefore
//    }

}
