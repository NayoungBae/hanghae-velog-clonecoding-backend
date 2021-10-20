package com.hanghae.velog.service;

import com.hanghae.velog.dto.DetailResponseDto;
import com.hanghae.velog.dto.GetMyPostsResponseDto;
import com.hanghae.velog.dto.MyPostingResponseDto;
import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.model.Comment;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

    // 게시글 전체조회 메소드
    public void getPostings(Posting posting) throws ParseException {
        List<Posting> postings = postingRepository.findAllByOrderByCreatedAtDesc();
        for(int i=0; i<postings.size(); i++) {
            Posting posting1 = postings.get(0);
        }

//        for (int i=0; i<postings.size(); i++) {
//            postings.add(4,getDayBefore(posting));
//        }

//        PostingResponseDto postingResponseDto = new PostingResponseDto(posting)
    }


    public String getDayBefore(Posting posting) throws ParseException {

        String now = LocalDate.now().toString();
        String createdAt = posting.getCreatedAt().toString();

        Date format1 = new SimpleDateFormat("yyyy/MM/dd").parse(now);
        Date format2 = new SimpleDateFormat("yyyy/MM/dd").parse(createdAt);

        // date.getTime() : Date를 밀리세컨드로 변환. 1초 = 1000밀리초
        Long diffSec = (format1.getTime() - format2.getTime()) / 1000; // 초 차이
        Long diffMin = (format1.getTime() - format2.getTime()) / 60000; // 분 차이
        Long diffHour = (format1.getTime() - format2.getTime()) / 3600000; // 시 차이, 24시까지 나옴
        Long diffDays = diffSec / (24 * 60 * 60); // 일자수 차이 예:7일, 6일

        //DayBefore 계산
        // 초 차이가 60초 미만일 때 -> return 초 차이
        // 초 차이가 60초 이상, 분 차이가 60분 미만일 때 -> return 분 차이
        // 분 차이가 60분 이상, 시 차이가 24 미만일 때 -> return 시 차이
        // 시 차이가 24 이상, 일 차이가 7일 미만일 때 -> return 일자수 차이
        // 일 차이가 7일 이상일 때 -> return createdAt의 년, 월, 일까지

        String dayBefore = "";

        if(diffSec < 60) {
            return "diffSec.toString()";
        } else if(diffSec >= 60 && diffMin < 60) {
            return "diffMin.toString()";
        } else if(diffMin >= 60 && diffHour < 24) {
            return "diffHour.toString()";
        } else if(diffHour >= 24 && diffDays < 7) {
            return "diffDays.toString()";
        } else if (diffDays > 7) {
            return "format2.toString();";
        }
        return dayBefore;
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

}
