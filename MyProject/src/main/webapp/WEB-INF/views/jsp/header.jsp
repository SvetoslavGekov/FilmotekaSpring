<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Color Libraries -->
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-highway.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2017.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-food.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-vivid.css">
<link rel="stylesheet" href="css/color-theme.css">
<link rel="stylesheet" href="css/html_slider.css">

	<!-- Navbar -->
	<div id="navigation" class="w3-top">
	  <div id="barItems" class="w3-bar w3-2017-lapis-blue  w3-card-4">
		<button class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='main'"><b>Home</b></button>
		<button class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='browse'"><b>Browse products</b></button>
		
		<!-- If user is admin (add additional menus) -->
		<c:if test="${sessionScope.USER.isAdmin }">
			<div class="w3-dropdown-hover">
				<button class="w3-button w3-hover-teal"><b>Administrative</b></button>
				<div class="w3-dropdown-content w3-bar-block w3-card-4 w3-vivid-white">
					<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='adm/newProduct/1'"><b>New Movie</b></a>
					<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='adm/newProduct/2'"><b>New TV Series</b></a>
				</div>
			</div>
		</c:if>
		
		<div class="w3-bar-item " style="width:30%">
			<form method = "GET" action="search">
				<i class="fa fa-search"></i>
				<input type="search" placeholder="Search..." name="word" required></input>
			</form>
		</div>
		
		<c:choose>
    		<c:when test="${ sessionScope.USER != null }">
       		 	<div class="w3-dropdown-hover w3-right" style="margin-right:1%">
				  	<button class=" w3-button w3-hover-teal" onclick="location.href='auth/account'" title="More"><b>${ sessionScope.USER.firstName} ${ sessionScope.USER.lastName}<b></button>     
				 	 <div class="w3-dropdown-content w3-bar-block w3-card-4 w3-vivid-white">
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='auth/myproducts'"><b>My Products</b></a>
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='auth/favourites'"><b>My Favorites</b></a>
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='auth/watchlist'"><b>My WatchList</b></a>
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='auth/cart'"><b>My Cart</b></a>
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='auth/orders'"><b>My Order History</b></a>
						<a class="w3-bar-item w3-button w3-hover-teal" onclick="location.href='logout'"><b>Logout</b></a>
				 	</div>
			    </div>
   			</c:when>    
	    	<c:otherwise>
	       		 <button class="w3-bar-item w3-button w3-right w3-hover-teal" onclick="location.href=''"><b>Sign In</b></button>
	    	</c:otherwise>
		</c:choose>
	  </div>
	</div>