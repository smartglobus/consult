package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/account")
public class Welcome extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> users = new ArrayList<>(List.of("Vasya","Petya", "Kolya"));// отладка. Вместо списка будет обращение к базе данных.


        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // если username не найден в базе, то переадрессация на страницу с ошибкой
        if (!users.contains(username)){
            req.getRequestDispatcher("userNotFound.jsp").forward(req,resp);
//            resp.sendRedirect("userNotFound.jsp");
        }

        writer.println("<html>" +
                "<head><title>My account</title></head>" +
                "<body>" +
                "<div align='center' style=\"font-size:25px; color:teal\">Username: " + username + "<br>" +
                "Password: " + password + "</div>" +
                "</body>" +
                "</html>");



    }
}
