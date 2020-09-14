<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products list</title>
</head>
<body>
<h1>Products:</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/cart/products/add?id=${product.id}">To cart</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/products/add">Add product</a>
<a href="${pageContext.request.contextPath}/orders">My orders</a>
<h3 style="text-align: right">In your <a href="${pageContext.request.contextPath}cart/products">cart</a> ${countInCart} products</h3>
</body>
</html>
