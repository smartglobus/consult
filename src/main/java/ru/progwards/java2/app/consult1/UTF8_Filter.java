package ru.progwards.java2.app.consult1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.util.Enumeration;

//@WebFilter("/*")
public class UTF8_Filter implements Filter {
    FilterConfig fConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        fConfig = filterConfig;
//        Enumeration<String> paramNames = filterConfig.getInitParameterNames();
        System.out.println("utf-8 filter initialised");
//        while (paramNames.hasMoreElements()) {
//            String parName = paramNames.nextElement();
//            System.out.print(parName + ": ");
//            System.out.println(filterConfig.getInitParameter(parName));
//
//        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");

        System.out.println("UTF-8 Filter started");
//        Enumeration<String> paramNames = fConfig.getInitParameterNames();
//        while (paramNames.hasMoreElements()) {
//            String parName = paramNames.nextElement();
//            System.out.print(parName + ": ");
//            System.out.println(fConfig.getInitParameter(parName));
//        }

//        servletResponse.getWriter().println("Сработал utf-8 filter<br><br>");
//        System.out.println("Сработал utf-8 filter");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
    }
}
