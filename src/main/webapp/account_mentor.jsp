<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>

<t:consAppTmpt>
    <jsp:attribute name="title">
          <title>my account</title>
    </jsp:attribute>
    <jsp:body>

        <div class="text-md-center  m-1 fs-3 fw-semibold">Consultations</div>
        <br><br>
<%--                длина списка ${consultations.size()}--%>
<%--        mentor = ${pageContext.request.getAttribute("mentor")}--%>
<%--        param.mentor = ${param.mentor}--%>

        <c:set scope="page" var="userOn" value="${pageContext.session.getAttribute('auth')}"/>
        <%--        <c:set scope="page" var="mentorsMap" value="${pageContext.request.getAttribute('mentorsMap')}"/>--%>
        <c:set scope="page" var="mentorsMap" value="${param.mentorsMap}"/>
        <c:set scope="page" var="mnt" value="${mentor}"/>
        <%--@elvariable id="appFormat" type="java"--%>
        <div class="container">
            <div class="text-md-center  m-1 fs-3 fw-semibold">Мои текущие записи</div>
            <div class="row justify-content-between">
                <div class="col-8">
                    <form method="get" action="${pageContext.request.contextPath}/account/mentor">
                        <label for="mentorSelect"> Наставник
                            <c:choose>
                                <c:when test="${mnt=='all'}">
                                    <select id="mentorSelect" class="form form-select" name="mentor">
                                        <option selected value="all">все наставники</option>
                                        <c:forEach var="mentor" items="${mentorsMap.values()}">
                                            <option value="${mentor.getLogin()}">${mentor.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="mentorSelect" class="form form-select" name="mentor">
                                        <option selected value="${mnt}">${mentorsMap.get(mnt).getName()}</option>
                                        <option value="all">все наставники</option>
                                        <c:forEach var="mentor" items="${mentorsMap.values()}">
                                            <c:if test="${!mnt.equals(mentor.getLogin())}">
                                                <option value="${mentor.getLogin()}">${mentor.getName()}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </label>
                        <label for="sortSelect">Порядок сортировки
                            <c:choose>
                                <c:when test="${param.sortDate==null||'ASC'.equalsIgnoreCase(param.sortDate)}">
                                    <select id="sortSelect" class="form form-select" name="sortDate">
                                        <option selected value="ASC">дата по возрастанию</option>
                                        <option value="DESC">дата по убыванию</option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="sortSelect" class="form form-select" name="sortDate">
                                        <option selected value="DESC">дата по убыванию</option>
                                        <option value="ASC">дата по возрастанию</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </label>
                        <label for="filter">Временной интервал
                            <c:choose>
                                <c:when test="${filter==null||'future'.equalsIgnoreCase(filter)}">
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="future">на 7 дней</option>
                                        <option value="past">прошедшие</option>
                                        <option value="today">за сегодня</option>
                                    </select>
                                </c:when>
                                <c:when test="${'past'.equalsIgnoreCase(filter)}">
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="past">прошедшие</option>
                                        <option value="future">на 7 дней</option>
                                        <option value="today">за сегодня</option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="today">за сегодня</option>
                                        <option value="future">на 7 дней</option>
                                        <option value="past">прошедшие</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </label>
                        <input type="submit" value="Применить">
                    </form>
                </div>
<%--                <div class="col-4">--%>
<%--                    <form method="get" action="${pageContext.request.contextPath}/schedule/new">--%>
<%--                        <div class="row">--%>
<%--                            <div class="col-9">--%>
<%--                                <label>Создать элемент расписания для авторизованного наставника (${userOn})--%>
<%--                                    <select required name="day_of_week" class="form form-select form-sm">--%>
<%--                                        <option selected disabled value="">выберите день недели</option>--%>
<%--                                        <c:forEach var="day" begin="1" end="7" step="1">--%>
<%--                                            <option value="${day}">${appFormat.getDayOfWeek(day)}</option>--%>
<%--                                        </c:forEach>--%>
<%--                                    </select>--%>
<%--                                </label>--%>
<%--                            </div>--%>
<%--                            <div class="col-3">--%>
<%--                                <input type="image" src="images/add_setting.bmp" width="100%" alt="add new value"--%>
<%--                                       title="add new value">--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </form>--%>
<%--                </div>--%>

            </div>
        </div>


        <div class="container">

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">mentor</th>
                    <th scope="col">start</th>
                    <th scope="col">duration</th>
                    <th scope="col">student</th>
                    <th scope="col">comment</th>
                    <th scope="col">...delete...</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cons" items="${consultations}">
                    <tr>
                        <td>${mentorsMap.get(cons.mentor).getName()}</td>
                        <td><fmt:formatDate value="${appFormat.getDate(cons.start)}" type="both"
                                            timeStyle="short"/></td>
                        <td>${appFormat.getMinutes(cons.duration)} min.</td>
                        <td>${cons.student}</td>
                        <td>
                            <div class="text-truncate" style="max-width: 120px" title="${cons.comment}">
                                    ${cons.comment}
                            </div>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/consultation/remove"
                                  onsubmit="return confirm('Вы уверены, что хотите удалить эту запись?')"
                                  style="display: inline">
                                <input type="text" name="id" value="${cons.mentor}&${cons.start}"
                                       hidden>
                                <input type="image" class="btn btn-light"
                                       src="${pageContext.request.contextPath}/images/bin.png"
                                       alt="delete" title="delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:consAppTmpt>