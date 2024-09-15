package ru.progwards.java2.app.consult1;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OnlyForMentorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("OnlyForMentorFilter started");
        HttpSession session = ((HttpServletRequest) request).getSession();
        String is_mentor = (String) session.getAttribute("is_mentor");
        if (is_mentor == null || "false".equals(is_mentor)) {
            request.setAttribute("error-description", "Недостаточно прав для просмотра ресурса.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
