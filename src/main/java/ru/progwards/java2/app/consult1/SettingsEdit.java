package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/settings-edit")
public class SettingsEdit extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        {
            System.out.println("\nSettingsEdit - Параметры:");
            Enumeration<String> params = req.getParameterNames();
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                System.out.println(name + ": " + req.getParameter(name));
            }
        }
        String name = req.getParameter("name");
        String value = req.getParameter("value");

        // если параметр value отсутствует в запросе, обращение трактуется как запрос на удаление настройки
        if (value == null) {
            DataBase.INSTANCE.settings.remove(name);
            resp.sendRedirect("/settings");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error-description", "В запросе отсутствует название настройки");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (name.equals("dayLimit") || name.equals("monthLimit")) {
            try {
                int newValue = Integer.parseInt(value);
                if (newValue < 0) throw new IllegalArgumentException("newValue < 0");
            } catch (Exception e) {
                req.setAttribute("error-description", "Настройки dayLimit и monthLimit должны содержать целое количество дней ≥0.");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                return;
            }
        }
        // проверка на длину консультации, так как на странице расписания в разделе создания новой записи выбор начала
        // пула консультаций ограничен 21.00. Консультация > 3 часов выйдет за полночь, что недопустимо.
        if (name.equals("slot_duration")) {
            try {
                long newValue = Long.parseLong(value);
                if (newValue >= 3_600_000 * 3) throw new IllegalArgumentException("newValue >= 3 hours");
            } catch (Exception e) {
                req.setAttribute("error-description", "Значение настройки \"slot_duration\" не распознано.\n " +
                        "Оно должно быть в миллисекундах и не превышать 3 часов (10_800_000).");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                return;
            }
        }


        if (value.trim().isEmpty()) {
            req.setAttribute("error-description", "Значение параметра " + name + " не может быть пустым.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            System.out.println("req address: " + req.getRequestURI());
            return;
        }

        DataBase.INSTANCE.settings.remove(name);
        DataBase.INSTANCE.settings.put(new DataBase.Settings.Record(name, value));
        resp.sendRedirect("/settings");
    }
}
