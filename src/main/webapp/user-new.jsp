<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>new user</title>
    </jsp:attribute>
    <jsp:body>

        <div class="container">
            <div class="text-md-start  m-1 fs-3 fw-semibold">Создание нового пользователя</div>
            <form method="get" action="${pageContext.request.contextPath}/user_new" class="m-4">
                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="login">логин</label></div>
                    <input class="mb-2" required id="login" type="text" name="login">

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="pass">пароль</label></div>
                    <input class="mb-2" required id="pass" type="text" name="password">

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="name">имя</label></div>
                    <input class="mb-2" required id="name" type="text" name="name">

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="is_mentor">тип пользователя</label></div>
                    <select required class="form-select w-auto mb-2" id="is_mentor" name="is_mentor">
                        <option disabled selected>выберите тип</option>
                        <option value="true">наставник</option>
                        <option value="false">ученик</option>
                    </select>

                <div class="text-md-start mb-1 fs-6 fw-semibold"><label for="image">фото (имя файла) </label></div>
                    <input class="mb-2" id="image" type="text" name="image">

                <p><input class="btn btn-primary" type="submit" value="Создать"></p>
            </form>
            <a class="btn btn-info btn-md" href="javascript:history.back()">
                Назад
            </a>
        </div>

    </jsp:body>
</t:consAppTmpt>