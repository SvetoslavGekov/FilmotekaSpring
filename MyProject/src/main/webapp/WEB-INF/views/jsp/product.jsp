<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
<title>${product.name }</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
	<!-- Include the header file via JSTL -->
	<c:import url="/html/header.html"></c:import>

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
							<button float="left" onClick="buy?productID=${product.id}">Buy</button>
							<button  onClick="rent?productID=${product.id}">Rent</button>
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
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>

<script type="text/javascript">
var dbError = "Sorry, an error occured while accessing the database. Please try again later!";
var noSuchProduct = "Sorry, but you've made a request for a product that does not exist.";

function addProductToFavorites(id) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText == 'true');
			if(result === true){
				alert("Product added to favorites.");
			}
			else{
				alert("Product removed from favorites.");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
	};
	
	xhttp.open("POST", "tofavorites", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID=" + id);
}

function addProductToWatchList(id) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText == 'true');
			if(result === true){
				alert("Product added to watchlist.");
			}
			else{
				alert("Product removed from watchlist.");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
	};
	
	xhttp.open("POST", "towatchlist", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID=" + id);
}
</script>
</html>