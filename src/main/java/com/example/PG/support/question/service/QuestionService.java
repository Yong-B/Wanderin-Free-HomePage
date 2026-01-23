package com.example.PG.support.question.service;

import com.example.PG.support.question.domain.QuestionEntity;
import com.example.PG.support.question.repository.QuestionRepository;
import com.example.PG.user.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long saveQuestion(String title, String content, Member member) {
        QuestionEntity question = new QuestionEntity();

        question.setTitle(title);
        question.setContent(content);
        question.setMember(member);
        question.setCreatedAt(LocalDateTime.now());
        question.setAnswered(false);

        QuestionEntity savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    public List<QuestionEntity> findMyQuestions(Member member) {
        return questionRepository.findByMemberOrderByCreatedAtDesc(member);
    }
}
