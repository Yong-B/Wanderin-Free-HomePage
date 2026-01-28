package com.example.PG.user.login.service;

import com.example.PG.user.member.domain.Member;
import com.example.PG.user.member.domain.Role;
import com.example.PG.user.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 설정 파일(yml)에서 값을 읽어옵니다. 
    // 값이 없으면 에러가 나거나 기본값(admin1234)을 쓰도록 설정할 수 있습니다.
    @Value("${admin.password:admin1234}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (memberRepository.findByLoginId("admin").isEmpty()) {
            Member admin = Member.builder()
                    .loginId("admin")
                    .password(passwordEncoder.encode(adminPassword))
                    .email("admin@naver.com")
                    .name("운영자")
                    .role(Role.ADMIN)
                    .build();
            memberRepository.save(admin);
            System.out.println(">>> 관리자 계정(admin) 생성 완료!");
        }
    }
}