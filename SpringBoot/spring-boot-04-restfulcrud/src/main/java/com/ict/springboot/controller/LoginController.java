package com.ict.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username")  String username,
                        @RequestParam("password")  String password,
                        Map<String, Object> map, HttpSession httpSession) {
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            httpSession.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            map.put("msg", "用户名密码错误");
            return "login";
        }
    }
}