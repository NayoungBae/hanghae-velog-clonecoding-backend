package com.hanghae.velog.controller;

import com.hanghae.velog.dbSet.SetTestDb;
import com.hanghae.velog.dto.MsgResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class testController {

    private final SetTestDb setTestDb;
    private Boolean flag = true;


    @GetMapping("/api/test")
    public MsgResponseDto testDB()
    {
      setTestDb.dbset();
      MsgResponseDto msgResponseDto = new MsgResponseDto("테스트셋업 성공");
      return msgResponseDto;
    }

    @GetMapping("/api/test2")
    public MsgResponseDto testDB2()
    {
        setTestDb.dbset2();
        MsgResponseDto msgResponseDto = new MsgResponseDto("테스트셋업 성공");
        return msgResponseDto;
    }
}
