package com.hanghae.velog.controller;

import com.hanghae.velog.Util.MD5Generator;
import com.hanghae.velog.dto.*;
import com.hanghae.velog.dto.DetailResponseDto;
import com.hanghae.velog.dto.PostsResponseDto;
import com.hanghae.velog.dto.MsgResponseDto;
import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.dto.PostingResponseDto;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.repository.CommentRepository;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.repository.UserRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import com.hanghae.velog.service.FileService;
import com.hanghae.velog.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final PostingService postingService;
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;

    private String commonPath = "/images"; // 이경로는 우분투랑 윈도우랑 다르니까 주의해야댐 우분투 : "/"
    // 윈도우  : \\ 인것같음.

    //메인페이지 게시글 전체 조회
    @GetMapping("/api/posting")
    public PostingListResponseDto getPosts() throws ParseException {
        PostingListResponseDto allpostings = postingService.getPostings();
        return allpostings;
        }


    // 게시글 상세조회
    @GetMapping("/api/posting/{posting-ID}")
    public DetailResponseDto getPostingDetail(@PathVariable("posting-ID") Long postingId) {
        DetailResponseDto detailResponseDto = postingService.getPostingDetail(postingId);
        return detailResponseDto;
    }

    //게시글 생성
    @PostMapping("/api/posting")
    public MsgResponseDto createPosting(
            @RequestParam(value = "file",required = false) MultipartFile files,
            @RequestBody PostingRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            String filename = "basic.jpg";
            if (files != null) {
                String origFilename = files.getOriginalFilename();
                filename = new MD5Generator(origFilename).toString() + ".jpg";
                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */

                String savePath = System.getProperty("user.dir") + commonPath;
                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
                //files.part.getcontententtype() 해서 이미지가 아니면 false처리해야함.
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "/" + filename;// 이경로는 우분투랑 윈도우랑 다르니까 주의해야댐 우분투 : / 윈도우 \\ 인것같음.
                files.transferTo(new File(filePath));
            }

            //테스트코드
            String username = "반환오류";
            if(userDetails == null){
                username = "이게 반환되면 오류입니다";
            }
            if(userDetails != null){
                username = userDetails.getUser().getUserName();
            }


            requestDto.setImageFile(filename);

            Posting posts = postingService.createPosting(requestDto,username);

            MsgResponseDto msgResponseDto = new MsgResponseDto("게시글 작성에 성공되었습니다.");
            return msgResponseDto;
        }
        catch (Exception e) {

            MsgResponseDto msgResponseDto = new MsgResponseDto("에러 발생");
            return msgResponseDto;
        }

    }

    //게시글 수정
    @PutMapping("/api/posting/{posting-ID}")
    public MsgResponseDto updatePosts(@PathVariable("posting-ID") Long postingId,@RequestBody(required = false) PostingRequestDto reqDto,@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if(userDetails == null){
            throw new IllegalArgumentException("로그인 한 사용자만 수정 명령을 시도할 수 있습니다.");
        }
        postingService.updatePosting(postingId,reqDto,userDetails);
        MsgResponseDto msgResponseDto = new MsgResponseDto("수정 성공");
        return msgResponseDto;
    }

    //게시글 삭제
    @DeleteMapping("/api/posting/{posting-ID}")
    public MsgResponseDto deletePosts(@PathVariable("posting-ID") Long postingId, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        //삭제할때 댓글들 자동으로삭제하는지 확인
//        list test =commentRepository.findAllByPostingId(postingId);
        if(userDetails == null){
            throw new IllegalArgumentException("로그인 한 사용자만 삭제 명령을 시도할 수 있습니다.");
        }
        //삭제할때 같이 저장된 이미지 경로 및 파일도 삭제
        postingRepository.deleteById(postingId);
//        commentRepository.deleteBy(test);
        MsgResponseDto msgResponseDto = new MsgResponseDto("삭제 성공");
        return msgResponseDto;
    }

    //내가 작성한 게시글 목록 조회
    @GetMapping("/api/mypage")
    public PostsResponseDto getMyPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) throws ParseException {
        PostsResponseDto postingList = postingService.getMyPosts(userDetails);
        return postingList;
    }

    //특정 유저 게시글 조회
    @GetMapping("/api/userpage/{userName}")
    public PostsResponseDto getUserPosts(@PathVariable String userName) throws ParseException {
        PostsResponseDto postingList = postingService.getUserPosts(userName);
        return postingList;
    }

}
