<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adminka</title>
</head>
<body>
<h1>Welcome, sir!</h1>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<a href="${pageContext.request.contextPath}/users" class="btn">All users</a>
<a href="${pageContext.request.contextPath}/orders" class="btn">All orders</a>
<a href="${pageContext.request.contextPath}/logout">LOGOUT</a>
</body>
</html>
