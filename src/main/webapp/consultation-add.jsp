<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:consAppTmpt>
    <jsp:attribute name="title">
        <title>book consultation</title>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="appFormat" type="java"--%>
        <div class="container">
            <div class="text-md-center  m-1 fs-5 fw-semibold">Выберите время консультации с ${mentor}</div>
            <br>

            <form onsubmit="return validateSelects()" method="get"
                  action="${pageContext.request.contextPath}/consultation/add">
                <table class="table table-borderless " style="width: 100%; table-layout: fixed;text-align: center">
                    <thead>
                    <tr>
                        <c:forEach var="day" begin="0" end="6" step="1">
                            <th scope="col" class="col-1"> ${appFormat.getDayOfWeek(days[day])}</th>
                        </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach var="day" begin="0" end="6" step="1">
                            <th><fmt:formatDate value="${dates[day]}" type="date" dateStyle="short"/></th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <c:forEach var="day" begin="0" end="6" step="1">
                            <td>
                                <style>
                                    select.form-select {
                                        overflow: hidden;
                                    }

                                    select.form-select:focus {
                                        overflow: auto;
                                    }
                                </style>

                                <select size="10" name="id" class="form form-select "
                                        onchange="handleSelectChange(this)">

                                    <option value="" selected disabled>&nbsp;</option>
                                        <%-- &nbsp; вставлен для невидимого заполнения строки. Иначе пустое окно укорачивается --%>
                                    <c:set var="day_slots" value="${freeSlotsArray.get(day)}"/>
                                    <c:forEach var="slot" items="${day_slots}">
                                        <option value="${slot.mentor}&${slot.start}">
                                            <fmt:formatDate value="${appFormat.getDate(slot.start)}" type="time"
                                                            timeStyle="short"/> - <fmt:formatDate
                                                value="${appFormat.getDate(slot.start + slot.duration)}" type="time"
                                                timeStyle="short"/>
                                        </option>
                                    </c:forEach>

                                </select>
                            </td>
                        </c:forEach>
                    </tr>
                    </tbody>
                </table>
                    <%--                <label>Ваш комментарий по теме консультации:--%>
                    <%--                    <input type="text" name="comment">--%>
                    <%--                </label>--%>
                <div class="mb-sm-2">
                        <%--                    <label for="exampleFormControlTextarea1" class="form-label">Ваш комментарий по теме консультации:</label>--%>
                    <label style="width:40%; max-width:500px;">Ваш комментарий по теме консультации:
                        <textarea name="comment" class="form-control" rows="3"></textarea>
                    </label>
                </div>
                <input type="submit" value="Записаться">
            </form>

            <script>
                function handleSelectChange(selectedElement) {
                    // Получаем все select элементы на странице
                    const selects = document.querySelectorAll('select[name="id"]');

                    // Проходим по всем select и сбрасываем значение, если это не тот select, который был изменен
                    selects.forEach((select) => {
                        if (select !== selectedElement) {
                            select.value = ''; // Сброс значения
                        }
                    });
                }

                function validateSelects() {
                    const selects = document.querySelectorAll('select[name="id"]');
                    let isSelected = false;

                    // Проверяем, выбран ли хоть один элемент
                    selects.forEach((select) => {
                        if (select.value !== '' && select.value !== null) {
                            isSelected = true;
                        }
                    });

                    // Если ни один select не выбран, выводим предупреждение
                    if (!isSelected) {
                        alert("Пожалуйста, выберите хотя бы одно значение.");
                        return false; // Отмена отправки формы
                    }

                    return true; // Разрешить отправку формы
                }
            </script>

        </div>
    </jsp:body>

</t:consAppTmpt>
<%--${freeSlotsArray.get(0).size()}--%>
<%--${freeSlotsArray.get(1).size()}--%>
<%--${freeSlotsArray.get(2).size()}--%>
<%--${freeSlotsArray.get(3).size()}--%>
