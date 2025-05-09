<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
<h2>Courses List</h2>

<c:forEach var="course" items="${courses}">
    <p>
        <b>${course.name}</b> by ${course.instructor}, ${course.credits} credits

        <c:choose>
            <c:when test="${fn:contains(registeredCourseIds, course.courseCode)}">
                <i>You are already registered.</i>
            </c:when>
            <c:otherwise>
                <form action="register" method="post">
                    <input type="hidden" name="courseId" value="${course.courseCode}">
                    <input type="submit" value="Register">
                </form>
            </c:otherwise>
        </c:choose>
    </p>
</c:forEach>

</body>
</html>
