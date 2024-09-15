<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>schedulePage</title>
    </jsp:attribute>
    <jsp:body>
        <h2>Schedule</h2>
        Выберите, чьё расписание вы хотите увидеть
        <div>

            <form method="get" action="schedule/list">
                <select name="mentor">
                    <c:set scope="page" var="userOn" value="${pageContext.session.getAttribute('auth')}"/>
                    <c:set scope="page" var="mentorsMap" value="${pageContext.request.getAttribute('mentorsMap')}"/>

                    <option selected value="${userOn}">${mentorsMap.get(userOn).getName()}</option>
                    <option value="${null}">все наставники</option>
                    <c:forEach var="mentor" items="${mentorsMap.values()}">
                        <c:if test="${!userOn.equals(mentor.getLogin())}">
                            <option value="${mentor.getLogin()}">${mentor.getName()}</option>
                        </c:if>
                    </c:forEach>

                </select>

                <input type="submit" value="Далее">
            </form>
        </div>
    </jsp:body>
</t:consAppTmpt>

