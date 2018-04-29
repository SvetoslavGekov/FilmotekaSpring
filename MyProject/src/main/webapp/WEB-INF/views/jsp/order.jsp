<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
<title>My Orders</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>
	
	<div id = "content" class="w3-container" style="margin-top:7%; margin-left:1%">
	<h3>Order &numero;${ order.id }</h3>
	<c:choose>
		<c:when test="${not empty order.shoppingCart }">
		
			<c:forEach var="entry" items="${order.shoppingCart }">
				<c:set var = "product" value = "${entry.key }"></c:set>
				<c:set var = "validity" value = "${entry.value }"></c:set>
				<c:set var = "price" value = "${product.buyCost }"></c:set>
				<c:if test="${not empty validity }">
					<c:set var = "price" value = "${ product.rentCost }"></c:set>
				</c:if>
						<table cellspacing="0" cellpading="0" width="75%" border="1">
							<tbody>
								<tr>
									<th colspan = "2">Item</th>
									<th>Validity</th>
									<th>Price</th>
								</tr>
								<tr>
								<td >
									<img height="150" width="150" alt="${product.name}" title = "${product.name }" src = "getPic?pic=${product.poster}">
								</td>
								<td valign="middle"><span class="cartItem">${product.name} ${product.releaseDate.year}</span></td>
								<td valign="middle" halign="center" >${validity }</td>
								<td valign="middle" halign="center" >${price } &euro;</td>
								</tr>
							</tbody>
						</table>
			</c:forEach>	
		</c:when>
		<c:otherwise>
			<h3>Empty order!</h3>
		</c:otherwise>
	</c:choose>
	</div>
</body>
</html>