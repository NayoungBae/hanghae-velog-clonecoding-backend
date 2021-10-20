package com.hanghae.velog.controller;

import com.hanghae.velog.domain.User;
import com.hanghae.velog.dto.LoginRequestDto;
import com.hanghae.velog.dto.LoginResponseDto;
import com.hanghae.velog.dto.MsgResponseDto;
import com.hanghae.velog.dto.SignupRequestDto;
import com.hanghae.velog.security.JwtTokenProvider;
import com.hanghae.velog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController     //return값이 html 파일이름이 아닌 이상 바꾸면 안됩니다
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/user/signup")
    public MsgResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        MsgResponseDto msgResponseDto = null;
        try {
            userService.signup(signupRequestDto);
        } catch(Exception e) {
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

}
