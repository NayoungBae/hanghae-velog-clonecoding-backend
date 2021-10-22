package com.hanghae.velog.service;

import com.hanghae.velog.dto.*;
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

    public DetailResponseDto getPostingDetail(Long id) {
        Posting posting = postingRepository.findByPostingId(id);

        String title = posting.getTitle();
        String content = posting.getContent();
        String filePath = posting.getFilePath();
        String modifiedAt = posting.getModifiedAt().toString();

        List<CommentResponseDto> commentList = new ArrayList<>();

        List<Comment> comments = posting.getComments();

        for(Comment com: comments) {
            Long postingId = com.getPostingId();
            Long commentId = com.getId();
            String userName = com.getUserName();
            String comment = com.getComment();
//            Long userIndex =    //...??
            LocalDateTime createdAtComment = com.getCreatedAt();
            LocalDateTime modifiedAtComment = com.getModifiedAt();

            CommentResponseDto commentResponseDto =
                    new CommentResponseDto(postingId, commentId, userName,
                                            comment, createdAtComment, modifiedAtComment);

            commentList.add(commentResponseDto);
        }

        DetailResponseDto detailResponseDto =
                new DetailResponseDto(id, title, content, filePath, modifiedAt, commentList);

        return detailResponseDto;
    }

    // 게시글 전체조회 메소드
    public PostingListResponseDto getPostings() throws ParseException {
        List<Posting> postings = postingRepository.findAllByOrderByCreatedAtDesc();

        List<PostingResponseDto> data = new ArrayList<>();

        for(int i=0; i<postings.size(); i++) {
            Posting post = postings.get(i);

            Long postingId = post.getPostingId();
            String userName = post.getUserName();
            String title = post.getTitle();
            String content = post.getContent();
            String filePath = post.getFilePath();
            String imageUrl = post.getImageUrl();
            String dayBefore = getDayBefore(post);
            int commentCnt = post.getComments().size();

            PostingResponseDto responseDto =
                    new PostingResponseDto(postingId, userName,imageUrl, title, content, filePath, dayBefore, commentCnt);

            data.add(responseDto);
        }

        PostingListResponseDto postingListResponseDto = new PostingListResponseDto(data);

        return postingListResponseDto;
    }

    //내가 작성한 게시글 목록 조회
    public PostsResponseDto getMyPosts(UserDetailsImpl userDetails) throws ParseException {
        String loginedUserName = userDetails.getUsername();    //현재 로그인한 닉네임

        PostsResponseDto postsResponseDto = getPosts(loginedUserName);

        return postsResponseDto;
    }

    //특정 유저 게시글 조회
    public PostsResponseDto getUserPosts(String currentUserName) throws ParseException {

        PostsResponseDto postsResponseDto = getPosts(currentUserName);

        return postsResponseDto;
    }

    //게시한 날짜부터 얼마나 되었는 지 계산
    public String getDayBefore(Posting posting) throws ParseException {

        //LocalDateTime -> Date으로 변환
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = posting.getCreatedAt();

        Date format1 = java.sql.Timestamp.valueOf(now);
        Date format2 = java.sql.Timestamp.valueOf(createdAt);


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
            String secstr = diffSec.toString();
            dayBefore = secstr + "초 전";
        } else if(diffSec >= 60 && diffMin < 60) {
            String minstr = diffMin.toString();
            dayBefore = minstr + "분 전";
        } else if(diffMin >= 60 && diffHour < 24) {
            String hourstr = diffHour.toString();
            dayBefore = hourstr + "시 전";
        } else if(diffHour >= 24 && diffDays < 7) {
            String daystr = diffDays.toString();
            dayBefore = daystr + "일 전";
        } else if (diffDays > 7) {
            dayBefore = format2.toString();
        }
        return dayBefore;
    }

    //특정 닉네임을 가진 계정이 쓴 게시글 조회
    private PostsResponseDto getPosts(String currentUserName) throws ParseException {
        List<Posting> posts = postingRepository.findByUserNameOrderByCreatedAtDesc(currentUserName);

        List<PostingResponseDto> data = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {
            Posting post = posts.get(i);

            Long postingId = post.getPostingId();
            String filePath = post.getFilePath();
            String title = post.getTitle();
            String content = post.getContent();
            String dayBefore = getDayBefore(post);
            String imageUrl = post.getImageUrl();
            int commentCnt = post.getComments().size();
            //String userImage =
            String userName = post.getUserName();

            PostingResponseDto responseDto =
                    new PostingResponseDto(postingId, userName, title, content,
                            imageUrl,filePath, dayBefore, commentCnt);
            data.add(responseDto);
        }

        PostsResponseDto postsResponseDto = new PostsResponseDto(data);

        return postsResponseDto;
    }


}