package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequestDto {
    private String userId;  //아이디: 이메일형식
    private String password;    //비밀번호
}
