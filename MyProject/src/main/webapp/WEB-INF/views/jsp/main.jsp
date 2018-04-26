<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<title>Welcome to FilmotekaBG</title>
</head>
<body>
<!-- Navbar -->
<div id="navigation" class="w3-top">
  <div class="w3-bar w3-2017-lapis-blue  w3-card-4">
	<button class="w3-bar-item w3-button w3-padding-large" onclick ="viewAllProducts()">FilmotekaBG</button>
	<button class="w3-bar-item w3-button w3-padding-large" onclick ="viewAllProducts()">Browse</button>
	<div class="w3-dropdown-hover">
	  <button class="w3-padding-large w3-button" title="More">My Account <i class="fa fa-caret-down"></i></button>     
	  <div class="w3-dropdown-content w3-bar-block w3-card-4">
		<a class="w3-bar-item w3-button">Profile</a>
		<a class="w3-bar-item w3-button">My Products</a>
		<a class="w3-bar-item w3-button">My Favorites</a>
		<a class="w3-bar-item w3-button">My Watchlist</a>
		<a class="w3-bar-item w3-button">My Cart</a>
		<a class="w3-bar-item w3-button">Logout</a>
	  </div>
	</div>
	<div class="w3-bar-item w3-padding-large" style="width:30%">
		<i class="fa fa-search"></i>
		<input type="text" placeholder="Search..."></input>
	</div>
	<button class="w3-bar-item w3-button w3-padding-large w3-right w3-hover-green">Sign In</button>
  </div>
</div>

	<!-- Contents -->
	<div id = "content" class="w3-container w3-right" style="margin-top:7%">
			<c:forEach var="entry" items="${mainPageProducts}">
	  			<p><c:out value="${entry.key}"/></p>
	  			<div class="w3-panel w3-padding w3-border w3-round-xxlarge">
	  				<div class="w3-row-padding">
	  					<p class="w3-panel w3-text-blue w3-wide"><b>${entry.key}</b></p>
			  			<c:forEach var="product" items="${entry.value}">
			  			<div class="w3-col" style="width:15%">
			  				<div class = "w3-border-5 w3-border-black w3-metro-light-green w3-round-large" style="border:16px">
			  					<p class = "w3-center w3-small w3-wide"><b>${product.name}</b></p>
			  					<div class="w3-display-container w3-white">
			  					<img src="getPic?pic=${product.poster}" class="w3-hover-opacity" style="width:100%; height:250px;">
			  						<div class="w3-display-middle w3-display-hover">
			  							<button class="w3-button w3-indigo w3-tiny">More Info</button>
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
</html>