package ru.progwards.java2.app.consult1;


import ru.progwards.java2.app.consult1.DataBase.Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/mentors")
public class Mentors extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String id = req.getParameter("id");// не понял пока, в каком месте наставник обретает id. Или это его логин?
        List<User> mentors = DataBase.INSTANCE.users.select(user -> user.is_mentor);

        req.setAttribute("mentors", mentors);
        req.getRequestDispatcher("/mentors.jsp").forward(req, resp);
    }
}
