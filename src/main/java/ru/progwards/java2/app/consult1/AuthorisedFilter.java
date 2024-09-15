package ru.progwards.java2.app.consult1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

//@WebFilter("/*")
public class AuthorisedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthorisedFilter initialised");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("AuthorisedFilter started");
//        String username = request.getParameter("username");
//        Cookie login = new Cookie("login",username);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("  AuthorisedFilter: RequestURI = " + req.getRequestURI());
        HttpSession session = req.getSession();
        System.out.println("  AuthorisedFilter: session creation time = "+new Date(session.getCreationTime()));
        System.out.println("  AuthorisedFilter: session MaxInactiveInterval = "+session.getMaxInactiveInterval());
        String auth = (String) session.getAttribute("auth");
        System.out.println("  AuthorisedFilter: session auth attribute = "+auth);
        System.out.println("  AuthoriseFilter: session is_mentor attribute = " + session.getAttribute("is_mentor"));




        boolean allowedURI = (req.getRequestURI().equals("/login.jsp")
                           || req.getRequestURI().startsWith("/images/")
                           || req.getRequestURI().equals("/authorise")
                           || req.getRequestURI().equals("/favicon.ico"));

//        String login = Arrays.stream(req.getCookies())
//                .filter(c -> c.getName().equals("login"))
//                .map(Cookie::getValue).findAny().orElse("");
        if (auth == null || (auth.isEmpty() && !allowedURI)) {
            System.out.println("  AuthorisedFilter: нужна авторизация");
            session.setAttribute("auth", "");
//            session.setMaxInactiveInterval(1);
            resp.sendRedirect("/login.jsp");

        } else {
            chain.doFilter(request, response);
        }

//        if (username == null || username.trim().isEmpty()) {
//            request.setAttribute("error-description", "Поле \"username\" не может быть пустым.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }


    }

    @Override
    public void destroy() {

    }
}
