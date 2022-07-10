package com.ict.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView();
        System.out.println("test----------------------------");
        //封装要显示到视图中的数据
        mv.addObject("msg", "hello springmvc");
        //视图名
        mv.setViewName("hello");
        return mv;
    }
}
