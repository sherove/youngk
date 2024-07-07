package com.young.in.youngk.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String hello() {
        return "index"; // 이 문자열이 templates 폴더의 index.html을 찾습니다.
    }


}
