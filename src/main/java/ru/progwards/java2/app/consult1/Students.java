package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class Students extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<User> mentors = DataBase.INSTANCE.users.select(user -> user.is_mentor);
        List<User> students = DataBase.INSTANCE.users.select(user -> !user.is_mentor);

//        req.setAttribute("mentors", mentors);
        req.setAttribute("students", students);
        req.getRequestDispatcher("/students.jsp").forward(req, resp);
    }
}
