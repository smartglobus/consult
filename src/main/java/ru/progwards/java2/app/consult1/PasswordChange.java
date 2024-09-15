package ru.progwards.java2.app.consult1;

import ru.progwards.java2.app.consult1.DataBase.Users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/password-change")
public class PasswordChange extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pass = req.getParameter("old_password");
        String pass1 = req.getParameter("password1");
        String pass2 = req.getParameter("password2");
        String username = req.getParameter("username");
        String userOn = (String) req.getSession().getAttribute("auth");
        User user = DataBase.INSTANCE.users.findKey(userOn);
//
//        if (pass == null || !pass.equals(user.password)) {
//            req.setAttribute("error-description", "Старый пароль введён неверно.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        if (pass1 == null || !pass1.equals(pass2)) {
//            req.setAttribute("error-description", "Первый и второй варианты пароля не совпадают. Повторите попытку");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        if (pass1.equals(pass)){
//            req.setAttribute("error-description", "Новый пароль не может совпадать со старым.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        if (pass.length() < 5) {
//            req.setAttribute("error-description", "Пароль должен содержать не менее 5 символов.");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
//        if (!userOn.equals(username)) {
//            req.setAttribute("error-description", "Попытка изменить пароль другого пользователя");
//            req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            return;
//        }
        user = DataBase.INSTANCE.users.remove(userOn);
        if (user == null) {
            req.setAttribute("error-description", "Ошибка, пользователь не найден.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        DataBase.INSTANCE.users.put(new User(user.login, pass1, user.name, user.is_mentor, user.image));
        req.setAttribute("user", user.name);
        req.getRequestDispatcher("/pass-change-success.jsp").forward(req,resp);
    }
}
