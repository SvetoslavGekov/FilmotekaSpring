/**
 * 
 */
var dbError = "Sorry, an error occured while accessing the database. Please try again later!";
var noSuchProduct = "Sorry, but you've made a request for a product that does not exist.";

function addProductToCart(id, willBuy) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				showAlert("Product added to cart.", 1);
				return;
			}
			else if (result === 'false'){
				showAlert("Sorry, but you already own this product!.", 2);
				return;
			}
		}
	};
	
	
	xhttp.open("POST", "auth/addtocart", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&willBuy=" + willBuy);
}



function addProductToFavorites(id, element) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
		else if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				showAlert("Product added to favorites.", 1);
				swapAddToFavorites(element,1);
				return;
			}
			else if (result === 'false'){
				showAlert("Product removed from favorites.", 1);
				swapAddToFavorites(element,2);
				return;
			}
		}
	};
	
	xhttp.open("POST", "auth/tofavorites", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID=" + id);
}

function addProductToWatchList(id,element) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
		}
		if (this.readyState == 4 && this.status == 200) {
			var result = (this.responseText);
			if(result === 'true'){
				showAlert("Product added to watchlist.", 1);
				swapAddToWatchlist(element,1);
				return;
			}
			else if (result === 'false'){
				showAlert("Product removed from watchlist.", 1);
				swapAddToWatchlist(element,2);
				return;
			}
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
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			showAlert("Product successfully rated.", 1);
			location.reload();
			return;
		}
	};
	
	
	xhttp.open("POST", "auth/rateproduct", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ id +"&rating=" + rating);
}

function redirectToUnauthorizedPage(){
	window.location.href = "";
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
			alert("The size of your review exceeds the given limit of 480 characters. Please keep it short and simple.");
		}else if (reviewContent.length < 3 ){
			alert("Your review is too short. Please add some more flavor.");
		}
		return;
	}
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 500){
			showAlert(dbError, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 400){
			showAlert(noSuchProduct, 3);
			return;
		}
		else if(this.readyState == 4 && this.status == 401){
			redirectToUnauthorizedPage();
			return;
		}
		else if (this.readyState == 4 && this.status == 200) {
			showAlert("Added review:\n"+reviewContent+"\n For product with id = "+productID, 1);
			window.location.reload();
			return;
		}
	};
	
	xhttp.open("POST", "auth/addreview", true);
	xhttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhttp.send("productID="+ productID +"&reviewContent=" + reviewContent);
}

function openPassFields(){
	document.getElementById("cpass").setAttribute("required", "");
	document.getElementById("cpass").setAttribute("pattern","^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
	document.getElementById("cpass").setAttribute("title", "At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number");
	
	document.getElementById("npass").setAttribute("required", "");
	document.getElementById("npass").setAttribute("pattern","^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
	document.getElementById("npass").setAttribute("title", "At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number");
	
	document.getElementById("npass2").setAttribute("required", "");
	document.getElementById("npass2").setAttribute("pattern","^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
	document.getElementById("npass2").setAttribute("title", "At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number");
	
    if(document.getElementById("passwordfield").style.display == 'none'){
        document.getElementById("passwordfield").style.display = 'block';
    }else{
        document.getElementById("passwordfield").style.display = 'none';
    }
}

function showAlert(message, type){
	
	var div = document.createElement("div");
	
	if(type ==1){
		//Success
		div.className += "w3-panel w3-green w3-display-container w3-round-xxlarge w3-bottom";
	}
	if(type == 2){
		//Information
		div.className += "w3-panel w3-blue w3-display-container w3-round-xxlarge w3-bottom";
	}
	if(type == 3){
		//Warning
		div.className += "w3-panel w3-yellow w3-display-container w3-round-xxlarge w3-bottom";
	}
	
	
	div.setAttribute("id", "resultAlert");
	div.style.width="30%";
	div.style.marginLeft="35%"

	var span = document.createElement("span");
	span.className += "w3-button w3-hover-red w3-large w3-display-topright w3-round-xxlarge"
	span.setAttribute('onclick','document.getElementById("resultAlert").remove()');
	var x = document.createTextNode('x');
	span.appendChild(x);

	var paragraph = document.createElement("p");
	var nodec = document.createTextNode(message);
	paragraph.appendChild(nodec);

	div.appendChild(span);
	div.appendChild(paragraph);
	document.body.appendChild(div);

	setTimeout(function(){
	     div.remove();
	},2000);
}

function swapAddToFavorites(element, type){
	if(type == 1){
		element.classList = "w3-button fa fa-heart-o w3-xxlarge w3-text-red";
		element.setAttribute("title", "Remove from favorites");
	}
	if(type == 2){
		element.classList = "w3-button fa fa-heart w3-xxlarge w3-text-red";
		element.setAttribute("title", "Add to favorites");
	}
}

function swapAddToWatchlist(element,type){
	if(type == 1){
		element.classList = "w3-button fa fa-eye-slash w3-xxlarge w3-text-green";
		element.setAttribute("title", "Remove from watchlist");
	}
	if(type == 2){
		element.classList = "w3-button fa fa-eye w3-xxlarge w3-text-green";
		element.setAttribute("title", "Add to watchlist");
	}
}
