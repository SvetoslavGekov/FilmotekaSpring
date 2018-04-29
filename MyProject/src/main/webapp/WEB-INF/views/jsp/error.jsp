<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="/FilmotekaSpring/css/color-theme.css">
<link rel="stylesheet" href="/FilmotekaSpring/css/html_slider.css">
<title>An error has occured!</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
  <!-- Error Modal -->
  <div id="error" class="w3-container" style="max-width:100%">
    <div class="w3-panel w3-card w3-animate-zoom" style="max-width:100%">
      <div class="w3-center  w3-container "><br>
        <img src="img/error.jpg" alt="Error image" style="max-width:70%" class="w3-image w3-circle w3-margin-top">
      </div>
	  <div class="w3-container w3-2017-lapis-blue">
      	<p class="w3-center w3-xlarge" ><b>Ooops, something went wrong!</b></p>
      	<p id="errorMessage" class="w3-center w3-medium">${ exception.message }</p>
      </div>	
    </div>
  </div>
</body>
</html>