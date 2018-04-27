<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order History</title>
<style>

table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 50%;
    margin: auto;
    margin-left:5%;
    margin-top:2%; 
}

td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

tr:nth-child(even) {
    background-color: #dddddd;
}

a:hover {
    color: red;
}
</style>
</head>
<body>

	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

	<!-- Content -->
	<div>
		<h1 style="margin-top:6%; margin-left:5%">My Orders</h1>
		<table>
		  <tr>
		    <th>ORDER ID</th>
		    <th>PURCHASE DATE</th>
		    <th>ORDER PRICE</th>
		    <th>DETAILS</th>
		  </tr>
		  
		  <c:forEach var="order" items="${orders}">
			  <tr>
			    <td>${ order.id }</td>
			    <td>${ order.date }</td>
			    <td>${ order.totalCost }</td>
			    <td><a onclick="location.href='order/${order.id}'">VIEW DETAILS</a></td>
			  </tr>
		  </c:forEach>
		</table>
	</div>

</body>
</html>