package com.example.PG.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    @GetMapping("/information")
    public String info() {
        return "info/information";
    }
}
