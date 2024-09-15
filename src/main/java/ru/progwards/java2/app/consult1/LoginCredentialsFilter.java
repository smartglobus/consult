package ru.progwards.java2.app.consult1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter("/authorise")
public class LoginCredentialsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginCredentialsFilter initialised");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("LoginCredentialsFilter started");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (username.isEmpty() || password.isEmpty()) {
            req.setAttribute("error-description", "Поля авторизации не могут быть пустыми.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (username.length()<3||password.length()<5){
            req.setAttribute("error-description", "Имя пользователя должно содержать не менее 3 символов, " +
                    "пароль - не менее 5 символов.<br>" +
                    "Убедитесь, что введённые вами значения соответствуют требованиям.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }


        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
