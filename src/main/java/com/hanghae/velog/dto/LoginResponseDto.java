package com.hanghae.velog.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private String userId;  //아이디: 이메일 형식
    private String token;   //발급받은 토큰
}
