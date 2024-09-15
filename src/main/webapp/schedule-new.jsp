<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>chose date</title>
    </jsp:attribute>
    <jsp:body>
        <c:set scope="page" var="userOn" value="${pageContext.session.getAttribute('auth')}"/>
        <%--@elvariable id="appFormat" type="java"--%>
        <div class="container">
            <div class="row justify-content-between">
                <div class="col-4">
                    <form method="get" action="${pageContext.request.contextPath}/schedule/free">
                        <div class="row">
                            <label class="text-md-start mb-1 fs-6 fw-semibold">Выбрать дату консультаций для (${userOn})
                                <select required name="date" class="form form-select form-sm w-auto">
                                    <option selected disabled value="">выберите дату</option>
                                    <c:forEach var="date" items="${dates}">
                                        <option value="${date}">${appFormat.getDayOfWeek(day_of_week)}, <fmt:formatDate
                                                value="${appFormat.getDate(date)}" dateStyle="short"/></option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div >
                        <div class="text-md-start mb-1 fs-6 fw-semibold">время начала консультаций</div>
                        <div class="row" >

                            <div class="col-3" style="padding-right: 0 ">

                                <select required name="hour" class="form form-select form-sm">
                                    <c:forEach var="hour" begin="8" end="21"
                                               step="1"><%--отсюда ограничение на длину консультации 3 часа (с 21 до 24 ч.)--%>
                                        <option value="${hour}"> ${hour}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-3" style="padding-left: 0">
                                <select required name="minutes" class="form form-select form-sm mb-4">
                                    <option>00</option>
                                    <option>30</option>
                                </select>
                            </div>
                            <div class="col">
                                <input class="btn btn-light btn-md" type="submit" title="Next" value="Далее">

                            </div>
                        </div>
                    </form>
                    <a class="btn btn-info btn-md" href="javascript:history.back()">
                        Назад
                    </a>
                </div>
            </div>
        </div>

    </jsp:body>
</t:consAppTmpt>
