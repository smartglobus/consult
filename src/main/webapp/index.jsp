<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<t:consAppTmpt>

        <jsp:attribute name="title">
            <title>Главная</title>
        </jsp:attribute>

    <jsp:body>



<%--        fast login--%>
<%--        <form method="get" action="loginServletPage">--%>
<%--            <label>login--%>
<%--                <input type="text" name="login">--%>
<%--            </label><br>--%>
<%--            <label>password--%>
<%--                <input type="text" name="password">--%>
<%--            </label>--%>
<%--            <input class="btn btn-secondary" type="submit" value="Войти">--%>
<%--        </form>--%>
<br>
        <div class="fadeIn first m-3" align="center">
            <img src="images/BachMonogram.jpg" width="150" id="icon"  alt="User's Icon" />
        </div>
        <div id="login">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <form id="login-form" class="form " action="authorise" method="get">
                                <h1 class="text-center text-info">Сервис записи на консультации</h1>
<%--                                <div class="form-group">--%>
<%--                                    <label for="username" class="text-info">Username:</label><br>--%>
<%--                                    <input type="text" name="username" id="username" class="form-control">--%>
<%--                                </div>--%>
<%--                                <div class="form-group">--%>
<%--                                    <label for="password" class="text-info">Password:</label><br>--%>
<%--                                    <input type="text" name="password" id="password" class="form-control">--%>
<%--                                </div>--%>
                                <div class="form-group text-center">
<%--                                    <label for="remember-me" class="text-info"><span>Remember me</span> <span><input id="remember-me" name="remember-me" type="checkbox"></span></label><br>--%>
                                    <input type="submit" class="btn btn-info btn-md m-5" value="Главная">
                                </div>
<%--                                <div id="register-link" class="text-right">--%>
<%--                                    <a href="http://www.ya.ru" class="text-info">Yandex here</a>--%>
<%--                                </div>--%>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>



</t:consAppTmpt>




<%--<%= session.getAttribute("auth")==pageContext.getSession().getAttribute("auth")%>--%>

