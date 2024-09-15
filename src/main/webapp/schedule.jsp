<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>schedule</title>
    </jsp:attribute>
    <jsp:body>
<%--        <h2>Schedule</h2>--%>
        <div class="text-md-center m-1 fs-3 fw-semibold">Schedule</div>
        <br><br>
        <%--@elvariable id="appFormat" type="java"--%>
        <c:set scope="page" var="userOn" value="${pageContext.session.getAttribute('auth')}"/>
<%--        <c:set scope="page" var="mentorsMap" value="${pageContext.request.getAttribute('mentorsMap')}"/>--%>
        <c:set scope="page" var="mentorsMap" value="${param.mentorsMap}"/>
        <c:set scope="page" var="mnt" value="${param.mentor}"/>

        <div class="container">
            <div class="row justify-content-between">
                <div class="col-8">
                    <form method="get" action="${pageContext.request.contextPath}/schedule">
                        <label for="mentorSelect"> Наставник
                            <c:choose>
                                <c:when test="${mnt==null||''.equals(mnt)}">
                                    <select id="mentorSelect" class="form form-select" name="mentor">
                                        <option selected value="${null}">все наставники</option>
                                        <c:forEach var="mentor" items="${mentorsMap.values()}">
                                            <option value="${mentor.getLogin()}">${mentor.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="mentorSelect" class="form form-select" name="mentor">
                                        <option selected value="${mnt}">${mentorsMap.get(mnt).getName()}</option>
                                        <option value="${null}">все наставники</option>
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
                                <c:when test="${param.filter==null||'future'.equalsIgnoreCase(param.filter)}">
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="future">будущие</option>
                                        <option value="past">прошедшие</option>
                                        <option value="all">за все время</option>
                                    </select>
                                </c:when>
                                <c:when test="${'past'.equalsIgnoreCase(param.filter)}">
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="past">прошедшие</option>
                                        <option value="future">будущие</option>
                                        <option value="all">за все время</option>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="filter" class="form form-select" name="filter">
                                        <option selected value="all">за все время</option>
                                        <option value="future">будущие</option>
                                        <option value="past">прошедшие</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </label>
                        <input type="submit" value="Применить">
                    </form>
                </div>
                <div class="col-4">
                    <form method="get" action="${pageContext.request.contextPath}/schedule/new">
                        <div class="row">
                            <div class="col-9">
                            <label>Создать элемент расписания для авторизованного наставника (${userOn})
                                <select required name="day_of_week" class="form form-select form-sm">
                                    <option selected disabled value="">выберите день недели</option>
                                    <c:forEach var="day" begin="1" end="7" step="1">
                                        <option value="${day}">${appFormat.getDayOfWeek(day)}</option>
                                    </c:forEach>
                                </select>
                            </label>
                            </div>
                            <div class="col-3">
                                <input type="image" src="images/add_schedule.png" width="70px" alt="add new value"
                                       title="add new value">
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>


        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">mentor</th>
                    <th scope="col">day of week</th>
                    <th scope="col">start time</th>
                    <th scope="col">duration, min</th>
                    <th scope="col">end time</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="rec" items="${schedule}">
                    <tr>
                        <td>${mentorsMap.get(rec.mentor).getName()  }</td>
                        <td>${appFormat.getDayOfWeek(rec.day_of_week)}</td>
                        <td><fmt:formatDate value="${appFormat.getDate(rec.start)}" type="both" timeStyle="short"/></td>
                        <td>${appFormat.getMinutes(rec.duration)}</td>
                        <td><fmt:formatDate value="${appFormat.getDate(rec.start+rec.duration)}" type="time"
                                            timeStyle="short"/></td>
                        <td>
                            <form method="get" action="../schedule/remove"
                                  onsubmit="return confirm('Вы уверены, что хотите удалить этот элемент расписания?')"
                                  style="display: inline">
                                <input type="text" name="id" value="${rec.mentor}&${rec.day_of_week}&${rec.start}"
                                       hidden>
                                <input type="image" class="btn btn-light"
                                       src="${pageContext.request.contextPath}/images/bin.png"
                                       alt="delete" title="delete">
                            </form>
                        </td>
                            <%--                    <td>--%>
                            <%--                        <form method="get" action="settings-edit.jsp" style="display: inline">--%>
                            <%--                            <input type="text" name="name" value="${rec.name}" hidden>--%>
                            <%--                            <input type="text" name="value" value="${rec.value}" hidden>--%>
                            <%--                            <input type="image" src="images/edit-row.png" alt="edit">--%>
                            <%--                        </form>--%>
                            <%--                    </td>--%>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:consAppTmpt>
