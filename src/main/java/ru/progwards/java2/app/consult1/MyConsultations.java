package ru.progwards.java2.app.consult1;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/schedule/my")
public class MyConsultations extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientID = ""; // ???
        String login = req.getParameter("login");
        List<Consultation> myCons = DataBase.INSTANCE.consultations.select(e -> e.student.equals(login));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(myCons);
        resp.getWriter().write(json);
        req.setAttribute("myConsJson", json);
//        resp.sendRedirect("/readingJackson");
        req.getRequestDispatcher("/readingJackson").forward(req,resp);
    }
}