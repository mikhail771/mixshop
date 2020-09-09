<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h1>Registration</h1>
<h3 style="color: red">${message}</h3>
<form method="post" action="${pageContext.request.contextPath}/registration">
    Name: <input type="text" name="name">
    Login: <input type="text" name="login">
    Password: <input type="password" name="password">
    Confirm Password: <input type="password" name="psd-repeat">

    <button type="submit">Register</button>
</form>
</body>
</html>
