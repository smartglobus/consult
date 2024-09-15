package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static ru.progwards.java2.app.consult1.AppEngine.todayStartOfDay;

@WebServlet("/account/mentor")
public class MentorAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // по умолчанию выводятся консультации авторизованного наставника на сегодняшний день
        String filter = req.getParameter("filter");// past, today, future (т.е. с сегодня до 7-го дня)
        if (filter == null || filter.isEmpty()) filter = "today";
        String student = req.getParameter("student");
        String userOn = (String) req.getSession().getAttribute("auth");// userOn здесь всегда наставник
        String mentor = req.getParameter("mentor");
        String sortDate = req.getParameter("sortDate");

        System.out.println("filter: " + filter);
        List<DataBase.Consultations.Consultation> myConsultations;
        long todayStartOfDay = todayStartOfDay();


//        if (filter == null) { // == case: today
//            myConsultations = DataBase.INSTANCE.consultations.select(c ->
//                    (c.start >= todayStartOfDay && c.start < (todayStartOfDay + (7 * 24 * 60 * 60 * 1000L))));
//        } else {
        switch (filter.toLowerCase()) {
//                case "all":
//                    myConsultations = DataBase.INSTANCE.consultations.getAll();
//                    break;
            case "past":
                myConsultations = DataBase.INSTANCE.consultations.select(c -> c.start < todayStartOfDay);
                break;
            case "future":
                myConsultations = DataBase.INSTANCE.consultations.select(c -> c.start >= todayStartOfDay);
                break;
            default:// == case: today
                myConsultations = DataBase.INSTANCE.consultations.select(c ->
                        (c.start >= todayStartOfDay && c.start < (todayStartOfDay + (24 * 60 * 60 * 1000L))));
                break;
        }
//        }
        // отсев незанятых слотов
        myConsultations = myConsultations.stream().filter(c -> !"".equals(c.student)).collect(Collectors.toList());

        if (mentor == null || mentor.isEmpty()) { // по умолчанию - авторизованный наставник
            myConsultations = myConsultations.stream().filter(c -> userOn.equals(c.getMentor())).collect(Collectors.toList());

        } else if (!"all".equals(mentor)) {// фильтр по наставнику. Если имя наставника некорректно - будет пустой список
            myConsultations = myConsultations.stream().filter(c -> mentor.equals(c.getMentor())).collect(Collectors.toList());
        } // иначе все наставники попадают в список

        if (student != null) {
            myConsultations = myConsultations.stream().filter(c -> student.equals(c.student)).collect(Collectors.toList());
        }

        if ("DESC".equalsIgnoreCase(sortDate)) myConsultations.sort((o1, o2) -> Long.compare(o2.start, o1.start));
        else myConsultations.sort((o1, o2) -> Long.compare(o1.start, o2.start));

        List<DataBase.Users.User> mentors = DataBase.INSTANCE.users.select(u -> u.is_mentor);
        Map<String, DataBase.Users.User> mentorsMap = new HashMap<>();
        for (DataBase.Users.User u : mentors) mentorsMap.put(u.login, u);
        req.setAttribute("mentorsMap", mentorsMap);
        req.setAttribute("consultations", myConsultations);
        req.setAttribute("student", student);
        req.setAttribute("sortDate", sortDate);

        if (mentor == null || mentor.isEmpty()) req.setAttribute("mentor", userOn);
        else req.setAttribute("mentor", mentor);

        req.setAttribute("filter", filter);

        req.getRequestDispatcher("/account_mentor.jsp").forward(req, resp);
    }
}
