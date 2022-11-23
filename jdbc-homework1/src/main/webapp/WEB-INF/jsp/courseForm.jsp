<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>강의 등록</title>
</head>
<body>
<h1>강의 등록 페이지</h1>
    <form method="post" action="/create">
        강사명: <input type="text" name="teacherName" /><br />
        과목명: <input type="text" name="subjectName" /><br />
        <br />
        <input type="submit" />
    </form>
</body>
</html>
