<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>error</title>
    </jsp:attribute>
    <jsp:body>
        <style>
            #error {
                color: teal;
                font-size: 25px;
            }
        </style>
        <div align="center">
            <div id="error" ><h1><b>Error</b></h1></div>

          <h3>${requestScope.get("error-description")}</h3>
        </div>
        <div class="d-grid gap-2 col-2 mx-auto">
            <a class="btn btn-info btn-md" href="javascript:history.back()" >
                Назад
            </a>
        </div>
    </jsp:body>
</t:consAppTmpt>
<html>
    <head>


    </head>
    <body>

    </body>
</html>