package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Consultations.Consultation;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppEngine {

    public static void update() {
        String slot_duration = DataBase.INSTANCE.settings.findKey("slot_duration").getValue();
        long slotDuration;
        try {
            slotDuration = Long.parseLong(slot_duration);
        } catch (Exception e) {
            slotDuration = DefaultBasicSettings.DEFAULT_SLOT_DURATION;
        }


        List<DataBase.Schedule.Value> schedule7daysOn = DataBase.INSTANCE.schedule.select(v ->
                v.start >= todayStartOfDay() && v.start < (todayStartOfDay() + TimeUnit.DAYS.toMillis(7)));

        for (DataBase.Schedule.Value sv : schedule7daysOn) {
            long scheduleValueStart = sv.start;
            long scheduleValueEnd = sv.start + sv.duration;

            // список слотов консультаций наставника sv.mentor, пересекающихся по времени с элементом расписания
            List<Consultation> currentConsultations = DataBase.INSTANCE.consultations.select(c -> (c.mentor.equals(sv.mentor) &&
                    c.start < scheduleValueEnd && (c.start + c.duration) > scheduleValueStart));
            // если ни одного слота не обнаружено, генерируются новые пустые слоты на период заданного элемента расписания
            if (currentConsultations.isEmpty()) {
                for (long i = scheduleValueStart; i <= scheduleValueEnd - slotDuration; i += slotDuration) {
                    try {
                        DataBase.INSTANCE.consultations.put(new Consultation(sv.mentor, i, slotDuration, "", ""));
                    } catch (IOException e) {
                        System.out.println("Log: Не удалось создать слот консультации на дату " + new Date(i));
                    }
                }
            }
        }

        System.out.println("Checking midnight update time...      time now is: " + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME));
    }

    public static int getMyMonthLimit(String student, long theDay) {
        long theDayStart = LocalDate.ofInstant(Instant.ofEpochMilli(theDay), ZoneId.systemDefault())
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String monthLimit = DataBase.INSTANCE.settings.findKey("monthLimit").getValue();

        int lim;
        try {
            lim = Integer.parseInt(monthLimit);
        } catch (Exception e) {
            lim = DefaultBasicSettings.DEFAULT_MONTH_LIMIT;
        }
        List<Consultation> myCons = DataBase.INSTANCE.consultations.select(c -> (c.student.equals(student) &&
                c.start >= (theDayStart - TimeUnit.DAYS.toMillis(29)) && c.start < (theDayStart + TimeUnit.DAYS.toMillis(1))));
        return lim - myCons.size();
    }

    public static int getMyDayLimit(String student, long theDay) {
        long theDayStart = LocalDate.ofInstant(Instant.ofEpochMilli(theDay), ZoneId.systemDefault())
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String dayLimit = DataBase.INSTANCE.settings.findKey("dayLimit").getValue();

        int lim;
        try {
            lim = Integer.parseInt(dayLimit);
        } catch (Exception e) {
            lim = DefaultBasicSettings.DEFAULT_DAY_LIMIT;
        }
        List<Consultation> myCons = DataBase.INSTANCE.consultations.select(c -> (c.student.equals(student) &&
                c.start >= theDayStart && c.start < (theDayStart + TimeUnit.DAYS.toMillis(1))));
        return lim - myCons.size();
    }

    public static long todayStartOfDay() {
        return LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static int getDayOfWeek(long date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()).getDayOfWeek().getValue();
    }

    public static void main(String[] args) {
        update();
//        long date = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        System.out.println(LocalDate.ofInstant(Instant.ofEpochMilli(date),ZoneId.systemDefault()).atStartOfDay());
    }
}
