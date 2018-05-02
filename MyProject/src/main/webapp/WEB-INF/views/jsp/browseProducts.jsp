<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet" href="css/html_slider.css">

<!-- JavaScript -->
<script src="js/utility.js"></script>

<title>Browse Products</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body bgcolor="#E9EBEE">
<!-- Include the header file via JSTL -->
<c:import url="header.jsp"></c:import>
	
<div class="w3-display-container" style="margin-top:5%">
<!-- Filters Section -->

<div id="filters" class="w3-container w3-white w3-round-xxlarge menus w3-left" style="margin-left:2%; width:20%">
	<form:form commandName="newFilter">
	
	<!--Ordering-->
	<div class="w3-panel">
		<input class ="w3-margin"  type="submit" value="Apply filter" />
        <button type = "button" onclick="openOrCloseSection('orderingSection')" class="w3-small w3-btn w3-block w3-theme-d2  w3-left-align">Ordering</button>
    	<div id="orderingSection" class="w3-container w3-show w3-padding ">
			<form:select path="orderedBy" id="orderedBySelector" class="w3-select w3-border w3-round-xxlarge" name="option">
			  <form:option value="name">Alphabetically</form:option>
			  <form:option value="duration">By Duration</form:option>
			  <form:option value="buy_cost">By Price</form:option>
			  <form:option value="rent_cost">By Renting Price</form:option>
			</form:select>
			<form:radiobutton path="isAscending" value="true"/> Ascending<br>
  			<form:radiobutton path="isAscending" value="false"/> Descending<br>
    	</div>
    </div>
	
	<!--Product name -->
	<div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('productNameSection')" class="w3-small w3-btn w3-block w3-theme-d2  w3-left-align">Product Name</button>
    	<div id="productNameSection" class="w3-container w3-show w3-padding ">
			<form:input id="filterName" path = "name" name="filterName" class="w3-container w3-input" type="text" placeholder="Enter product name"/>
    	</div>
    </div>
    
    <!--Release year -->
    <div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('releaseYearSection')" class="w3-small w3-btn w3-block w3-theme-d2 w3-left-align">Release Year</button>
    	<div id="releaseYearSection" class="w3-container w3-show  w3-padding ">
				<div class="slidecontainer">
                <span>Minimum: <span id="minReleaseYear">${newFilter.minReleaseYear}</span></span>
  				<form:input type="range" path="minReleaseYear" min="${filter.minReleaseYear}" max="${filter.maxReleaseYear}" value="${newFilter.minReleaseYear}" class="slider" id="minReleaseYearSlider" 
  					oninput="getSliderValue('minReleaseYearSlider','minReleaseYear')"/>
				</div>
                <div class="slidecontainer">
                <span>Maximum: <span id="maxReleaseYear">${newFilter.maxReleaseYear}</span></span>
  				<form:input path="maxReleaseYear" type="range" min="${filter.minReleaseYear}" max="${filter.maxReleaseYear}" value="${newFilter.maxReleaseYear}" class="slider" id="maxReleaseYearSlider" 
  					oninput="getSliderValue('maxReleaseYearSlider','maxReleaseYear')"/>
				</div>	
    	</div>
    </div>
    
    <!--Duration -->
    <div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('durationSection')" class="w3-small w3-container w3-btn w3-block w3-theme-d2 w3-left-align">Duration</button>
    	<div id="durationSection" class="w3-container w3-show  w3-padding ">
				<div class="slidecontainer">
                <span>Minimum duration: <span id="minDuration">${newFilter.minDuration}</span></span>
  				<form:input path = "minDuration" type="range" min="${filter.minDuration}" max="${filter.maxDuration}" value="${newFilter.minDuration}" class="slider" id="minDurationSlider" 
  					oninput="getSliderValue('minDurationSlider','minDuration')"/>
				</div>
                <div class="slidecontainer">
                <span>Maximum duration: <span id="maxDuration">${newFilter.maxDuration}</span></span>
  				<form:input path="maxDuration" type="range" min="${filter.minDuration}" max="${filter.maxDuration}" value="${newFilter.maxDuration}" class="slider" id="maxDurationSlider" 
  					oninput="getSliderValue('maxDurationSlider','maxDuration')"/>
				</div>	
    	</div>
    </div> 
    <!--Buy cost -->
    <div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('buyCostSection')" class="w3-small w3-container w3-btn w3-block w3-theme-d2 w3-left-align">Price</button>
    	<div id="buyCostSection" class="w3-container w3-show  w3-padding ">
				<div class="slidecontainer">
                <span>Minimum: <span id="minBuyCost">${newFilter.minBuyCost}</span></span>
  				<form:input path="minBuyCost" type="range" min="${filter.minBuyCost}" max="${filter.maxBuyCost}" value="${newFilter.minBuyCost}" class="slider" id="minBuyCostSlider" 
  					oninput="getSliderValue('minBuyCostSlider','minBuyCost')"/>
				</div>
                <div class="slidecontainer">
                <span>Maximum: <span id="maxBuyCost">${newFilter.maxBuyCost}</span></span>
  				<form:input path="maxBuyCost" type="range" min="${filter.minBuyCost}" max="${filter.maxBuyCost}" value="${newFilter.maxBuyCost}" class="slider" id="maxBuyCostSlider" 
  					oninput="getSliderValue('maxBuyCostSlider','maxBuyCost')"/>
				</div>	
    	</div>
    </div>
    <!--Rent cost -->
    <div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('rentCostSection')" class="w3-small w3-container w3-btn w3-block w3-theme-d2 w3-left-align">Rent cost</button>
    	<div id="rentCostSection" class="w3-container w3-show  w3-padding ">
				<div class="slidecontainer">
                <span>Minimum: <span id="minRentCost">${newFilter.minRentCost}</span></span>
  				<form:input path="minRentCost" type="range" min="${filter.minRentCost}" max="${filter.maxRentCost}" value="${newFilter.minRentCost}" class="slider" id="minRentCostSlider" 
  					oninput="getSliderValue('minRentCostSlider','minRentCost')"/>
				</div>
                <div class="slidecontainer">
                <span>Maximum: <span id="maxRentCost">${newFilter.maxRentCost}</span></span>
  				<form:input path="maxRentCost" type="range" min="${filter.minRentCost}" max="${filter.maxRentCost}" value="${newFilter.maxRentCost}" class="slider" id="maxRentCostSlider" 
  					oninput="getSliderValue('maxRentCostSlider','maxRentCost')"/>
				</div>	
    	</div>
    </div>   
    
	<div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('genresSection')" class="w3-small w3-container w3-btn w3-block w3-theme-d2 w3-left-align">Genres</button>
    	<div id="genresSection" class="w3-container w3-show  w3-padding ">
    		<form:checkboxes path="genres" items="${genres}" itemLabel="value" itemValue="id" delimiter="<br/>"/>
    	</div>
    </div>
	</form:form>
