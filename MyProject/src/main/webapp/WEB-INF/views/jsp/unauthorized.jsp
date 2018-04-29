<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/color-theme.css">
<link rel="stylesheet" href="css/html_slider.css">

<base href="http://localhost:8080/FilmotekaSpring/">
<title>Unauthorized access!</title>
</head>
<body>
  <!-- Unauthorized Access-->
  <div id="error" class="w3-container" style="max-width:100%">
    <div class="w3-panel w3-card w3-animate-zoom" style="max-width:100%">
      <div class="w3-center  w3-container "><br>
        <img src="img/unauthorized.jpg" alt="Unauthorized access image" style="max-width:100%" class="w3-image w3-circle w3-margin-top">
      </div>
	  <div class="w3-container w3-2017-lapis-blue">
      	<p class="w3-center w3-xlarge" ><b>You need to be logged in to access this content!</b></p>
      	<a href="main">Return to main page</a><br>
      	<a href="">Sign up now</a>
      </div>	
    </div>
  </div>
</body>
</html>