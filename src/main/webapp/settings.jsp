<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:consAppTmpt>
    <jsp:attribute name="title">
         <title>settings</title>
    </jsp:attribute>

    <jsp:body>
        <%--@elvariable id="appFormat" type="java"--%>

        <div class="text-md-center  m-1 fs-3 fw-semibold">Settings</div>
        <br><br>
        <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">setting</th>
                <th scope="col">value</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="rec" items="${settingsList}">
                <tr>
                    <td>${rec.name}</td>

<%--                    <script>--%>
<%--                        alert()--%>
<%--                        confirm()--%>
<%--                        // function confirm(msg){--%>
<%--                        //--%>
<%--                        // }--%>
<%--                    </script>--%>

                    <c:choose>
                        <c:when test="${(rec.name).equals('slot_duration')}">
                            <td>${rec.value}ms (${appFormat.getMinutes(rec.value)} min.)</td>
                        </c:when>
                        <c:otherwise>
                            <td>${rec.value}</td>
                        </c:otherwise>
                    </c:choose>

                    <td>
                        <form method="post" action="settings-edit" onsubmit="return confirm('Вы уверены, что хотите удалить эту настройку?')" style="display: inline">
                            <input type="text" name="name" value="${rec.name}" hidden>
                            <input class="btn btn-light" type="image" src="images/bin.png" alt="delete" title="delete">
                        </form>
                    </td>
                    <td>
                        <form method="get" action="settings-edit.jsp" style="display: inline">
                            <c:set scope="request" var="edit" value="true"/>
                            <input type="text" name="edit" value="true" hidden>
                            <input type="text" name="name" value="${rec.name}" hidden>
                            <input type="text" name="value" value="${rec.value}" hidden>
                            <input class="btn btn-light" type="image" src="images/edit-row.png" alt="edit" title="edit">
                        </form>
                    </td>
                </tr>

            </c:forEach>
            </tbody>
        </table>

        <form method="post" action="settings-edit.jsp" style="display: inline">
            <input type="text" name="name" value="enter name" hidden>
            <input type="text" name="value" value="enter value" hidden>
            <input  type="image" src="images/setting_add.png" width="50px" alt="Add new setting" title="Add new setting">
        </form>
        </div>
    </jsp:body>
</t:consAppTmpt>
<%--<html>--%>
<%--<head>--%>

<%--    <link href="//cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <script src="//cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>--%>
<%--</head>--%>
<%--<body>--%>


<%--</body>--%>
<%--</html>--%>