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

import static ru.progwards.java2.app.consult1.AppEngine.todayStartOfDay;

@WebServlet("/account/student")
public class StudentAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String user = (String) session.getAttribute("auth");
//        String filter = req.getParameter("filter");//all, past, today, future (т.е. с сегодня до 7-го дня)
        List<DataBase.Consultations.Consultation> myConsultations;

        myConsultations = new ArrayList<>(DataBase.INSTANCE.consultations.select(c ->
                c.student.equals(user) && c.start >= todayStartOfDay()));
        myConsultations.sort(Comparator.comparing(c -> c.start));

        String dayLimit = DataBase.INSTANCE.settings.findKey("dayLimit").getValue();
        int dayLim;
        try {
            dayLim = Integer.parseInt(dayLimit);
        } catch (Exception e) {
            dayLim = DefaultBasicSettings.DEFAULT_DAY_LIMIT;
        }

        String monthLimit = DataBase.INSTANCE.settings.findKey("monthLimit").getValue();
        int monthLim;
        try {
            monthLim = Integer.parseInt(monthLimit);
        } catch (Exception e) {
            monthLim = DefaultBasicSettings.DEFAULT_MONTH_LIMIT;
        }

        List<DataBase.Users.User> mentors = DataBase.INSTANCE.users.select(u -> u.is_mentor);
        Map<String, DataBase.Users.User> mentorsMap = new HashMap<>();
        for (DataBase.Users.User u : mentors) mentorsMap.put(u.login, u);
        req.setAttribute("mentorsMap", mentorsMap);
        req.setAttribute("consultations", myConsultations);
        req.setAttribute("dayLimit", dayLim);
        req.setAttribute("monthLimit", monthLim);

//        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        resp.sendRedirect("/index.jsp");
        req.getRequestDispatcher("/account_student.jsp").forward(req, resp);

    }
}
