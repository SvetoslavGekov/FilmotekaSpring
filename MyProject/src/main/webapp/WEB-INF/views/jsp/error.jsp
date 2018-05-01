<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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


<link rel="stylesheet" href="/FilmotekaSpring/css/color-theme.css">
<title>An error has occured!</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
  <!-- Error Modal -->
    <div class="w3-center w3-animate-zoom w3-light-grey" >
        	<img src="img/error.png" alt="Error image" style="max-width:45%" class="w3-image w3-margin-top">
	      	<div class="w3-container w3-2017-lapis-blue">
		      	<p class="w3-xxlarge " ><b>Ooops, something went wrong!</b></p>
		      	<p id="errorMessage" class="w3-large"><b>${ exception.message }</b></p>
		      	<a href="main" class="w3-medium">Back to the main page</a><br>
	      </div>	
    </div>
</body>
</html>