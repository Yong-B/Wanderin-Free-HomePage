package com.example.PG.support.question.controller;

import com.example.PG.support.question.domain.QuestionEntity;
import com.example.PG.support.question.service.QuestionService;
import com.example.PG.user.member.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
 
    @PostMapping("/inquiry/save")
    public String saveInquiry(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              HttpSession session) {

        // 세션에서 로그인 유저 꺼내기
        Member loginMember = (Member) session.getAttribute("loginMember");

        // 로그인 안 한 경우 처리 (보통 인터셉터로 하지만 간단히 로직 추가)
        if (loginMember == null) {
            return "redirect:/login";
        }

        // 서비스 호출하여 저장
        questionService.saveQuestion(title, content, loginMember);

        // 저장 후 다시 고객지원 페이지로 이동 (문의내역 탭이 보이게 리다이렉트)
        return "redirect:/support/faq?tab=list";
    }
}