</div>

<!-- Products section -->
<div id ="contents" class="w3-container menus w3-right" style="width:70%; margin-right:4%">
	<h1>Filtered products</h1>
	<c:forEach var="product" items="${products}">
	<div class="w3-container w3-border w3-round-xxlarge w3-white" style="margin-top:2%">
		<div class="w3-row-padding">
			<div class="w3-col w3-container w3-margin" style="width:25%;">
				<div class="w3-container w3-padding">
	        		<a href="product/${product.id}">
	        			<img src="getPic?pic=${product.poster}" title="${product.name}" class = "w3-round" style="width:100%; height:225px;">
	        		</a>
	        	</div>
	        </div>
	        	<div class="w3-col w3-container w3-margin" style="width:45%;">
	            	<div class="w3-container">
	                  <a href="product/${product.id}">
	                  	<span class="w3-large w3-text-highway-blue"><b>${product.name}</b></span><br><br>
	                  </a>
	                  
	                  <span class="w3-medium w3-text-theme " ><b>Product type:</b>
	                  	<span class="w3-small w3-text-black ">${product.productCategory.value}</span>
	                  </span><br>
					  <span class="w3-medium w3-text-theme "><b>Released:</b>
					  	<span class="w3-small w3-text-black ">${product.releaseDate.year}</span>
					  </span><br>
					  <span class="w3-medium w3-text-theme "><b>Pg Rating:</b>
					  	<div class="w3-tag w3-round w3-green w3-tiny" style="padding:3px">
							<div class="w3-tag w3-round w3-green w3-border w3-border-white">
								${product.pgRating}
							</div>
						</div>
					  </span><br>
					  <span class="w3-medium w3-text-theme "><b>Viewer Rating:</b>
						<div class="w3-tag w3-round w3-vivid-reddish-purple w3-tiny" style="padding:3px">
						  <i class="fa fa-star-o"></i>
						  <div class="w3-tag w3-round w3-vivid-reddish-purple w3-border w3-border-white">
								<fmt:formatNumber value="${product.viewerRating}" maxFractionDigits="2"/>
						  </div>
						</div>
					  </span><br>
					  <span class="w3-medium w3-text-theme "><b>Duration:</b>
					  	<span class="w3-small w3-text-black ">${product.duration} minutes</span>
					  </span><br>
					  <span class="w3-medium w3-text-theme "><b>Genres:</b><br>
					  	<c:forEach var="genre" items="${product.genres}">
				  			<div class="w3-tag w3-round w3-food-blueberry w3-tiny" style="padding:3px">
								<div class="w3-tag w3-round w3-food-blueberry w3-border w3-border-white">
									${genre.value}
								</div>
							</div>
					  	</c:forEach>
					  	
					  </span><br>
					  
	              </div>
	          </div>
	          <div class="w3-rest w3-container w3-margin">
	          	<span class="w3-medium w3-text-theme w3-text-black" > Pricing
	          	<br><br>
	          	<!-- Buying price -->
	          	<span class="w3-medium w3-text-black" >Buying</span>
				<c:choose>
				<c:when test="${product.originalBuyCost != product.buyCost }">
	          		<br><span class="w3-small w3-text-red">Old price: <del><fmt:formatNumber value="${product.originalBuyCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></del></span><br>
	          		<span class="w3-medium">New price: <fmt:formatNumber value="${product.buyCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
	          	</c:when>
	          	<c:otherwise>
	          		<br><span class="w3-medium">Price: <fmt:formatNumber value="${product.buyCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
	          	</c:otherwise>
				</c:choose>
				
				<!-- Buying price -->
				<br><br>
				<span class="w3-medium w3-text-black">Renting</span>
				<c:choose>
				<c:when test="${product.originalBuyCost != product.buyCost }">
	          		<br><span class="w3-small w3-text-red">Old price: <del><fmt:formatNumber value="${product.originalRentCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></del></span><br>
	          		<span class="w3-medium">New price: <fmt:formatNumber value="${product.rentCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
	          		<br><br>
	          		<span class="w3-medium">Save ${product.salePercent}% now!</span>
	          	</c:when>
	          	<c:otherwise>
	          		<br><span class="w3-medium">Price: <fmt:formatNumber value="${product.rentCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
	          	</c:otherwise>
				</c:choose>
	          	</span>
	          	
	          </div>
	      </div>
	  </div>
		</c:forEach>
	</div>
</div>
</body>
</html>