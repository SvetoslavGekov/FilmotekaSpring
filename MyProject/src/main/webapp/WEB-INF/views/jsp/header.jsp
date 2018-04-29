<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
	  <div class="w3-bar w3-2017-lapis-blue  w3-card-4">
		<button class="w3-bar-item w3-button w3-padding-large" onclick="location.href='main'">FilmotekaBG</button>
		<button class="w3-bar-item w3-button w3-padding-large" onclick="location.href='browse'">Browse</button>
		<div class="w3-dropdown-hover">
		  <button class="w3-padding-large w3-button" title="More">My Account <i class="fa fa-caret-down"></i></button>     
		  <div class="w3-dropdown-content w3-bar-block w3-card-4">
			<a class="w3-bar-item w3-button" onclick="location.href='auth/account'">Profile</a>
			<a class="w3-bar-item w3-button">My Products</a>
			<a class="w3-bar-item w3-button">My Favorites</a>
			<a class="w3-bar-item w3-button">My WatchList</a>
			<a class="w3-bar-item w3-button" onclick="location.href='auth/cart'">My Cart</a>
			<a class="w3-bar-item w3-button" onclick="location.href='auth/orders'">My Order History</a>
			<a class="w3-bar-item w3-button" onclick="location.href='logout'">Logout</a>
		  </div>
		</div>
		<div class="w3-bar-item w3-padding-large" style="width:30%">
			<form method = "GET" action="search">
				<i class="fa fa-search"></i>
				<input type="search" placeholder="Search..." name="word" required></input>
			</form>
		</div>
		<button class="w3-bar-item w3-button w3-padding-large w3-right w3-hover-green">Sign In</button>
	  </div>
	</div>