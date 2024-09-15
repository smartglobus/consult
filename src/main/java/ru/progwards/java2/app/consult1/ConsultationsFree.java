package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;
import ru.progwards.java2.app.consult1.DataBase.Schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ru.progwards.java2.app.consult1.AppEngine.getDayOfWeek;
import static ru.progwards.java2.app.consult1.AppEngine.todayStartOfDay;

@WebServlet("/consultation/free")
public class ConsultationsFree extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String student = (String) req.getSession().getAttribute("auth");
        if (!DataBase.INSTANCE.users.exists(student)) {
            req.setAttribute("error-description", "Студент с указанным именем не найден в базе данных.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        String mentor = req.getParameter("mentor");
        System.out.println("ConsultationsFree mentor value: " + mentor);
//список из 7 списков свободных слотов для выбранного наставника на ближайшие 7 дней
        List<List<Consultation>> freeSlotsArray = new ArrayList<>(7);
        freeSlotsArray.add(new ArrayList<>(DataBase.INSTANCE.consultations.select(c ->
                        (c.mentor.equals(mentor) &&
                                c.student.isEmpty() &&
                                c.start > System.currentTimeMillis() &&
                                c.start < todayStartOfDay() + TimeUnit.DAYS.toMillis(1)))
                .stream().sorted(Comparator.comparing(c -> c.start)).collect(Collectors.toList())));

        for (int i = 2; i <= 7; i++) {
            int d = i;
            freeSlotsArray.add(new ArrayList<>(DataBase.INSTANCE.consultations.select(c ->
                    (c.mentor.equals(mentor) &&
                            c.student.isEmpty() &&
                            c.start > todayStartOfDay() + TimeUnit.DAYS.toMillis(d - 1) &&
                            c.start < todayStartOfDay() + TimeUnit.DAYS.toMillis(d))))
                    .stream().sorted(Comparator.comparing(c -> c.start)).collect(Collectors.toList()));
        }

//        Map<Integer, Date> daysAndDates = new HashMap<>(7);
        List<Integer> days = new ArrayList<>(7);
        List<Date> dates = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            Date date = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(i));
            dates.add(date);
            days.add(getDayOfWeek(date.getTime()));
//            daysAndDates.put(dayOfWeek, date);
        }

        req.setAttribute("mentor", mentor);
        req.setAttribute("days", days);
        req.setAttribute("dates", dates);
        req.setAttribute("freeSlotsArray", freeSlotsArray);
        req.getRequestDispatcher("/consultation-add.jsp").forward(req, resp);
    }
}


