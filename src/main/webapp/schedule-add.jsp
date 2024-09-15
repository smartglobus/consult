<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>add schedule value</title>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="appFormat" type="java"--%>
        <div class="container">
        <form method="get" action="${pageContext.request.contextPath}/schedule/add" >
            <div class="text-md-start m-1 fs-5 fw-semibold">
                Начало консультаций: ${appFormat.getDayOfWeek(day_of_week)},
                <fmt:formatDate value="${appFormat.getDate(start)}" type="both" timeStyle="short"/>
            </div>
            <label  class="text-md-start m-1 fs-6 fw-semibold">Время окончания консультаций<br>
                <select required name="valueEnd" class="form form-select w-75 mb-4">
                    <c:forEach var="slot" items="${freeSlots}">
                        <option value="${slot}"><fmt:formatDate value="${appFormat.getDate(slot)}" type="time" timeStyle="short"/></option>
                    </c:forEach>
                </select>
            </label>
            <input type="text" name="valueStart" value="${start}" hidden>
            <input type="text" name="dayOfWeek" value="${day_of_week}" hidden>
            <input type="submit" value="Создать">
        </form>
            <a class="btn btn-info btn-md" href="javascript:history.back()" >
                Назад
            </a>
        </div>

    </jsp:body>
</t:consAppTmpt>


