package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.progwards.java2.app.consult1.IDbTable.hashSha256;

@WebServlet("/password-change")
public class PasswordChange extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPass = req.getParameter("password1");
        String userOn = (String) req.getSession().getAttribute("auth");
        String securePassword = hashSha256(newPass); // может быть null только при ошибке алгоритма метода

        User user = DataBase.INSTANCE.users.remove(userOn);
        if (user == null || securePassword == null) {
            req.setAttribute("error-description", "Ошибка, пользователь не найден.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        DataBase.INSTANCE.users.put(new User(user.login, securePassword, user.name, user.is_mentor, user.image));
        req.setAttribute("user", user.name);
        req.getRequestDispatcher("/pass-change-success.jsp").forward(req, resp);
    }
}
