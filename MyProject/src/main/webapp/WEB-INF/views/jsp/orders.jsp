<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- HTML Imports -->
<!--<link rel="import" href="../menus/menus.html">

<!-- Color Libraries -->
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-highway.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2017.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-food.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-vivid.css">
<link rel="stylesheet" href="css/color-theme.css">
<link rel="stylesheet" href="css/html_slider.css">
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

td{
    border: 2px solid darkblue;
    text-align: left;
    padding: 8px;
}

th {
    border: 3px solid black;
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

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body bgcolor="#E9EBEE">

	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

	<!-- Content -->
	<div id = "content" class="w3-container" style="margin-top:7%;">
	
	<div class="w3-panel w3-padding-large w3-border-theme-theme w3-topbar w3-bottombar w3-leftbar w3-rightbar w3-round-xxlarge w3-light-grey">
	  	<div class="w3-row-padding">	
		<p class="w3-panel w3-center w3-text-theme w3-xxlarge"><b>My Orders</b></p>
		<div id ="contents" class="w3-container menus w3-animate-zoom" style="width:100%; margin-right:15% ; margin-left:20%">
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
			    <td><fmt:formatNumber value="${ order.totalCost }" maxFractionDigits="2"/> <i class="fa fa-euro"></i></td>
			    <td><a onclick="location.href='auth/order/${order.id}'">VIEW DETAILS</a></td>
			  </tr>
		  </c:forEach>
		</table>
		</div>
		</div>
		</div>
		</div>
</body>
</html>