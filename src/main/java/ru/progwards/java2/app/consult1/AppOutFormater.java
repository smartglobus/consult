package ru.progwards.java2.app.consult1;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class AppOutFormater {

    public static Date getDate(String date) {
        long d;
        try {
            d = Long.parseLong(date);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Date(d);
    }

    public static int getMinutes(long ms) {
        return (int) (ms / (60 * 1000));
    }

    public static String getDayOfWeek(int d) {
        if (d < 1 || d > 7)
            return "wrong day_of_week number";
        return DayOfWeek.of(d).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }


}
