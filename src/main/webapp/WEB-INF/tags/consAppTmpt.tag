<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="tag for consult app" pageEncoding="utf-8" %>
<%@ attribute name="title" fragment="true" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:invoke fragment="title"/>

    <c:set var="nameOn" value="${pageContext.session.getAttribute('auth')}"/>

    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="d-flex w-100"> <!-- Применяем Flexbox -->
                    <div class="navbar-nav">
                        <a class="nav-link active" aria-current="page" href="https://progwards.ru/">Progwards</a>
                        <c:if test="${!''.equals(pageContext.session.getAttribute('auth'))}">
                            <a class="nav-link" href="${pageContext.request.contextPath}/account">Консультации</a>
                        </c:if>
                        <c:if test="${'true'.equals(pageContext.session.getAttribute('is_mentor'))}">
                            <form method="get" action="${pageContext.request.contextPath}/schedule">
                                <input type="text" name="mentor" value="${pageContext.session.getAttribute('auth')}"
                                       hidden>
                                <input type="submit" class="nav-link" value="Расписание">
                            </form>
                            <a class="nav-link" href="${pageContext.request.contextPath}/mentors">Наставники</a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/students">Ученики</a>
                            <a class="nav-link" href="${pageContext.request.contextPath}/settings">Настройки</a>
                        </c:if>
                    </div>
                    <div class="ms-auto d-flex"> <!-- Теперь этот блок сместится вправо -->
                        <c:if test="${!''.equals(nameOn)}">
                            <a class="nav-link m-1 fs-5 fw-semibold">${nameOn}</a>
                            <a class="nav-link m-2" href="${pageContext.request.contextPath}/logoff">Logout</a>
                            <a class="nav-link ms-1" href="${pageContext.request.contextPath}/user-changes.jsp">
                                <img src="${pageContext.request.contextPath}/images/profile_change.png" style="width: 30px" alt="add new user">
                            </a>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>
    </nav>


    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="//cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:doBody/>
</body>
</html>