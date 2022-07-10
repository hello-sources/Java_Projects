package com.ict.servlet;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(name = "AServlet", urlPatterns = "/AServlet")
public class AServlet extends BaseServlet {

    public void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("addUser...");
        throw new IOException("测试一下");
    }

    public void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("editUser...");
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("deleteUser...");
    }

    public void loadUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("loadUser...");
    }

    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("findAll...");
    }
}
