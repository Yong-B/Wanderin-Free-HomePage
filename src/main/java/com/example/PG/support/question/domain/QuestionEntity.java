package com.example.PG.support.question.domain;

import com.example.PG.user.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ✅ 유저 테이블과의 외래키 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // DB에 member_id 컬럼이 생성됩니다
    private Member member;

    // 답변 관련 필드 (나중에 답변 기능을 위해)
    @Column(columnDefinition = "TEXT")
    private String answer;

    private boolean isAnswered = false;
    
    

}