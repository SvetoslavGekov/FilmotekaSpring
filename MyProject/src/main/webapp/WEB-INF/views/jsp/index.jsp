<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<head>
<style>
span.signup,
.close:hover,
.close:focus {
    color: blue;
    cursor: pointer;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Icon Imports -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- Color Libraries -->
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-highway.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2017.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-food.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-vivid.css">
<link rel="stylesheet" href="css/color-theme.css">
<!--  <link rel="stylesheet" href="css/user_form.css">  -->

<base href="http://localhost:8080/FilmotekaSpring/">

<title>FilmotekaBG Login</title> 
</head>
<body bgcolor="#E9EBEE">

<div class="w3-display-container">
	<div class="w3-border w3-round-xxlarge w3-card-4 w3-animate-zoom w3-white" style="width:50%; margin-top:5%; margin-left:25%" >
      <div class="w3-center"><br>
      	<p class="w3-wide w3-xxlarge w3-text-theme"><b>FILMOTEKA.BG LOGIN</b></p>
        <img src="img/avatar.jpg" alt="User Avatar" style="width:30%" class="w3-circle w3-margin-bottom">
      </div>
      
      <!-- In case of invalid credentials -->
      <c:if test="${not empty loginError}">
      	<p class="w3-center w3-large w3-text-red"><b>${loginError}</b></p>
      </c:if>
      
      <form class="w3-container" method="post" action="login">
        <div class="w3-section">
          <label><b>Username</b></label>
          <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="username" value = "admin" required>
          <label><b>Password</b></label>
          <input class="w3-input w3-border" type="password" placeholder="Enter Password" name="password" required value = "Admin123">
          <button class="w3-button w3-block w3-2017-lapis-blue w3-section w3-padding w3-hover-teal" type="submit"><b>Login</b></button>
        </div>
      </form>
      <div class="w3-container w3-border-top w3-grey w3-padding-large">
		<span class="">Don't have an account?  
	    	<span class="signup" onclick="document.getElementById('id01').style.display='block'">Sign up now</span>
	    </span>
      </div>
    </div>
</div>

<div id="id01" class="w3-modal">
	<div class="w3-modal-content w3-card-4 w3-light-grey w3-animate-zoom " style="max-width:60%">
		<span onclick="document.getElementById('id01').style.display='none'" 
			class="w3-button w3-xlarge w3-hover-red w3-display-topright" title="Close Modal">&times;</span>
		

		<form class="w3-container w3-padding-large" method="post" action="register" >
			<h2 class="w3-center w3-text-theme-d5 "><b>Sign Up</b></h2>
		    <p class="w3-center">Please fill in this form to create an account.</p>
		    <!-- Username -->
			<div class="w3-row w3-section">
			  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-user w3-text-theme"></i></div>
			    <div class="w3-rest">
			      <input class="w3-input w3-border" name="username" type="text" placeholder="Enter Username"
			      	required pattern="^(?=.*[a-z]).{4,}" title="At least 4 characters long">
			    </div>
			</div>
			
			<!-- Email address -->
			<div class="w3-row w3-section">
				<div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-envelope-o w3-text-theme"></i></div>
			    <div class="w3-rest">
			      <input class="w3-input w3-border" name="email" type="email" placeholder="Enter Email"
			      	title="Your email should look something like myemail@myprovider.com">
			    </div>
			</div>
			
			<!-- Password -->
			<div class="w3-row w3-section">
			  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-key w3-text-theme"></i></div>
			    <div class="w3-rest">
			      <input class="w3-input w3-border" name="password" type="password" placeholder="Enter Password"
			      	required pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$" 
			      	title="Your password should be atleast 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number">
			    </div>
			</div>
			
			<!-- First and Last names-->
			<div class="w3-row w3-section">
			  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-address-card-o w3-text-theme"></i></div>
			    <div class="w3-rest">
			      <input class="w3-input w3-border" name="firstName" type="text" placeholder="Enter First Name" required >
			    </div>
			</div>
			
			<div class="w3-row w3-section">
			  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-address-card-o w3-text-theme"></i></div>
			    <div class="w3-rest">
			      <input class="w3-input w3-border" name="lastName" type="text" placeholder="Enter Last Name" required >
			    </div>
			</div>
			
			<button type="submit" class="w3-button w3-block w3-section w3-theme w3-ripple w3-padding w3-hover-teal">Create account</button>
	    </form>
	    
	</div>
</div>

</html>