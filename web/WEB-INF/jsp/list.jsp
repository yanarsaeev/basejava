<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Title</title>
</head>
<body>
    <jsp:include page="fragments/header.jsp" />
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Имя</th>
                <th>Email</th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <tr>
                    <td><a href="resume?uuid=${resume.getUuid()}">${resume.getFullName()}</a></td>
                    <td>${resume.getContact(ContactType.MAIL)}</td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp" />
</body>
</html>
