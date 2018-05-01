<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- Color Libraries -->
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

<title>My Account</title>

<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
	<!-- Include the header file via JSTL -->
	<c:import url="header.jsp"></c:import>
	
	
	<!-- Content -->
    <div class="container" style="margin-top:7%">    
          <div class="row">
               <div class="panel panel-default">
                <div class="panel-heading">  <h4 >User Profile</h4></div>
                 <div class="panel-body">
                 	<form method= "POST" action="auth/updateProfile" enctype="multipart/form-data"></form>
	                <div class="col-md-4 col-xs-12 col-sm-6 col-lg-4">
	                 <c:choose>
		                  <c:when test="${empty sessionScope.USER.profilePicture}">
		                  	<img alt="User Pic"  src="img/avatar.jpg" id="profile-image1" class="img-circle img-responsive">
		                  </c:when>
		                  <c:otherwise>
		                  	<img alt="User Pic"  src="getProfilePic?pic=${sessionScope.USER.profilePicture}" id="profile-image1" class="img-circle img-responsive">
		                  </c:otherwise>
	                 </c:choose>
	                </div>
	                <div class="col-md-8 col-xs-12 col-sm-6 col-lg-8" >
	                    <div class="container" >
	                      <h2>${ sessionScope.USER.firstName } ${ sessionScope.USER.lastName }</h2>
	                      <p>Date Of Joining: <b>
	                      	${ sessionScope.USER.registrationDate.dayOfMonth} 
		                    ${ sessionScope.USER.registrationDate.month} 
		                    ${ sessionScope.USER.registrationDate.year}
	                      </b></p>
	                    
	                     
	                    </div>
	                     <hr>
	                    <ul class="container details  w3-text-theme" >
	                      <li><p><span class="glyphicon glyphicon-user one" style="width:50px;"></span>${ sessionScope.USER.username }</p></li>
	                      <li><p><span class="glyphicon glyphicon-envelope one" style="width:50px;"></span>${ sessionScope.USER.email }</p></li>
	                      <li><p><span class="glyphicon glyphicon-phone one" style="width:50px;"></span>${ sessionScope.USER.phone } </p></li>
	                      <li><p><span class="glyphicon glyphicon-euro one" style="width:50px;"></span>${ sessionScope.USER.money }</p></li>
	                    </ul>
	                    <hr>
	                    <div class="col-sm-5 col-xs-6 tital" >last login: 
	                      ${ sessionScope.USER.lastLogin.dayOfWeek}<br>
	                      ${ sessionScope.USER.lastLogin.dayOfMonth} 
	                      ${ sessionScope.USER.lastLogin.month} 
	                      ${ sessionScope.USER.lastLogin.year} at 
	                      ${ sessionScope.USER.lastLogin.hour}:${ sessionScope.USER.lastLogin.minute}
	                    </div>
	                    <br>        
	                    <button class="w3-button w3-section w3-theme w3-ripple w3-padding w3-hover-teal" onclick="showOrHideContent('editAccount')">Edit profile</button>
	                </div>
          		</div>
      		</div>
      	</div>
    </div>
		
	<!-- Edit Profile -->
    <div id="editAccount" style='display:none' class="container" style="margin-top:7%">    
          <div class="row">
               <div class="panel panel-default">
                <div class="panel-heading">  <h4 >Update Profile</h4></div>
                 <div class="panel-body">
                 <form method= "POST" enctype="multipart/form-data" action="auth/updateProfile">
	                <div class="col-md-8 col-xs-12 col-sm-6 col-lg-8" >
	                	<div class="container" >
	                		<label for="profilePicture"><b>Profile picture:</b></label>
	                		<div class="w3-col" style="width:50px"><i class="w3-xlarge fa fa-user w3-text-theme"></i></div>
	                      <input class="w3-button w3-section w3-ripple w3-padding w3-hover-teal" type="file" name="profilePicture" accept=".jpg"/>
	                    </div>
	                    <hr>
	                    <div class="container" >
	                      <label for="firstname"><b>First Name:</b></label>
	                      	<div class="w3-col" style="width:50px"><i class="w3-xlarge fa fa-address-card-o w3-text-theme"></i></div>
							 <input class="w3-input w3-border" type="text" placeholder="Enter your first name" name="firstname" value="${ sessionScope.USER.firstName }"
							 pattern="[A-Za-z]{1,32}" title="Not including numbers or special characters" style="text-transform: capitalize;">
	                    </div>
	                    <hr>
	                    <div class="container" >
	                      <label for="lastname"><b>Last Name:</b></label>
	                      <div class="w3-col" style="width:50px"><i class="w3-xlarge fa fa-address-card-o w3-text-theme"></i></div>
							 <input class="w3-input w3-border" type="text" placeholder="Enter your last name" name="lastname" value="${ sessionScope.USER.lastName }"
							 pattern="[A-Za-z]{1,32}" title="Not including numbers or special characters" style="text-transform: capitalize;">
	                    </div>
	                     <hr>
	                    <div class="container" >
	                       <label for="email"><b>Email:</b></label>
	                      	 <div class="w3-col" style="width:50px"><i class="w3-xlarge fa fa-envelope-o w3-text-theme"></i></div>
							 <input class="w3-input w3-border" type="email" placeholder="Enter your email address" name="email" value="${ sessionScope.USER.email }"
				 	 			pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$">
	                    </div>
	                    <hr>
	                    <div class="container" >
	                    	<label for="phone"><b>Phone number:</b></label>
	                    	 <div class="w3-col" style="width:50px"><i class="w3-xlarge glyphicon glyphicon-phone one w3-text-theme"></i></div>
							 <input class="w3-input w3-border" type="text" placeholder="Enter your mobile phone" name="phone" value="${ sessionScope.USER.phone }"
						  	    pattern="08\d{8}" title="Starting with 08 and 10 symbols long"><br>
	                    </div>
	                    <hr>
	                    <div class="container" >
	                   		 <div class="w3-col" style="width:50px"><i class="w3-xlarge fa fa-key w3-text-theme"></i></div>
	                    	<button id="change_pass_btn" class="w3-button w3-section w3-theme w3-ripple w3-padding w3-hover-teal" onclick="openPassFields()">Change Password</button>
	                    </div>
	                    <hr>
	                    <div style="display:none" id="passwordfield">
	                   		 <div class="container" >
				 	 			<label for="cpass"><b>Current password:</b></label>
				 	 			<input class="w3-input w3-border" type="password" id="cpass" placeholder="Enter your password" name="currentPass" >
				 	 		</div>
				 	 		<hr>
				 	 		<div class="container" >
					 	 		<label for="npass"><b>New password:</b></label>
					 	 		<input class="w3-input w3-border" type="password" id="npass"  placeholder="Enter the new passsword" name="newPass1"><br>
					 	 		<hr>
					 			<input class="w3-input w3-border" type="password" id="npass2"  placeholder="Repeat the new password" name="newPass2"><br>
					 		</div>
				 		</div>
				 		<hr>
					 	<div class="container" >
					 			<button type="submit" class="w3-button w3-section w3-theme w3-ripple w3-padding w3-hover-teal">Save Changes</button><br>
					 			<button type="reset" class="w3-button w3-section w3-theme w3-ripple w3-padding w3-hover-teal">Reset</button>
		                </div>
		            </div> 
		           </form>	
          		</div>
      		</div>
      	</div>
    </div>
</body>
<script>

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
</script>


<script src="js/userInteractions.js">
</script>
</html>