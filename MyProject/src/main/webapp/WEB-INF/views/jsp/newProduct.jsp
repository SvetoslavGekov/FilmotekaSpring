<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<base href="http://localhost:8080/FilmotekaSpring/">
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
<link rel="stylesheet" href="css/input_trailer_options.css">

<title>FilmotekaBG Administration</title>

</head>
<body bgcolor="#E9EBEE">

	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>
	
	<div class="w3-container" style="width:76%; margin-top:4%; margin-left:12%">
		<div class="w3-container w3-theme w3-border">
			<c:choose>
				<c:when test="${ mode == false}">
					<h1>Edit Existing Product</h1>
				</c:when>
				<c:otherwise>
					<h1>Create New Product</h1>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="w3-container w3-white w3-border">
			<div class = "w3-row-padding">
				<div class ="w3-container w3-margin w3-border">
					<form:form commandName="product" enctype="multipart/form-data">
						<div class="w3-row-padding w3-margin">
						
							<input type="hidden" name="category" value="${category}">
	      	  				<input type="hidden" name="productID" value="${product.id}">
	      	  				
							<!-- Left 50% of the form -->
							<div class="w3-col s6">
								<!-- Product name -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-commenting-o"></i><b> Product Name*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="name" type="text" required="required" maxlength="150"/>
							    </div>
							    
							    <!-- Release year -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i><b> Release Year*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="releaseDate" type="date" name="releaseDate" required="required" />
							    </div>
							    
							    <!-- PG Rating -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-info-circle"></i><b> PG Rating*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="pgRating" type="text" required="required" maxlength="10"/>
							    </div>
							    
							    <!-- Duration -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-clock-o"></i><b> Duration*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="duration" type="number" required="required" min="1" max="65535"/>
							    </div>
							    
							    <!-- Price -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-euro"></i><b> Price*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="originalBuyCost" type="number" required="required" min="1" max="99999" step="0.1"/>
							    </div>
							    
								<!-- Rent cost -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-euro"></i><b> Rent cost*: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="originalRentCost" type="number" required="required" min="1"  max="99999" step="0.1"/>
							    </div>
							    
							    <!-- Description -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-tv"></i><b> Description: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px; resize:none;" path="description" rows="5" cols="44" maxlength="1000"/>
							    </div>
							    
							    <!-- Writers -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-pencil-square-o"></i><b> Writers: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px; resize:none;" path="writers" rows="3" cols="44" maxlength="200"/>
							    </div>
							    
							    <!-- Actors -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-user"></i><b> Actors: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px; resize:none;" path="actors" rows="3" cols="44" maxlength="1000"/>
							    </div>							    
							</div>
							
							<!-- Right 50% of the form -->
							<div class="w3-col s6">
							
								<!-- Poster -->
								<img src="getPic?pic=${product.poster}" title="${product.name}" class = "w3-round w3-border"
									 style="width:50%; height:300px; margin-bottom:5%;"><br>
								<label class="w3-text-theme"><i class="w3-xlarge fa fa-file-image-o"></i><b> Poster Image: ${product.poster} </b></label>
								<form:input path="poster" type="hidden"/>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <input class="w3-input w3-border" type="file" name="posterFile" accept=".jpg" maxlength="200"/>
							    </div>

								<!-- Trailer -->
								<label class="w3-text-theme"><i class="w3-xlarge fa fa-file-movie-o"></i><b> Trailer: ${product.trailer} </b></label>
								
								<br><input type="radio" class="likeFile" name="bn"/> FILE
								&emsp;<input type="radio" class="likeUrl" name="bn" checked="checked" /> URL
								
							    <div id="trailerFILE" class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <input class="w3-input w3-border" type="file" name="trailerFile" accept=".mp4" maxlength="200"/>
							    </div>
							    <div id="trailerURL" class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <input class="w3-input w3-border" type="text" name="trailerURL" pattern="https?://www.youtube.com/embed/.+" title="Include https://www.youtube.com/embed/"
							      		placeholder="URL address of the trailer..." maxlength="55" />
							    </div>
	
								<!-- Sale percent -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-percent"></i><b> Sale Percent: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="salePercent" type="number" max="99"/>
							    </div>
							    
								<!-- Sale validity-->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i><b> Sale Validity: </b></label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="saleValidity" type="date"/>
							    </div>
							    
							    <!-- Category specific info -->
					          <c:set var = "categoryID" value = "${product.productCategory.id}"/>
					          <c:choose>
					          	<c:when test="${categoryID == 1}">
									<!-- Director-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-bullhorn"></i><b> Director: </b></label>
								    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
								      <form:input class="w3-input w3-border" path="director" type="text" maxlength="80"/>
								    </div>
					          	</c:when>
					          	
					           	<c:when test="${categoryID == 2}">
									<!-- Season-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-hourglass-o"></i><b> Season: </b></label>
								    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
								      <form:input class="w3-input w3-border" path="season" type="number" max="127"/>
								    </div>
								    
									<!-- Finished Airing Date-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i><b> Finished Airing Date: </b></label>
								    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
								      <form:input class="w3-input w3-border" path="finishedAiring" type="date"/>
								    </div>								    	
					          	</c:when>         	
					          </c:choose>
							    								    	
							</div>
						</div>
						<!-- Genres -->
						<div class="w3-container w3-theme w3-border">
							<h2>Genres</h2>
						</div>
						<div class="w3-container w3-border w3-white" >
							<form:checkboxes path="genres" items="${genres}" itemLabel="value" itemValue="id" delimiter="<br/>"/>
						</div>
						<div class="w3-panel">
						<c:choose>
							<c:when test="${ mode == false}">
								<input class="w3-button w3-small w3-theme w3-round-large" type="submit" value="Save changes"/>
							</c:when>
							<c:otherwise>
								<input class="w3-button w3-small w3-theme w3-round-large" type="submit" value="Create product"/>
							</c:otherwise>
						</c:choose>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>