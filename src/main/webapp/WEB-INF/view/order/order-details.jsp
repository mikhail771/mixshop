<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order details</title>
</head>
<body>
<h1>Order details:</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Product</th>
    </tr>
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
        <c:forEach var="product" items="${order.products}">
            <h4>${product.name}</h4>
            <h4>${product.price}</h4>
        </c:forEach>
            </td>
        </tr>
</table>
</body>
</html>
