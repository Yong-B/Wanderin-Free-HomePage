package com.example.PG.support.question.repository;

import com.example.PG.support.question.domain.QuestionEntity;
import com.example.PG.user.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findByMemberOrderByCreatedAtDesc(Member member);
}
