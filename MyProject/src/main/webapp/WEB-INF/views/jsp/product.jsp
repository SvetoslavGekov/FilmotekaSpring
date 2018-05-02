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
								<span class="w3-large w3-wide">${product.duration} minutes</span>
								<i onclick="addProductToFavorites(${product.id},this)" title="Add to favorites" class="fa fa-heart w3-xlarge"></i>
								<i onclick="addProductToWatchList(${product.id},this)" title="Add to watchlist" class="fa fa-heart w3-xlarge"></i>
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
							</div>
							
						</div>
					</div>
				<div class="w3-container w3-theme w3-text-vivid-white">
					Description:
				</div>
				<div class="w3-container w3-border" style="margin-bottom:10px">
					<span class="w3-small w3-vivid-white w3-text-black"> ${product.description}</span>
				</div>
				</div>
			</div>
		</div>
	</div>








	<!-- Visualize the product information -->
	<div class = "product" style="margin-top:10%">
		<table cellspacing="0" cellpading="0" border="1">
			<tbody>
				<tr>
					<td class ="image" rowspawn="2" valign="top">
						<div>
							<img class = "poster" width="100%" alt = "${product.poster }" title = "${product.name }" 
								src="getPic?pic=${product.poster}">
						</div>
						<div>
							<button float="left" onClick="addProductToFavorites(${product.id})">To Favorites</button>
							<button  onClick="addProductToWatchList(${product.id})">To Watch list</button>
							<button float="left" onClick="addProductToCart(${product.id},true)">Buy</button>
							<button  onClick="addProductToCart(${product.id},false)">Rent</button>
							<button float="left" onClick="showOrHideContent('rate')">Add rating</button>
							
							<div id="rate" class="rating" style="display:none">
							<c:forEach begin="1" end="10" varStatus="loop">
   							 	<span onClick="rateProduct(${ product.id }, ${11 - loop.index})">&#9734;</span>
							</c:forEach>
							</div>
							
						</div>
					</td>
					<td>
						<h4>${product.name}</h4>
						<p>PgRating: ${product.pgRating}</p>
						<p>Duration: ${product.duration}</p><br>
						<span>Genres<br>
						<c:forEach var="genre" items="${product.genres }">
							<span >${genre.value }</span><span class = ghost>|</span>
						</c:forEach>
						</span>
						<div ><h5>Description:</h5>${product.description }</div>
						<div><h5>Writers:</h5><span>${product.writers }</span></div>
						<div><h5>Actors:</h5><span>${product.actors }</span></div>
						
						<c:choose>
							<c:when test="${product.productCategory.id == 1 }">
								<div><h5>Director:</h5><span>${product.director}</span></div>
							</c:when>
							
							<c:when test="${product.productCategory.id == 2 }">
								<div><h5>Season:</h5><span>${product.season}</span></div>
								<div><h5>Finished airing:</h5><span>${product.finishedAiring}</span></div>
								
							</c:when>
						</c:choose>
						<button id="addreviewbtn" onclick="showOrHideContent('addreview')">Add review</button>
						<button id="reviewsbtn" onclick="showOrHideContent('reviews')">Show reviews</button>
					</td>
				</tr>
			</tbody>
		</table>
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