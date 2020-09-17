<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inject data</title>
</head>
<body>
<h2>Your data was injected to DB!</h2>
<a href="${pageContext.request.contextPath}/products" class="btn">All products</a>
<a href="${pageContext.request.contextPath}/logout">LOGOUT</a>
</body>
</html>