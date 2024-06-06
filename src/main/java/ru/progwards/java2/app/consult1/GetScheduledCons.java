package ru.progwards.java2.app.consult1;

import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;


@WebServlet(urlPatterns = "/scheduled")
public class GetScheduledCons extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        System.out.println(login);
        PrintWriter writer = resp.getWriter();
        writer.println(
                "<html><head><meta charset=\"utf-8\"><title>GetScheduledCons</title></head>"+
                        "<body> !!!body!!!<br> Это моё тело! Пока голое.</body>"+
                        "</html>"
        );
        writer.println("<br>");
        writer.println("login: "+ login);
    }
}