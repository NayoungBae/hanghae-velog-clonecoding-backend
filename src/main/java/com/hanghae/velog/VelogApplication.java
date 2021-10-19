package com.hanghae.velog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelogApplication.class, args);
    }

}
