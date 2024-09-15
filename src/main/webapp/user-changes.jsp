<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>password change</title>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="text-md-start  m-1 fs-3 fw-semibold">Смена пароля пользователя</div>

            <form method="get" action="${pageContext.request.contextPath}/password-change" class="m-4">
                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="pass">Введите старый пароль</label></div>
                <input class="mb-2" required id="pass" type="password"  name="old_password">

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="pass1">Введите новый пароль</label></div>
                <input class="mb-2" required id="pass1" type="password"  name="password1">

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="pass2">повторите ввод</label></div>
                <input class="mb-2" required id="pass2" type="password" name="password2">

                <input type="text" name="username" value="${pageContext.session.getAttribute('auth')}" hidden>

                <p><input class="btn btn-primary" type="submit" value="Подтвердить"></p>
            </form>
            <a class="btn btn-info btn-md" href="javascript:history.back()">
                Назад
            </a>
        </div>

    </jsp:body>
</t:consAppTmpt>