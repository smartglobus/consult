package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/consultation/add")
public class ConsultationAdd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String student = (String) req.getSession().getAttribute("auth");
        String comment = new String(req.getParameter("comment").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String id = req.getParameter("id");
        if (id == null || req.getParameterValues("id").length != 1) {
            req.setAttribute("error-description", "При выборе слота произошёл сбой. Просьба, повторить попытку.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        String[] keySet = id.split("&");// {mentor, start}
        String mentor = keySet[0];
        long start;
        try {
            start = Long.parseLong(keySet[1]);
        } catch (Exception e) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }


//        System.out.println("ConsultationAdd mentor: " + mentor);
//        System.out.println("ConsultationAdd start: " + keySet[1]);
//        System.out.println("ConsultationAdd comment: " + comment);

        Consultation removed = DataBase.INSTANCE.consultations.remove(new DataBase.Consultations.Key(mentor, start));
        if (removed != null) {
            DataBase.INSTANCE.consultations.put(new Consultation(mentor, start, removed.duration, student, comment));
        } else {
            req.setAttribute("error-description", "Ошибка доступа к базе данных.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("/account");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
