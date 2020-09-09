<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Add new product</h1>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Name: <input type="text" name="name" required>
    Price: <input type="number" name="price" required>
    <button type="submit">Add product</button>
</form>
</body>
</html>
