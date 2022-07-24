package com.ict.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {


    @RequestMapping(
            value = {"/testRequestMapping", "/test"},
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String success() {
        return "success";
    }

    @GetMapping("/testGetMapping")
    public String testGetMapping() {
        return "success";
    }

    @PostMapping("/testPostMapping")
    public String testPostMapping() {
        return "success";
    }
}
