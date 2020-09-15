<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h4 style="color: red">${errorMessage}</h4>
<form action="${pageContext.request.contextPath}/login" method="post">
    Login: <input type="text" name="login" required>
    Password: <input type="password" name="password" required>
    <button type="submit">Login</button>
</form>
<a href="${pageContext.request.contextPath}/registration">Registration</a>
</body>
</html>
