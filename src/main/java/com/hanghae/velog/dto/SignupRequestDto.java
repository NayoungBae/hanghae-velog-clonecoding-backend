package com.hanghae.velog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequestDto {
    private String userId;  //사용자 ID: 이메일 형식
    private String password;    //사용자 비밀번호
    private String userName;    //사용자 이름
}
