<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>successful password change</title>
    </jsp:attribute>
    <jsp:body>
        <div class="container d-flex justify-content-center">
            <div class="text-md-center  m-1 fs-3 fw-semibold">Пароль пользователя <span
                    style="color: teal">${user}</span> успешно изменён.
            </div>
        </div>
        <div class="container d-flex justify-content-center ">
                <img class="img " src="${pageContext.request.contextPath}/images/success.png" alt="Done!">
            <br>
        </div>
        <div class="container d-flex justify-content-center">
            <div >
                <form class="btn-primary" method="get" action="${pageContext.request.contextPath}/account">
                    <button class="btn  btn-success" style="color: #9ed781; ">На главную</button>
                </form>
            </div>
        </div>


    </jsp:body>
</t:consAppTmpt>