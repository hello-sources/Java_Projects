package com.ict.mvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    //“/”-->/WEB-INF/templates/index.html

    //将当前请求和控制器方法建立映射关系，当浏览器发送请求是value时候，就执行该方法
    @RequestMapping(value = "/")
    public String index() {
        //返回视图名称
        System.out.println("测试首页跳转");
        return "index";
    }

    @RequestMapping(value = "/target")
    public String toTarget() {
        return "target";
    }
}
