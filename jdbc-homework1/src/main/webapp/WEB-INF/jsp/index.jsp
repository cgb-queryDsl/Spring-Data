<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>강의 관리 페이지</title>
</head>
<body>
<h1>강의 관리 페이지</h1>
<table>
    <thead>
    <tr>
        <th>강사명</th>
        <th>과목명</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${courses}" var="course">
        <tr>
            <td><c:out value="${course.teacherName}"/></td>
            <td><c:out value="${course.subjectName}"/></td>
            <td>
                <form action="/update/${course.courseId}" method="get">
                    <button type="submit">수정</button>
                </form>
            </td>
            <td>
                <form action="/courses/${course.courseId}" method="post">
                    <input type="hidden" name="_method" value="delete">
                    <button type="submit">삭제</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>


<a href="/create">등록하기</a><br/>
<a href="/login">로그인</a><br/>
</body>
</html>
