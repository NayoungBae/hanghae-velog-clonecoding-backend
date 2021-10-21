package com.hanghae.velog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.velog.Util.MD5Generator;
import com.hanghae.velog.model.User;
import com.hanghae.velog.dto.LoginRequestDto;
import com.hanghae.velog.dto.LoginResponseDto;
import com.hanghae.velog.dto.MsgResponseDto;
import com.hanghae.velog.dto.SignupRequestDto;
import com.hanghae.velog.security.JwtTokenProvider;
//import com.hanghae.velog.service.KakaoUserService;
import com.hanghae.velog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RequiredArgsConstructor
@RestController     //return값이 html 파일이름이 아닌 이상 바꾸면 안됩니다
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
//    private final KakaoUserService kakaoUserService;

    private String commonPath = "/Profileimages";
    //회원가입
    @PostMapping("/user/signup")
    public MsgResponseDto signup(
            @RequestParam(value = "file",required = false) MultipartFile files,
            @RequestBody SignupRequestDto signupRequestDto) {

        MsgResponseDto msgResponseDto = null;
        try {
            String filename = "basic_profile.jpg";
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
                signupRequestDto.setProfileImage(filename);
                //유저가입
                userService.signup(signupRequestDto);

        }catch(Exception e) {
            msgResponseDto = new MsgResponseDto(e.getMessage());
            return msgResponseDto;
        }
        msgResponseDto = new MsgResponseDto("success");

        return msgResponseDto;
    }

    //로그인
    @PostMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto,
                                  HttpServletResponse response) {
        String userId = "";
        String token = "";
        LoginResponseDto loginResponseDto = null;
        try {

            User user = userService.login(loginRequestDto);
            //토큰에 payload에 들어갈 정보
            String userName = user.getUserName();   //사용자 닉네임을 토큰에 넣음
            token = jwtTokenProvider.createToken(userName);

            //response할 아이디 값을 변수에 저장
            userId = user.getUserId();

            //header에 토큰 세팅
            response.setHeader("X-AUTH-TOKEN", token);

            //header에 cookie 저장
            Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);

        } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
            loginResponseDto = new LoginResponseDto(null, null);
            return loginResponseDto;
        }

        loginResponseDto = new LoginResponseDto(userId, token);
        return loginResponseDto;
    }


//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//        kakaoUserService.kakaoLogin(code);
//        return "redirect:/";
//    }
}
