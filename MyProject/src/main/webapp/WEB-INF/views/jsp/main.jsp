<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
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
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-safety.css">
<link rel="stylesheet" href="css/color-theme.css">
<link rel="stylesheet" href="css/html_slider.css">

<title>FilmotekaBG Main</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body bgcolor="#E9EBEE">

	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

	<!-- Contents -->
	<div id = "content" class="w3-container" style="margin-top:7%; margin-left:6%; margin-right:6%;">
			<c:forEach var="entry" items="${products}">
	  			<div class="w3-panel w3-padding-large w3-border-theme-theme w3-topbar w3-bottombar w3-leftbar w3-rightbar w3-round-xxlarge w3-light-grey">
	  				<div class="w3-row-padding">
	  					<p class="w3-panel w3-text-theme w3-center w3-xlarge"><b><c:out value=" ${entry.key}"></c:out></b></p>
			  			<c:forEach var="product" items="${entry.value}">
			  			<div class="w3-col w3-animate-zoom" style="width:18%">
			  				<div class = "w3-vivid-bluish-green w3-round-large" style="border:25px">
			  					<p class = "w3-center w3-small w3-wide"><b><c:out value="${product.name}"></c:out></b></p>
			  					<div class="w3-display-container w3-white">
			  					<img src="getPic?pic=${product.poster}" class="w3-hover-opacity" style="width:100%; height:250px;">
			  						<div class="w3-display-middle w3-display-hover">
			  							<button class="w3-button w3-indigo w3-tiny w3-round-xxlarge w3-hover-vivid-greenish-blue" 
			  								onclick="location.href='product/${product.id}'"><b>More Info</b></button>
			  						</div>
			  						<div class="w3-display-bottomleft w3-display-hover " style="margin-bottom:1%; margin-left:2%;">
			  							<button class="w3-button w3-indigo w3-tiny w3-round-xxlarge"
			  								 onClick="addProductToCart(${product.id},true)"><b>Buy</b></button>
										<p class="w3-tiny w3-text-white w3-highway-green w3-round-xxlarge w3-center w3-align-right" title="Price">
											<b>
											<fmt:formatNumber value="${product.buyCost}" maxFractionDigits="2"/>
											<i class="fa fa-euro"></i>
											</b>
										</p>
			  						</div>
			  						<div class="w3-display-bottomright w3-display-hover " style="margin-bottom:1%; margin-right:2%;" title="Rent cost">
			  							<button class="w3-button w3-indigo w3-tiny w3-round-xxlarge" 
			  								onClick="addProductToCart(${product.id},false)"><b>Rent</b></button>
			  							<p class=" w3-tiny w3-text-white w3-highway-green w3-round-xxlarge w3-center w3-align-right">
											<b>
											<fmt:formatNumber value="${product.rentCost}" maxFractionDigits="2"/>
											<i class="fa fa-euro"></i>
											</b>
										</p>
			  						</div>
			  						<div class="w3-display-topright w3-display-hover w3-small" style="margin-top:2%; margin-right:2%;" title="PG Rating">
			  							<div class="w3-tag w3-round w3-green" style="padding:3px">
										  <div class="w3-tag w3-round w3-green w3-border w3-border-white">
												<b><c:out value="${product.pgRating}"></c:out></b>
										  </div>
										</div>
			  						</div>
			  						<div class="w3-display-topleft w3-display-hover w3-small" style="margin-top:2%; margin-left:2%;" title="Viewer Rating">
			  							<div class="w3-tag w3-round w3-vivid-reddish-purple" style="padding:3px">
										  <i class="fa fa-star-o"></i>
										  <div class="w3-tag w3-round w3-vivid-reddish-purple w3-border w3-border-white">
												<b><fmt:formatNumber value="${product.viewerRating}" maxFractionDigits="2"/></b>
										  </div>
										</div>
			  						</div>
			  					</div>
			  				</div>
			  			</div>
			  			</c:forEach>
			  		</div>
			  	</div>
			</c:forEach>
	</div>
</body>
<script src="js/userInteractions.js">

</script>
</html>