package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@WebServlet("/schedule/add")
public class ScheduleAddValue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value_start = req.getParameter("valueStart");
        String value_end = req.getParameter("valueEnd");
        String day_of_week = req.getParameter("dayOfWeek");
        final long oneDay = 24 * 60 * 60 * 1000;
        long valueStart;
        long valueEnd;
        int dayOfWeek;
        try {
            valueStart = Long.parseLong(value_start);
            valueEnd = Long.parseLong(value_end);
            dayOfWeek = Integer.parseInt(day_of_week);
        } catch (Exception e) {
            req.setAttribute("error-description", "Не удалось распознать параметры запроса.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        String mentor = (String) session.getAttribute("auth");
        long duration = valueEnd - valueStart;

        DataBase.INSTANCE.schedule.put(new DataBase.Schedule.Value(mentor, dayOfWeek, valueStart, duration));

//        LocalDateTime ldt = LocalDate.now().atStartOfDay();
//        long plus30days = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + (31 * oneDay) - 1000;// последняя секунда 30-го дня
//
//        for (long start = valueStart; start < plus30days; start += (7 * oneDay)) {
//            DataBase.INSTANCE.schedule.put(new DataBase.Schedule.Value(mentor, dayOfWeek, start, duration));
//        }

        resp.sendRedirect("/schedule?mentor=" + mentor);
    }
}
