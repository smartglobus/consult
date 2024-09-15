package ru.progwards.java2.app.consult1;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static ru.progwards.java2.app.consult1.ScheduleGetFreeSlots.getStartOfDay;

//выбор даты и времени нового ЭР для заданного дня недели
@WebServlet("/schedule/new")
public class ScheduleGetDateTime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dayOfWeek = req.getParameter("day_of_week");
        int day_of_week;
        final long oneDay = 24 * 60 * 60 * 1000;


        // проверка параметров запроса
        if (dayOfWeek == null) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        try {
            day_of_week = Integer.parseInt(dayOfWeek);
        } catch (NumberFormatException e) {
            req.setAttribute("error-description", "Не распознаны параметры запроса.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        if (day_of_week < 1 || day_of_week > 7) {
            req.setAttribute("error-description", "Параметр день недели должен быть в диапазоне то 1 до 7.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        List<Long> dates = new ArrayList<>();
        long theDay = getStartOfDay(day_of_week);
        long todayStartOfDay = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long plus30days = todayStartOfDay + (31 * oneDay);// конец 30-го дня

        for (long dayStart = theDay; dayStart < plus30days; dayStart += (7 * oneDay)) {
            dates.add(dayStart);
        }

        req.setAttribute("day_of_week", dayOfWeek);
        req.setAttribute("dates", dates);
        req.getRequestDispatcher("/schedule-new.jsp").forward(req, resp);
    }
}
