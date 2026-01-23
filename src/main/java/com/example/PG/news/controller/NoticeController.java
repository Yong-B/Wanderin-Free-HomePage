package com.example.PG.news.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NoticeController {

    @GetMapping("/notice")
    public String notice() {
        return "news/notice";
    }
}
