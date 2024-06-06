<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>consult</title>
<style>
    table, td {
    border-width: 2;
    border-collapse: collapse;

    border: 2px solid black ;
        background-color: azure;
    }
    #table1{
    width:80%;

    }
    #left{
    width: 70%;
    }
</style>
</head>
<body>
<h2>Hello World!!!</h2>
<table   id="table1">
    <tr>
        <td align="center" id="left">
            <h4>first line of the table</h4>
        </td>
        <td align="center" id="right">
            <h4>second line of the table</h4>
        </td>
    </tr>
</table>
<ol>
    <li>line1</li>
    <li>line2</li>
    <li>line3</li>
     <li>line4</li>
</ol>
<br><br>
<form method="get" action="index">
<input type="text" name="login">
<input type="submit" value="Поехали!">
</form>
</body>

 <h2 align="center">Пользователь не найден.</h2><br><br>

        <form method="get" action="login">
            <input type="submit" value="Вернуться назад">
        </form>


</html>