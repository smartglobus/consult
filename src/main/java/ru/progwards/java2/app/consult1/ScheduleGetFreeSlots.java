package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Schedule.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/schedule/free")
public class ScheduleGetFreeSlots extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dateSelected = req.getParameter("date");
        String hour = req.getParameter("hour");
        String minutes = req.getParameter("minutes");
        long date;
        int h;
        int mins;

        // проверка параметров запроса
        if (dateSelected == null || hour == null || minutes == null) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        try {
            date = Long.parseLong(dateSelected);
            h = Integer.parseInt(hour);
            mins = Integer.parseInt(minutes);
        } catch (NumberFormatException e) {
            req.setAttribute("error-description", "Не распознаны параметры запроса.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        int day_of_week = LocalDateTime.ofInstant(Instant.ofEpochMilli(date),ZoneId.systemDefault()).getDayOfWeek().getValue();

        DataBase.Settings.Record slotDur = DataBase.INSTANCE.settings.findKey("slot_duration");
        long slotDuration = slotDur == null ? 45 * 60 * 1000 : Long.parseLong(slotDur.value);// 45 минут, если настройка не найдена

        String mentorOn = (String) req.getSession().getAttribute("auth");

        long newValueStart = date + h * (60 * 60 * 1000) + mins * (60 * 1000);
        long theDayEnd = date + (24 * 60 * 60 * 1000);// можно сделать настройку "окончание рабочего дня"


        List<Long> freeSlots = new ArrayList<>();// список времён потенциального окончания нового Value
        List<Value> values = new ArrayList<>(DataBase.INSTANCE.schedule.select(v -> (v.mentor.equals(mentorOn) &&
                v.start >= date && v.start <= theDayEnd)));
        values.sort(Comparator.comparingLong((Value v) -> v.start));
        long rightLimit = theDayEnd;
        // если в этот день уже есть другие ЭР, окончание нового ЭР с заданным временем начала (h и mins)
        // может быть выбрано не позже, чем время начала ближайшего следующего из уже существующих ЭР
        for (Value v : values) {
            if ((newValueStart + slotDuration) > v.start && newValueStart < (v.start + v.duration)) {
                req.setAttribute("error-description", "Выбранное время старта консультаций пересекается с уже существующей записью.");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                return;
            }
            if (newValueStart < v.start) {
                rightLimit = v.start;
                break;
            }
        }

        for (long i = newValueStart + slotDuration; i <= rightLimit; i += slotDuration) {
            freeSlots.add(i);
        }

        req.setAttribute("start", newValueStart);
        req.setAttribute("day_of_week", day_of_week);
        req.setAttribute("freeSlots", freeSlots);
        req.getRequestDispatcher("/schedule-add.jsp").forward(req, resp);
    }

    // полночь ближайшего будущего dayOfWeek
    public static long getStartOfDay(int dayOfWeek) {
        LocalDateTime date = LocalDate.now().atStartOfDay();
        int day = date.getDayOfWeek().getValue();
        while (day != dayOfWeek) {
            date = date.plusDays(1);
            day = date.getDayOfWeek().getValue();
        }
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}


