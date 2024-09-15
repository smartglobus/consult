package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user_remove")
public class UserRemove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        DataBase.Users.User user = DataBase.INSTANCE.users.remove(login);
        if (user == null) {
            req.setAttribute("error-description", "Удаление не удалось, пользователь не найден.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
// надо ли удалять все консультации ученика или наставника? Или только будущие, для освобождения слотов?
        // надо ли удалять все элементы расписания наставника, или только будущие?
        if (user.is_mentor) resp.sendRedirect("/mentor");
        else resp.sendRedirect("/student");

    }
}
