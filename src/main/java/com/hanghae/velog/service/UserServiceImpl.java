package com.hanghae.velog.service;

import com.hanghae.velog.model.User;
import com.hanghae.velog.dto.LoginRequestDto;
import com.hanghae.velog.dto.SignupRequestDto;
import com.hanghae.velog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Override
    public User signup(SignupRequestDto signupRequestDto) {
        String userId = signupRequestDto.getUserId();   //아이디:이메일형식
        String password = signupRequestDto.getPassword();   //비밀번호(유효성 검사 위해 암호화 X)
        String userName = signupRequestDto.getUserName();   //닉네임
        String profileimage = signupRequestDto.getProfileImage();
        //아이디 중복검사
        isValidUserId(userId);
        //비밀번호 유효성 검사
        isValidPassword(password);
        //사용자 닉네임 중복 검사
        isValidUserName(userName);

        password = passwordEncoder.encode(password);    //암호화한 비밀번호

        User user = new User(userId, password, userName, profileimage);
        return userRepository.save(user);
    }

    //로그인
    @Override
    public User login(LoginRequestDto loginRequestDto) {
        String userId = loginRequestDto.getUserId();    //사용자 아이디: 이메일 형식

        //아이디로 DB 검색
        Optional<User> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) {
            throw new IllegalArgumentException("로그인 실패(아이디 불일치)");
        }

        boolean isEqualPassword = passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword());
        if(!isEqualPassword) {
            throw new IllegalArgumentException("로그인 실패(비밀번호 불일치)");
        }

        return user.get();
    }

    //아이디 유효성 검사, 중복검사
    public void isValidUserId(String userId) {

        //이메일 정규식
        String emailReg = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if(!Pattern.matches(emailReg, userId)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }

        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()) {
            throw new IllegalArgumentException("아이디가 존재합니다.");
        }
   }

   //비밀번호 유효성 검사
    private void isValidPassword(String password) {

        int min = 4;     //비밀번호 최소길이
        int max = 16;    //비밀번호 최대길이

        //비밀번호 정규식
        String passwordReg = "^[A-Za-z0-9]{" + min + "," + max + "}$";
//       String passwordReg = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + min + "," + max + "})$";
       if(!Pattern.matches(passwordReg, password)) {
           throw new IllegalArgumentException("올바른 비밀번호 형식이 아닙니다.");
       }
    }

    //사용자 닉네임 중복 검사
    private void isValidUserName(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        if(user.isPresent()) {
            throw new IllegalArgumentException("닉네임이 존재합니다.");
        }
    }

}
