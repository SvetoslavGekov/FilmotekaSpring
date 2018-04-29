/**
 * 
 */
var dbError = "Sorry, an error occured while accessing the database. Please try again later!";
var noSuchProduct = "Sorry, but you've made a request for a product that does not exist.";

function addProductToCart(id, willBuy) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				alert("Product successfully added to cart.");
			}
			else if (result === 'false'){
				alert("Sorry, but you already own this product!");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	
	xhttp.open("POST", "auth/addtocart", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&willBuy=" + willBuy);
}

function rateProduct(id, rating) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Product successfully rated with rating = "+rating);
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	
	xhttp.open("POST", "auth/rateproduct", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&rating=" + rating);
}


function addProductToFavorites(id) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				alert("Product added to favorites.");
			}
			else if (result === 'false'){
				alert("Product removed from favorites.");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	xhttp.open("POST", "auth/tofavorites", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID=" + id);
}

function addProductToWatchList(id) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				alert("Product added to watchlist.");
			}
			else if (result === 'false'){
				alert("Product removed from watchlist.");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	xhttp.open("POST", "auth/towatchlist", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID=" + id);
}

function rateProduct(id, rating) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Product successfully rated with rating = "+rating);
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	
	xhttp.open("POST", "auth/rateproduct", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&rating=" + rating);
}

function redirectToUnauthorizedPage(){
	window.location.href = "unauthorized";
}

//If these functions are here(userInteractions.js), my btns can't find them
// so thay are now in product.jsp

function showOrHideContent(btnid){
	if(document.getElementById(btnid).style.display == 'block'){
		document.getElementById(btnid).style.display = 'none';
	}
	else{
		document.getElementById(btnid).style.display = 'block';
	}
}

function addReview(reviewContent, productID) {
	//clear the textarea
	document.getElementById('reviewcont').value = '';
	//add the review only if it is between 3 and 480 characters
	if(reviewContent.length < 3 || reviewContent.length > 480){
		if(reviewContent.length > 480){
			alert("What is the meaning of a too long review? That's pretty annoying for the other customers.");
		}else{
			alert("What is the meaning of a too short review? Help other customers make the right choice.");
		}
		return;
	}
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			alert("Added review:\n"+reviewContent+"\n For product with id = "+productID);
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
	};
	
	xhttp.open("POST", "auth/addreview", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ productID +"&reviewContent=" + reviewContent);
}

