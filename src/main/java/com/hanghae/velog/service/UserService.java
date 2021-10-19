package com.hanghae.velog.service;

import com.hanghae.velog.domain.User;
import com.hanghae.velog.dto.LoginRequestDto;
import com.hanghae.velog.dto.LoginResponseDto;
import com.hanghae.velog.dto.SignupRequestDto;

public interface UserService {
    //회원가입
    public User signup(SignupRequestDto signupRequestDto);

    //로그인
    public User login(LoginRequestDto loginRequestDto);
}
