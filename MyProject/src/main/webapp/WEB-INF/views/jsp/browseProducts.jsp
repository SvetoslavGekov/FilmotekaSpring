<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<body>
<!-- Include the header file via JSTL -->
<c:import url="header.jsp"></c:import>
	
<!-- Filters Section -->
<form:form commandName="newFilter">
<div id="filters" class="w3-container w3-light-gray w3-round-xxlarge menus w3-left">
	<input type="submit" value="Apply filter" />
	<!--Ordering-->
	<div class="w3-panel">
        <button type = "button" onclick="openOrCloseSection('orderingSection')" class="w3-small w3-btn w3-block w3-theme-d2  w3-left-align">Ordering</button>
    	<div id="orderingSection" class="w3-container w3-show w3-padding ">
			<form:select path="orderedBy" id="orderedBySelector" class="w3-select w3-border" name="option">
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
</div>
</form:form>

<div id ="contents" class="w3-container w3-border w3-left" style="margin-left:24%; margin-top:10%">
		<table cellspacing="0" cellpading="0" width="75%" border="1">
			<tbody>
				<tr>
					<th colspan = "2">Product</th>
				</tr>
				<c:forEach var="product" items="${products}">
				<tr>
				<td>
					<a href="product/${product.id}">
						<img height="150" width="150" alt="${product.name}" title = "${product.name }" src = "getPic?pic=${product.poster}">
					</a>
				</td>
				<td valign="middle"><span class="cartItem">${product.name} ${product.releaseDate.year}</span></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>	
</div>
</body>
</html>