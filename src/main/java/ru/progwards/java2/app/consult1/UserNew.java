package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static ru.progwards.java2.app.consult1.IDbTable.hashSha256;

@WebServlet("/user_new")
public class UserNew extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String is_mentor = req.getParameter("is_mentor");
        String image = req.getParameter("image");

        if (login == null || password == null || name == null || is_mentor == null) {
            req.setAttribute("error-description", "В запросе отсутствуют необходимые параметры.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (login.length() < 3 || password.length() < 5 || name.length() < 3) {
            req.setAttribute("error-description", "Имя пользователя и логин должны содержать не менее 3 символов, " +
                    "пароль - не менее 5 символов.<br>" +
                    "Убедитесь, что введённые вами значения соответствуют требованиям.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        boolean isMentor;
        if ("true".equals(is_mentor)) {
            isMentor = true;
        } else if ("false".equals(is_mentor)) {
            isMentor = false;
        } else {
            req.setAttribute("error-description", "Ошибочный тип пользователя.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        String securePassword = hashSha256(password);
        System.out.println("securePassword: "+ securePassword);

        String encSafeName = new String(name.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        User newUser = new User(login, securePassword, encSafeName, isMentor, image);
        if (!DataBase.INSTANCE.users.exists(login)){
            DataBase.INSTANCE.users.put(newUser);
        }else {
            req.setAttribute("error-description", "Ошибка обращения к базе данных. Не удалось добавить нового пользователя.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        if (isMentor) resp.sendRedirect("/mentors");
        else resp.sendRedirect("/students");
    }
}
