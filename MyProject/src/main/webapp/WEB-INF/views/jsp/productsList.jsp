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
<link rel="stylesheet" href="../css/color-theme.css">
<link rel="stylesheet" href="../css/html_slider.css">

<!-- JavaScript -->
<script src="../js/utility.js"></script>

<title>${ collection }</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body bgcolor="#E9EBEE">
<!-- Include the header file via JSTL -->
<c:import url="header.jsp"></c:import>


<!-- Products section -->
<div class="w3-row-padding" style="margin-top:5%">
<div id ="contents" class="w3-container menus" style="width:70%; margin-right:15%; margin-left:15%">
	<p class="w3-panel w3-text-theme w3-center w3-xxlarge"><b>My ${ collection }</b></p>
	<c:forEach var="product" items="${products}">
	<div class="w3-display-container w3-border w3-round-xxlarge w3-white w3-animate-left" style="margin-top:2%">
			<c:choose>
	 			 <c:when test="${ (collection == 'Favorites')}">
	 			 	<button class="w3-display-topright w3-border w3-red w3-display-hover w3-round-xxlarge w3-hover-grayscale"
						onclick="location.href='auth/removefromfavs?productID=${product.id}'" >&#10006</button>
	  			</c:when>
	  			<c:otherwise>
	  				<button class="w3-display-topright w3-border w3-red w3-display-hover w3-round-xxlarge w3-hover-grayscale"
						onclick="location.href='auth/removefromWatchList?productID=${product.id}'" >&#10006</button>
	  			</c:otherwise>
			</c:choose>
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
	                  	<span class="w3-large w3-text-highway-blue"><b><c:out value="${product.name}"></c:out></b></span><br><br>
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
				
				<!-- Renting price -->
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
<script src="js/userInteractions.js"></script>

</html>