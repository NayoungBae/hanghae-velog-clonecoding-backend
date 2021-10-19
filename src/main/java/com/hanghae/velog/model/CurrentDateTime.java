package com.hanghae.velog.model;

import java.time.LocalDate;

public class CurrentDateTime {

    // 현재 날짜 구하기 예시)2021-10-19
    LocalDate now = LocalDate.now();

    // 연도, 월(문자열), 일 구하기
    int year = now.getYear(); //2021
    int monthValue = now.getMonthValue(); // 10
    int dayofMonth = now.getDayOfMonth(); // 19
}
