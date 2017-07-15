<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<td><b>Menu</b></td>
		</tr>
		<tr>
			<td><a href="CustomerDetails">Customer Details</a></td>
		</tr>
		<tr>
			<td><a href="AllCustomers">All Customers</a></td>
		</tr>
		<tr>
			<td><a href="Stocks.xhtml">Stocks</a></td>
		</tr>

		<c:choose>
			<c:when test="${requestScope.message == null }">
${requestScope.customer.name}'S stocks<br />

				<table border="1" cellpadding="4">
					<thead>
						<tr>
							<td>Stock Symbol</td>
							<td>Quantity</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="share" items="${requestScope.shares}">
							<tr>
								<td>${share.stockSymbol}</td>
								<td>${share.quantity}</td>
							</tr>
						</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p style="color: red;">${requestScope.message }</p>
</c:otherwise>
		</c:choose>
</html>