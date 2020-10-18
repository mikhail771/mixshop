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
<a href="${pageContext.request.contextPath}/user/orders">My orders</a>
<h3 style="text-align: right">Your <a href="${pageContext.request.contextPath}cart/products">cart</a></h3>
<a href="${pageContext.request.contextPath}/logout">LOGOUT</a>
<a href="${pageContext.request.contextPath}/inject-data" class="btn">Inject admin</a>
</body>
</html>
