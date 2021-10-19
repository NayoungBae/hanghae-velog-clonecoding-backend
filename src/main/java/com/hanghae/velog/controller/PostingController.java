package com.hanghae.velog.controller;

import com.hanghae.velog.Util.MD5Generator;
import com.hanghae.velog.dto.PostingRequestDto;
import com.hanghae.velog.model.Posting;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostingController {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final PostingService postingService;
    private final PostingRepository postingRepository;

    private String commonPath = "/images"; // 이경로는 우분투랑 윈도우랑 다르니까 주의해야댐 우분투 : "/"
    // 윈도우  : \\ 인것같음.

    //메인페이지 게시글 전체 조회
    @GetMapping("/api/posting")
    public List<Posting> getPostings() {
        List<Posting> postingList = postingRepository.findAllByOrderByCreatedAtDesc();
        return postingList;
    }

    @PostMapping("/api/posting")
    public Posting createPosting(
            @RequestParam(value = "file",required = false) MultipartFile files,
            @RequestBody PostingRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            String filename = "basic.jpg";
            if (files != null) {
                String origFilename = files.getOriginalFilename();
                filename = new MD5Generator(origFilename).toString() + ".jpg";
                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
                System.out.println("test");
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
                username = userDetails.getUser().getUserId();
            }


            requestDto.setImageFile(filename);

            Posting posts = postingService.createPosting(requestDto,username);
            return null;
        }
        catch (Exception e) {
            return null;
        }

    }
}
