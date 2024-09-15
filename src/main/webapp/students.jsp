<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>students</title>
    </jsp:attribute>
    <jsp:body>
        <div class="text-md-center  m-1 fs-3 fw-semibold">Students</div>
        <div class="row justify-content-end">
            <div class="col-2"><form action="user-new.jsp">
                <input type="image"  src="${pageContext.request.contextPath}/images/add-user.png" width="30%"  alt="add new user">
                </form>
            </div>
        </div>
        <div class="container">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>login</th>
                    <th>name</th>
                    <th></th>
                </tr>

                </thead>
                <tbody>
                <c:forEach var="mentor" items="${students}">
                    <tr>
                        <td>${mentor.login} </td>
                        <td>${mentor.name} </td>
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/user_remove"
                                  onsubmit="return confirm('Вы уверены, что хотите удалить этого пользователя?')"
                                  style="display: inline">
                                <input type="text" name="login" value="${mentor.login}"
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
