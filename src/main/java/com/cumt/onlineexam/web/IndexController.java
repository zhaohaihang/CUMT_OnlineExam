package com.cumt.onlineexam.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
    @GetMapping
    public String index(){
        return "/index";
    }

    @GetMapping("/update-pass")
    public String update(){
        return "update-pass";
    }
}
