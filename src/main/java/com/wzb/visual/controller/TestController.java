package com.wzb.visual.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping(value = "CoverTest")
    public String reDirect() {
        System.out.println("CoverTest");
        return "cover.html";
    }
}
