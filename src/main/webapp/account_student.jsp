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
        <%--@elvariable id="appFormat" type="java"--%>
        <div class="container">
            <div class="text-md-start  m-1 fs-6 fw-semibold">Лимит на количество консультаций в день: ${dayLimit}</div>
            <div class="text-md-start  m-1 fs-6 fw-semibold">Лимит на количество консультаций в месяц: ${monthLimit}</div>

            <div class="text-md-center  m-1 fs-3 fw-semibold">Мои текущие записи</div>
            <br>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">mentor</th>
                    <th scope="col">start</th>
                    <th scope="col">duration</th>
                    <th scope="col">comment</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cons" items="${consultations}">
                    <tr>
                        <td>${mentorsMap.get(cons.mentor).getName()}</td>
                        <td><fmt:formatDate value="${appFormat.getDate(cons.start)}" type="both"
                                            timeStyle="short"/></td>
                        <td>${appFormat.getMinutes(cons.duration)} min.</td>
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
        <div class="container">
            <div class="row">
                <div class="col">
                    <form  method="get"
                          action="${pageContext.request.contextPath}/consultation/free">
                        <label>Записаться на консультацию
                            <select required class="form-select" name="mentor">
                                <option selected disabled value="">Выберите наставника</option>
                                <c:forEach var="mentor" items="${mentorsMap.keySet()}">
                                    <option value="${mentor}">${mentorsMap.get(mentor).name}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <input class="btn btn-primary" type="submit" value="Вперёд">
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:consAppTmpt>
