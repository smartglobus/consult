package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/authorise")
public class AuthoriseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        //отладка
        System.out.println("AuthoriseServlet: session old maxInactiveInterval = " + session.getMaxInactiveInterval());
        System.out.println("AuthoriseServlet: session auth attribute = " + session.getAttribute("auth"));

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        DataBase.Users.User user = DataBase.INSTANCE.users.findKey(username);
        if (user == null) {
            req.setAttribute("error-description", "Пользователь с именем " + username + " не найден.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (!password.equals(user.getPassword())) {
            req.setAttribute("error-description", "Введённый пароль для Пользователя с именем " + username
                    + "\n не совпадает с сохранённым в системе.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        DataBase.Settings.Record maxInactiveInterval = DataBase.INSTANCE.settings.findKey("MAX_INACTIVE_INTERVAL(sec.)");
        if (maxInactiveInterval != null) {
            int interval = Integer.parseInt(maxInactiveInterval.getValue());
            System.out.println("AuthoriseServlet: interval = " + interval);//отладка
            session.setMaxInactiveInterval(interval);
        }

        session.setAttribute("auth", username);
        session.setAttribute("is_mentor", user.is_mentor ? "true" : "false");

        //отладка
        System.out.println("AuthoriseServlet: session new maxInactiveInterval = " + session.getMaxInactiveInterval());

        resp.sendRedirect("/account");
    }
}
