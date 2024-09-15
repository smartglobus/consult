package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setting-delete")
public class wstSettingDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String settingName = req.getParameter("name");
//        if (settingName == null || settingName.trim().isEmpty()) {
//            req.setAttribute("error-description", "Не указано названия настройки.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        if (DataBase.INSTANCE.settings.remove(settingName) == null) {
//            req.setAttribute("error-description", "Настройка не найдена.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        resp.sendRedirect("/settings");
    }
}
