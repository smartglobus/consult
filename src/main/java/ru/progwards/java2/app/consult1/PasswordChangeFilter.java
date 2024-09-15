package ru.progwards.java2.app.consult1;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PasswordChangeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String pass = req.getParameter("old_password");
        String pass1 = req.getParameter("password1");
        String pass2 = req.getParameter("password2");
        String username = req.getParameter("username");
        String userOn = (String) ((HttpServletRequest)req).getSession().getAttribute("auth");
        DataBase.Users.User user = DataBase.INSTANCE.users.findKey(userOn);

        if (!userOn.equals(username)) {
            req.setAttribute("error-description", "Попытка изменить пароль другого пользователя");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (pass == null || !pass.equals(user.password)) {
            req.setAttribute("error-description", "Старый пароль введён неверно.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (pass1 == null || !pass1.equals(pass2)) {
            req.setAttribute("error-description", "Первый и второй варианты пароля не совпадают. Повторите попытку");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (pass1.equals(pass)){
            req.setAttribute("error-description", "Новый пароль не может совпадать со старым.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }
        if (pass1.length() < 5) {
            req.setAttribute("error-description", "Пароль должен содержать не менее 5 символов.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
