package ru.progwards.java2.app.consult1;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static ru.progwards.java2.app.consult1.AppEngine.*;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("appFormat", new AppOutFormater());


        update();

//        sce.getServletContext().setAttribute("GoHomeHour", SettingsEnum.GoHomeHour.getSetting_name());
        // запуск метода
        new Thread(new Runnable() {
            int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
            @Override
            public void run() {
                while (true) {
                    // запуск метода программы организации расписания, который в полночь создаёт новые
                    // слоты на 7-й день вперёд, а также реагирует на появление новых элеменов расписания
                    // в ближайшие 7 дней, включая текущий
                    int dayOfWeekNow = LocalDate.now().getDayOfWeek().getValue();
                    if (dayOfWeekNow != dayOfWeek) {
                        update();
                        dayOfWeek = dayOfWeekNow;
                    }

                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
