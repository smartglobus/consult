package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;
import ru.progwards.java2.app.consult1.DataBase.Schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/schedule/remove")
public class ScheduleRemoveValue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            req.setAttribute("error-description", "Ошибка запроса.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        String[] keySet = id.split("&");
        String mentor = keySet[0];
        int dayOfWeek;
        long start;
        try {
            dayOfWeek = Integer.parseInt(keySet[1]);
            start = Long.parseLong(keySet[2]);
        } catch (Exception e) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        Schedule.Value removed = DataBase.INSTANCE.schedule.remove(new Schedule.Key(mentor, dayOfWeek, start));
        if (removed == null) {
            req.setAttribute("error-description", "Ошибка доступа к базе данных. Возможно, этот элемент уже удалён.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        //проверить, есть ли уже сгенерированные слоты консультаций на этот период, и если на них ещё нет записи, удалить их
        List<Consultation> cons_to_delete = DataBase.INSTANCE.consultations.select(c ->
                c.mentor.equals(removed.mentor) &&
                        "".equals(c.student) &&
                        c.start >= removed.start &&
                        c.start + c.duration <= (removed.start + removed.duration));

        for (Consultation c : cons_to_delete)
            DataBase.INSTANCE.consultations.remove(new DataBase.Consultations.Key(c.mentor, c.start));

        resp.sendRedirect("/schedule?mentor=" + req.getSession().getAttribute("auth"));
    }
}
