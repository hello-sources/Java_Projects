package com.ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.IntUnaryOperator;

@WebServlet(name = "AServlet", urlPatterns = "/AServlet")
public class AServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ClassLoader cl = this.getClass().getClassLoader();
        //classloader相对于classes目录
        //InputStream input = cl.getResourceAsStream("a.txt");
//        String s = IOUtils.toString(input);
//        System.out.println(s);
        Class c = this.getClass();
        //相当于当前.classes文件所在目录，如果要找的文件不在该目录下，就空指针异常
        //InputStream input = c.getResourceAsStream("a.txt");

        //相对classes下使用/
        InputStream input = c.getResourceAsStream("/a.txt");

        //如果是跨目录下找资源，则使用/..回退，绝对路径不行不能带盘符
        //InputStream input = c.getResourceAsStream("/../../index.jsp");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        try {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
        } catch (Exception e) {
            try {
                input.close();
                bufferedReader.close();
            } catch (Exception e1) {
                //do nothing
            }
        }
    }
}
