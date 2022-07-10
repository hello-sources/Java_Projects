package com.ict.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统计访问量
 */

@WebServlet(name = "AServlet", urlPatterns = "/AServlet")
public class AServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1、获取servletContext对象
         * 2、从servletContext中获取名为count的属性
         * 3、如果存在，就count + 1再保存回去
         * 4、不存在，说明是第一次访问，向servletcontext中保存名为count的属性，值为1
         */
        ServletContext app = this.getServletContext();
        Integer count = (Integer) app.getAttribute("count");
        if (count == null) {
            app.setAttribute("count", 1);
        } else {
            app.setAttribute("count", count+ 1);
        }
        System.out.println(count);
        PrintWriter pw =  response.getWriter();
        pw.write("<h1>" + count + "</h1>");
    }
}
