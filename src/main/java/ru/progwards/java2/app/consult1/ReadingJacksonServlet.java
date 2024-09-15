package ru.progwards.java2.app.consult1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/readingJackson")
public class ReadingJacksonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> attributeNames = req.getAttributeNames();
        String atributeName = "";
        while (attributeNames.hasMoreElements())
            atributeName = attributeNames.nextElement();
            System.out.println(atributeName + ": " + req.getAttribute(atributeName));
        HttpSession session = req.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
    }
}
