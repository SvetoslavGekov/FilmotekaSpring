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
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-commenting-o"></i> Product Name*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="name" type="text" required="required"/>
							    </div>
							    
							    <!-- Release year -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i> Release Year*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="releaseDate" type="date" name="releaseDate" required="required" />
							    </div>
							    
							    <!-- PG Rating -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-info-circle"></i> PG Rating*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="pgRating" type="text" required="required"/>
							    </div>
							    
							    <!-- Duration -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-clock-o"></i> Duration*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="duration" type="number" required="required" min="1"/>
							    </div>
							    
							    <!-- Price -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-euro"></i> Price*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="originalBuyCost" type="number" required="required" min="1"/>
							    </div>
							    
								<!-- Rent cost -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-euro"></i> Rent cost*: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="originalRentCost" type="number" required="required" min="1"/>
							    </div>
							    
							    <!-- Description -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-tv"></i> Description: </label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px;" path="description" rows="5" cols="44"/>
							    </div>
							    
							    <!-- Writers -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-pencil-square-o"></i> Writers: </label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px;" path="writers" rows="3" cols="44"/>
							    </div>
							    
							    <!-- Actors -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-user"></i> Actors: </label>
							    <div class="w3-rest" style="margin-bottom:15px;">
							      <form:textarea class="w3-text" style="font-size:12px;" path="actors" rows="3" cols="44"/>
							    </div>							    
							</div>
							
							<!-- Right 50% of the form -->
							<div class="w3-col s6">
							
								<!-- Poster -->
								<img src="getPic?pic=${product.poster}" title="${product.name}" class = "w3-round w3-border"
									 style="width:50%; height:300px; margin-bottom:5%;"><br>
								<label class="w3-text-theme"><i class="w3-xlarge fa fa-file-image-o"></i> Poster Image: ${product.poster} </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <input class="w3-input w3-border" type="file" name="posterFile" accept=".jpg"/>
							    </div>

								<!-- Trailer -->
								<label class="w3-text-theme"><i class="w3-xlarge fa fa-file-movie-o"></i> Trailer: ${product.trailer} </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <input class="w3-input w3-border" type="file" name="trailerFile" accept=".avi"/>
							    </div>
	
								<!-- Sale percent -->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-percent"></i> Sale Percent: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="salePercent" type="number"/>
							    </div>
							    
								<!-- Sale validity-->
							  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i> Sale Validity: </label>
							    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
							      <form:input class="w3-input w3-border" path="saleValidity" type="date"/>
							    </div>
							    
							    <!-- Category specific info -->
					          <c:set var = "categoryID" value = "${product.productCategory.id}"/>
					          <c:choose>
					          	<c:when test="${categoryID == 1}">
									<!-- Director-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-bullhorn"></i> Director: </label>
								    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
								      <form:input class="w3-input w3-border" path="director" type="text"/>
								    </div>
					          	</c:when>
					          	
					           	<c:when test="${categoryID == 2}">
									<!-- Season-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-hourglass-o"></i> Season: </label>
								    <div class="w3-rest" style="margin-bottom:15px; width:75%;">
								      <form:input class="w3-input w3-border" path="season" type="number"/>
								    </div>
								    
									<!-- Finished Airing Date-->
								  	<label class="w3-text-theme"><i class="w3-xlarge fa fa-calendar"></i> Finished Airing Date: </label>
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
								<input class="w3-button w3-small w3-theme" type="submit" value="Save changes"/>
							</c:when>
							<c:otherwise>
								<input class="w3-button w3-small w3-theme" type="submit" value="Create product"/>
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