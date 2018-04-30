<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html >
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
<title>${product.name }</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>

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

<script type="text/javascript">

</script>
</html>