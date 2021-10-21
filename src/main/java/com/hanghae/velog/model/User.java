package com.hanghae.velog.model;

import com.hanghae.velog.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //테이블 ID

    @Column(nullable = false, unique = true)
    private String userId;      //사용자 ID: email

    @Column(nullable = false, unique = true)
    private String userName;    //사용자 닉네임

    @Column(nullable = false)
    private String password;    //사용자 비밀번호

    @Column(nullable = false)
    private String ProfileImage;

    public User(String userId, String password, String userName, String profileImage) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.ProfileImage = profileImage;
    }

}
