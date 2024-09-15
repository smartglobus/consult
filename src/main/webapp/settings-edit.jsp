<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--<html>--%>
<%--    <head>--%>
<%--        --%>
<%--        --%>

<%--    </head>--%>
<%--    <body>--%>

<%--    --%>
<%--    </body>--%>
<%--</html>--%>
<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>edit settings</title>
    </jsp:attribute>
    <jsp:body>
        <%--        <form method="post" action="settings-edit" style="text-align: center">--%>
        <%--            <p><label>Название параметра <input class="form" type="text" name="name" value="${param.name}" readonly></label></p>--%>
        <%--            <label>Значение параметра (≥0) <input  type="text" name="value" value="${param.value}"></label>--%>
        <%--            <input class="btn btn-secondary" type="submit" value="Сохранить">--%>
        <%--        </form>--%>

        <%--<br><br>--%>
        <div class="fadeIn first" align="center">
            <img src="images/BachMonogram.jpg" id="icon" alt="User's Icon"/>
        </div>
        <div id="login">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <form id="login-form" class="form" action="settings-edit" method="post">
                                <h3 class="text-center text-info">Enter new value</h3>
                                <div class="form-group">
                                    <label for="name" class="text-info">Название параметра: </label><br>
                                    <c:choose>
                                        <c:when test="${param.edit.equals('true')}">
                                            <input type="text" name="name" id="name" class="form-control"
                                                   value="${param.name}" readonly>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" name="name" id="name" class="form-control"
                                                   value="${param.name}">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="form-group">
                                    <label for="value" class="text-info">Значение параметра (≥0): </label><br>
                                    <input type="text" name="value" id="value" class="form-control"
                                           value="${param.value}">
                                </div>
                                <div class="form-group">
<%--                                    <label for="remember-me" class="text-info"><span>Remember me</span> <span><input--%>
<%--                                            id="remember-me" name="remember-me" type="checkbox"></span></label><br>--%>
                                    <br><input type="submit" class="btn btn-info btn-md" value="Сохранить">

                                    <a class="btn btn-info btn-md" href="javascript:history.back()" >
                                        Отмена
                                    </a>
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