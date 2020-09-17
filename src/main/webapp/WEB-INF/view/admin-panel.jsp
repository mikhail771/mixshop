<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adminka</title>
</head>
<body>
<h1>Welcome, sir!</h1>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<a href="${pageContext.request.contextPath}/users" class="btn">Users</a>
<a href="${pageContext.request.contextPath}/orders" class="btn">Orders</a>
<a href="${pageContext.request.contextPath}/products/manage" class="btn">Products</a>
<a href="${pageContext.request.contextPath}/logout">LOGOUT</a>
</body>
</html>
