<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>강의 수정</title>
</head>
<body>
<h1>강의 수정 페이지</h1>
    <form method="post" action="/courses/${course.courseId}">
        <input type="hidden" name="_method" value="put"/>
        강사명: <input type="text" name="teacherName" value="${course.teacherName}"/><br />
        과목명: <input type="text" name="subjectName" value="${course.subjectName}"/><br />
        <br />
        <input type="submit" />
    </form>
</body>
</html>
