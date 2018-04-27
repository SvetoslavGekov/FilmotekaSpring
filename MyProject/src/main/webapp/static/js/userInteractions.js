/**
 * 
 */
var dbError = "Sorry, an error occured while accessing the database. Please try again later!";
var noSuchProduct = "Sorry, but you've made a request for a product that does not exist.";

function addProductToCart(id, willBuy) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText == 'true');
			if(result === true){
				alert("Product successfully added to cart.");
			}
			else{
				alert("Sorry, but you already own this product!");
			}
		}
		else if(this.readyState == 4 && this.status == 500){
			alert(dbError);
		}
		else if(this.readyState == 4 && this.status == 400){
			alert(noSuchProduct);
		}
	};
	
	
	xhttp.open("POST", "addtocart", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&willBuy=" + willBuy);
}


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