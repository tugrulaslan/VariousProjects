<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="trader.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Customers</title>
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
</table>
<table border="1">
<tr>
<td>Customer ID</td>
<td>Name</td>
<td>Address</td>
<td></td>
</tr>

<%
Customer[] customer = (Customer[] )request.getAttribute("customer");
for(int i = 0; i < customer.length; i++){
	%>
	<tr>
	<td><%= customer[i].getId()%></td>
	<td><%= customer[i].getName()%></td>
	<td><%= customer[i].getAddr()%></td>
	<td><a href="PortfolioController?customerIdentity=<%= customer[i].getId()%>">View</td>
	</tr>
	<%
}

%>
</table>

</body>
</html>