package com.example.PG.news.notice.controller;

import com.example.PG.board.domain.dto.BoardWriteDto;
import com.example.PG.news.notice.domain.Notice;
import com.example.PG.news.notice.domain.dto.NoticeWriteDto;
import com.example.PG.news.notice.service.NoticeService;
import com.example.PG.user.member.domain.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news/notice")
public class NoticeController {
    private final NoticeService noticeService;

    // 1. 글쓰기 폼 페이지 이동
    @GetMapping("/write")
    public String writeForm(Model model) {
        // th:object="${noticeWriteDto}"와 연결될 빈 객체를 반드시 넘겨줘야 에러가 안 납니다!
        model.addAttribute("noticeWriteDto", new NoticeWriteDto());
        return "news/noticeWrite";
    }

    // 2. 글 저장 처리 (Service의 write 메서드 호출)
    @PostMapping("/write")
    public String write(@ModelAttribute NoticeWriteDto noticeDto,
                        @SessionAttribute(name = "loginMember", required = false) Member loginMember) {

        if (loginMember == null) {
            // 로그인이 안 되어 있으면 로그인 페이지로 보냄
            return "redirect:/login";
        }

        // 이제 loginMember는 '운영자' 객체를 가지고 있습니다.
        noticeService.write(noticeDto, loginMember);
        return "redirect:/news/notice";
    }
    // 3. 상세 보기 (Service의 getNotices 메서드 호출)
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        // 서비스에서 findById를 수행하고 viewCount를 올리는 getNotices 호출
        Notice notice = noticeService.getNotices(id);
        model.addAttribute("notice", notice);
        return "news/noticeDetail";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("dto") NoticeWriteDto dto,
                         HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        // 1. 세션 체크
        if (loginMember == null) {
            return "redirect:/login";
        }

        // 2. 서비스 호출 (내부에서 본인 확인 로직 수행)
        try {
            noticeService.update(id, dto, loginMember.getId());
        } catch (IllegalStateException e) {
            // 권한이 없을 경우 리스트로 튕겨내기
            return "redirect:/news/notice";
        }

        // 3. 수정 후 다시 상세 페이지로 이동
        return "redirect:/news/notice/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            return "redirect:/login";
        }

        try {
            noticeService.delete(id, loginMember.getId());
        } catch (IllegalStateException e) {
            // 권한이 없을 경우 에러 메시지와 함께 리다이렉트 (필요 시 로직 추가)
            return "redirect:/news/notice";
        }

        return "redirect:/news/notice";
    }
}
