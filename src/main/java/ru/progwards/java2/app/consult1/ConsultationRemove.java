package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/consultation/remove")
public class ConsultationRemove extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        // предусмотреть все варианты защиты от падения при отсутствующем или некорректном "id"
        if (id == null) {
            req.setAttribute("error-description", "При выборе слота произошёл сбой. Просьба, повторить попытку.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        String[] keySet = id.split("&");

        long start;
        try {
            start = Long.parseLong(keySet[1]);
        } catch (NumberFormatException e) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        Consultation removed = DataBase.INSTANCE.consultations.remove(new DataBase.Consultations.Key(keySet[0], start));

        if (removed != null) {
            DataBase.INSTANCE.consultations.put(new Consultation(removed.mentor, removed.start, removed.duration, "", ""));
        } else {
            req.setAttribute("error-description", "Ошибка доступа к базе данных.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("/account");
    }
}
