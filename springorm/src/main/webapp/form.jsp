<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Form</title>
</head>
<body>
	<h1>Product Form</h1>
	<form:form action="addProduct.do" modelAttribute="product">
		Name <form:input path="name"/> <br />
		Price <form:input path="price"/> <br />
		Quantity <form:input path="quantity"/> <br />
		<button type="submit">Add Product</button>
	</form:form>
</body>
</html>