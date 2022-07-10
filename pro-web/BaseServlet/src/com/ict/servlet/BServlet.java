package com.ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BServlet", urlPatterns = "/BServlet")
public class BServlet extends BaseServlet {
    public String func1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("func1...");
//        req.getRequestDispatcher("/xxx").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/xxx.jsp");
        return "/index.jsp";
    }

    public String func2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("func2...");
        return "r:/index.jsp";
    }

    public String func3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("func3...");
//        return "download:/WEB-INF/file/a.jpg";
        return "d:/WEB-INF/files/a.rmvb";
    }
}
