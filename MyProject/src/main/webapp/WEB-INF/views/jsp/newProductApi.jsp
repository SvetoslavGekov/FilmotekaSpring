<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<!-- Style rating stars and review table -->
<link href="../css/product.css" rel="stylesheet" type="text/css">
<title>FilmotekaBG Administration</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
<body bgcolor="#E9EBEE">
	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

	<!-- Visualize the product information -->
	<div class="w3-container" style="width:70%; margin-top:4%; margin-left:15%">
		<div class="w3-container w3-theme w3-border">
			<h1>Search For A Movie With OMDb API</h1>
			<div class="w3-container" style="margin-top:10px; margin-bottom:10px">
				<form>
					<input type="text" name="query" placeholder="Enter movie title here" minlength="3" maxlength="100" required>
					<button type = "submit" class="w3-button" formmethod="post" title="Search"><i class="fa fa-search"></i></button>
				</form>
			</div>
		</div>
		<div class="w3-container w3-white w3-border">
			<div class="w3-row-padding">
				<div class ="w3-container w3-margin w3-border">
					<c:choose>
						<c:when test="${not empty apiMovies }">
							<c:forEach var="movie" items="${apiMovies}">
								<div class="w3-container w3-border w3-round-xxlarge w3-white" style="margin-top:2%">
								<div class="w3-row-padding">
									<div class="w3-col w3-container w3-margin" style="width:25%;">
										<div class="w3-container w3-padding">
							        		<img src="${movie.poster}" title="${movie.title}" class = "w3-round" style="width:100%; height:225px;">
							        	</div>
							        </div>
							        	<div class="w3-col w3-container w3-margin" style="width:45%;">
							            	<div class="w3-container">
							                  <span class="w3-large w3-text-highway-blue"><b>${movie.title}</b></span><br><br>
											  <span class="w3-medium w3-text-theme "><b>Released:</b>
											  	<span class="w3-small w3-text-black ">${movie.year}</span>
											  </span><br>
											  <span class="w3-medium w3-text-theme "><b>IMDB identifier:</b>
											  	<span class="w3-small w3-text-black ">${movie.imdbID}</span>
											  </span><br>
											   <button type="submit" class=" w3-button w3-medium w3-text-theme w3-round-large"
											   	onclick="location.href='adm/newMovie/${movie.imdbID}'"><b>Create movie from API</b>
											  </button><br>
							              </div>
							          </div>
							      </div>
							  </div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<h2>No products match your search criteria.</h2>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>