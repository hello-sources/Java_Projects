package com.ict.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 获取参数，用来识别用户请求的方法
         */
        String methodName = req.getParameter("method");
        if (methodName == null || methodName.trim().isEmpty()) {
            throw new RuntimeException("您没有传递method参数，无法确定想要调用的方法");
        }

        /**
         * 得到方法名称，是否可以通过反射来调用方法
         * 1、得到方法名，通过方法名得到该method类的对象
         * 2、需要得到class，然后调用它的方法进行查询，得到method
         * 3、我们要查询的是当前类的方法，所以我们要得到当前类的class
         */
        Class c = this.getClass();
        Method method = null;
        try {
            method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("您要调用的方法：" + methodName + ", 不存在");
        }

        /**
         * 调用method表示的方法
         */
        try {
            String result = (String)method.invoke(this, req, resp);
            /**
             * 获取请求处理方法，执行后返回的字符串，它表示转发或重定向的路径
             * 帮助完成转发或者重定向
             */
            /**
             * 如果用户返回的是null，则什么都不做
             */
            if (result == null || result.trim().isEmpty()) {
                return ;
            }
            /**
             * 查看返回字符串中是否包含冒号，如果没有，则转发
             * 有冒号，使用冒号分割字符串得到前缀和后缀
             * 前缀如果是f表示转发，r表示重定向，后缀就是转发或重定向的路径
             */
            if (result.contains(":")) {
                //使用冒号分割字符串，得到前缀和后缀
                int index = result.indexOf(":");
                String s = result.substring(0, index);
                String path = result.substring(index+ 1);
                if (s.equalsIgnoreCase("r")) {
                    resp.sendRedirect(req.getContextPath() + path);
                } else if (s.equalsIgnoreCase("f")) {
                    req.getRequestDispatcher(path).forward(req, resp);
                } else {
                    throw new RuntimeException("您指定的操作 " + s + ", 当前版本不支持");
                }

            } else { //没有冒号，默认转发
                req.getRequestDispatcher(result).forward(req, resp);
            }

        } catch (Exception e) {
            System.out.println("您调用的方法：" + methodName + ", 内部抛出了异常");
            throw  new RuntimeException(e);
        }
    }
}
