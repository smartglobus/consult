<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <title>errror</title>
    </head>
    <body>
    <%@ page import="java.util.Date" %>
    Hello! Time now:  <%= new Date() %>
        <div align="center" style="font-size:25px; color:teal">
           <h2> <%= request.getAttribute("username") %></h2><br>
            <form  action="login">
                <input type="submit" value="Назад">
            </form>
        </div>
    </body>
</html>