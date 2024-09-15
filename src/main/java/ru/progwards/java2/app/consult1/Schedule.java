package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static ru.progwards.java2.app.consult1.AppEngine.todayStartOfDay;

@WebServlet("/schedule")
public class Schedule extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

// Структура запроса: GET /schedule/list?mentor=1&filter=ALL|PAST|FUTURE&sortDate=ASC|DESC&page=1&count=20
        String mentor = req.getParameter("mentor");
        String filter = req.getParameter("filter");
        String sortDate = req.getParameter("sortDate");

        long todayStartOfDay = todayStartOfDay();

        // null или FUTURE - по умолчанию, расписание на сегодня и будущее
        // PAST - только прошедшие (архив)
        // ALL - расписание за все время
        List<DataBase.Schedule.Value> scheduleValuesImmutable;
        if (filter != null) {
            switch (filter.toLowerCase()) {
                case "past":
                    scheduleValuesImmutable = DataBase.INSTANCE.schedule.select(e -> e.start < todayStartOfDay);
                    break;
                case "all":
                    scheduleValuesImmutable = DataBase.INSTANCE.schedule.getAll();
                    break;
                default:
                    scheduleValuesImmutable = DataBase.INSTANCE.schedule.select(e -> e.start >= todayStartOfDay);
                    break;
            }
        } else scheduleValuesImmutable = DataBase.INSTANCE.schedule.select(e -> e.start >= todayStartOfDay);

        List<DataBase.Schedule.Value> scheduleValues = new ArrayList<>(scheduleValuesImmutable);
        if (mentor != null && !mentor.trim().isEmpty()) {
            scheduleValues = scheduleValues.stream().filter(e -> e.mentor.equals(mentor)).collect(Collectors.toList());
        }

        Comparator<DataBase.Schedule.Value> sortDateComparator = (o1, o2) -> {
            if ("DESC".equalsIgnoreCase(sortDate))
                return Long.compare(o2.start, o1.start);
            return Long.compare(o1.start, o2.start);// ASC by default
        };
        scheduleValues.sort(sortDateComparator);

        List<DataBase.Users.User> mentors = DataBase.INSTANCE.users.select(user -> user.is_mentor);
        Map<String, DataBase.Users.User> mentorsMap = new HashMap<>();
        for (DataBase.Users.User user : mentors) mentorsMap.put(user.login, user);
        req.setAttribute("mentorsMap", mentorsMap);

        req.setAttribute("schedule", scheduleValues);
        req.setAttribute("mentor", mentor);
        req.setAttribute("sortDate", sortDate);
        req.setAttribute("filter", filter);

//        Enumeration<String> atrs = req.getAttributeNames();
//        System.out.println("Attributes:");
//        while (atrs.hasMoreElements()) System.out.println(" ...  " + atrs.nextElement());


        req.getRequestDispatcher("/schedule.jsp").forward(req, resp);
    }


}
