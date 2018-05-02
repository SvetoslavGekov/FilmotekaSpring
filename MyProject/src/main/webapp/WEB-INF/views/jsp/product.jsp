<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html>
<head>
<style>
.fa {
    font-size: 50px;
    cursor: pointer;
    user-select: none;
}
</style>


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
<title>${product.name }</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body bgcolor="#E9EBEE">
	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

	<!-- Visualize the product information -->
	<div class="w3-container" style="width:70%; margin-top:4%; margin-left:15%">
		<div class="w3-container w3-theme w3-border">
			<h1>Product Information</h1>
		</div>
		<div class="w3-container w3-white w3-border">
			<div class="w3-row-padding">
				<div class ="w3-container w3-margin w3-border">
					<div class="w3-row-padding w3-margin">
					
						<!-- Left part of the product info page -->
						<div class="w3-col s4">
							<!-- Poster -->
							<div class="w3-container ">
								<img src="getPic?pic=${product.poster}" title="${product.name}" class = "w3-round w3-margin w3-center"
								 style="width:100%; height:350px; margin-bottom:5%;">
								 
								 <!-- Pricing -->
								 <span class="">Price:<br></span>
								<c:if test="${product.originalBuyCost != product.buyCost }">
								<span class="w3-tag w3-round-large w3-red"><del>${product.originalBuyCost }  <i class="fa fa-euro"></i></del></span>
								</c:if>
								<span class="w3-tag w3-round-large w3-green"><fmt:formatNumber value="${product.buyCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
								<span class="w3-button w3-small w3-round w3-theme w3-right" onclick="addProductToCart(${product.id},true)">Buy</span>
								<br>
								<span class="">Renting price:<br></span>
								<c:if test="${product.originalBuyCost != product.buyCost }">
								<span class="w3-tag w3-round-large w3-red"><del>${product.originalRentCost }  <i class="fa fa-euro"></i></del></span>
								</c:if>
								<span class="w3-tag w3-round-large w3-green"><fmt:formatNumber value="${product.rentCost}" maxFractionDigits="2"/> <i class="fa fa-euro"></i></span>
								<span class="w3-button w3-small w3-round w3-theme w3-right" onclick="addProductToCart(${product.id},false)">Rent</span>
								<br>
								
								<c:if test="${product.originalBuyCost != product.buyCost }">
								<span class="w3-tax w3-large w3-theme">Save ${product.salePercent }% now!</span>
								</c:if>
							</div>
							
							<div class="w3-container">
	
							</div>
						</div>
						
						<!-- Right part of the product info page -->
						<div class="w3-col s8">
							
							<div class="w3-container w3-text-theme">
								<!-- Name -->
								<span class="w3-wide w3-xxlarge"><b>${product.name}</b></span><br><span class = ghost>|</span>
														
								<!-- Year -->
						  		<span class="w3-large w3-wide"><b>(${product.releaseDate.year})</b></span><span class = ghost>|</span>
								
								<!-- Duration -->
								<span class="w3-large w3-wide">${product.duration} minutes</span><br>
								<c:choose >
									<c:when test="${not isInFavorites }">
										<i onclick="addProductToFavorites(${product.id},this)" title="Add to favorites" class="fa fa-heart w3-xxlarge w3-text-red"></i>
									</c:when>
									<c:otherwise>
										<i onclick="addProductToFavorites(${product.id},this)" title="Remove from favorites" class="fa fa-heart-o w3-xxlarge w3-text-red"></i>
									</c:otherwise>
								</c:choose>
								
								<c:choose >
								<c:when test="${not isInWatchlist }">
										<i onclick="addProductToWatchList(${product.id},this)" title="Add to watchlist"	class="fa fa-eye w3-xxlarge w3-text-green"></i>
									</c:when>
									<c:otherwise>
										<i onclick="addProductToWatchList(${product.id},this)" title="Remove from watchlist"	class="fa fa-eye-slash w3-xxlarge w3-text-green"></i>
									</c:otherwise>
								</c:choose>
								<br>
								<hr>
							</div>
							
							<!-- Genres -->
							<div class="w3-container">
						  	<c:forEach var="genre" items="${product.genres}">
					  			<div class="w3-tag w3-round w3-food-blueberry w3-tiny" style="padding:3px">
									<div class="w3-tag w3-round w3-food-blueberry w3-border w3-border-white">
										${genre.value}
									</div>
								</div>
					  		</c:forEach>
					  		<span class = ghost>|</span>
					  		
					  		<!-- Pg Rating -->
				  			<div class="w3-tag w3-round w3-green w3-tiny" style="padding:3px">
								<div class="w3-tag w3-round w3-green w3-border w3-border-white">
									${product.pgRating}
								</div>
							</div>
							<span class = ghost>|</span>
							
							<!-- Viewer Rating -->
							<div class="w3-tag w3-round w3-vivid-reddish-purple w3-tiny" style="padding:3px">
							  <i class="fa fa-star-o"></i>
							  <div class="w3-tag w3-round w3-vivid-reddish-purple w3-border w3-border-white">
									<fmt:formatNumber value="${product.viewerRating}" maxFractionDigits="2"/>
							  </div>
							  
							</div>								
							<span class="w3-small w3-text-black">(Votes: ${product.raters.size()})</span>					
							</div>
							
							<div class="w3-container" style="margin-top:10px">
								<!-- Writers -->
								<div class="w3-container w3-small  w3-theme w3-text-vivid-white">Writers: </div>
								<span class="w3-medium"><b>${product.writers}</b></span><br>
								
								<!-- Actors -->
								<div class="w3-container w3-small  w3-theme w3-text-vivid-white" style="margin-top:10px">Actors: </div>
								<span class="w3-medium"><b>${product.actors}</b></span><br>
								
								<!-- Category specific info -->
								<c:choose>
									<c:when test="${product.productCategory.id == 1}">
										<!-- Director-->
										<div class="w3-container w3-small  w3-theme w3-text-vivid-white" style="margin-top:10px">Director: </div>
										<span class="w3-medium"><b> ${product.director}</b></span>
									</c:when>
									<c:when test="${product.productCategory.id == 2}">
										<!-- Season-->
										<div class="w3-container w3-small  w3-theme w3-text-vivid-white" style="margin-top:10px">Season: </div>
										<span class="w3-medium"><b> ${product.season}</b></span><br>
										<!-- Finished airing-->
										<div class="w3-container w3-small  w3-theme w3-text-vivid-white" style="margin-top:10px">Finished airing: </div>
										<span class="w3-medium"><b> ${product.finishedAiring}</b></span>
									</c:when>
								</c:choose>
								
								<div class="w3-container w3-theme w3-text-vivid-white" style="margin-top: 15px;">Description:
								</div>
								<div class="w3-container w3-border" style="margin-bottom:10px">
									<span class="w3-small w3-vivid-white w3-text-black"> ${product.description}</span>
								</div>
								<button id="addreviewbtn" onclick="showOrHideContent('addreview')">Add review</button>
								<button id="reviewsbtn" onclick="showOrHideContent('reviews')">Show reviews</button>
							</div>
						</div>
					</div>
					<!-- Reviews  -->
					<div>
					
					</div>
					<div id="addreview" class = "w3-container" style="display:none" style="margin-top:10px">
						<textarea rows="7" cols="50" style="resize:none" name="content"
							placeholder="${ sessionScope.USER.firstName }, tell us what you think about this product..."
							maxlength="480" minlength="5"
						></textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Adding review -->
	<div id="addreview" style="display:none" style="margin-top:10px">
		<table width="500" align="center" style="margin-top:1%">
					<thead>
						<tr>
						<th align="left">${ sessionScope.USER.username }</th>
						</tr>
					</thead>
					<tbody>
						<tr>
						<td>
							<textarea rows="7" cols="70" style="resize:none" name="content" id="reviewcont"
									placeholder="${ sessionScope.USER.firstName }, what you think about this product..."
								maxlength="480" minlength="5"></textarea>
						</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
						<td align="right"><button onclick="addReview(document.getElementById('reviewcont').value, ${ product.id })">Submit</button></td>
						</tr>
					</tfoot>
		</table>
	</div>
	
	
	
	<!-- Review tables -->
	<div id="reviews" style="display:none" style="margin-top:4%">
	<c:choose>
		<c:when test="${not empty reviews }">
		<c:forEach var="review" items="${reviews}">
			<table class="review" align="right" style="margin-top:2%">
				<thead>
					<tr>
					<th>${ review.username }</th>
					</tr>
				</thead>
				<tbody>
					<tr>
					<td>${ review.content }</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td>
							${ review.dateTime.dayOfMonth} 
		                    ${ review.dateTime.month} 
		                    ${ review.dateTime.year} at 
		                    ${ review.dateTime.hour}:${ review.dateTime.minute}:${ review.dateTime.second }
						</td>
					</tr>
				</tfoot>
			</table>
		</c:forEach>
		</c:when>
		
		<c:otherwise>
			<h3 align="center"><i>Be the first to write a review</i></h3>
		</c:otherwise>
	</c:choose>
	</div>	
	
</body>

<script src="js/userInteractions.js">
</script>
</html>