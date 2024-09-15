package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Settings.Record;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet("/settings")
public class SettingsPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DataBase.Settings.Record> settings = new ArrayList<Record>(DataBase.INSTANCE.settings.getAll());
        settings.sort((o1, o2) -> o1.name.compareTo(o2.name));
        req.setAttribute("settingsList", settings);
        req.getRequestDispatcher("settings.jsp").forward(req,resp);
    }
}
