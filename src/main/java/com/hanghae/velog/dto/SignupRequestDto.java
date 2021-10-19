package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignupRequestDto {
    private String userId;  //사용자 ID: 이메일 형식
    private String password;    //사용자 비밀번호
    private String userName;    //사용자 이름
}
