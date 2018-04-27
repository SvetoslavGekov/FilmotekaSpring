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
<!-- HTML Imports -->
<!--<link rel="import" href="../menus/menus.html">

<!-- Color Libraries -->
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-metro.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-highway.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-2017.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-food.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-colors-vivid.css">
<link rel="stylesheet" href="css/color-theme.css">
<link rel="stylesheet" href="css/html_slider.css">

<title>My Account</title>
</head>
<body>
	<!-- Include the header file via JSTL -->
	<c:import url="/html/header.html"></c:import>
	
	<!-- Content -->
	<div id = "content" class="w3-container w3-left" style="margin-top:7%">
		First name: ${ sessionScope.USER.firstName }<br>
		Last name: ${ sessionScope.USER.lastName }<br>
		UserName: ${ sessionScope.USER.username }<br>
		Email: ${ sessionScope.USER.email }<br>
		Phone: ${ sessionScope.USER.phone }<br>
		Date of registration: ${ sessionScope.USER.registrationDate }<br>
		Last login: ${ sessionScope.USER.lastLogin}<br>
		Money: ${ sessionScope.USER.money }<br>
		<input type="button" value="Edit" onclick="document.getElementById('editAccount').style.display = 'block'"><br>
	</div>
	
	<!-- Edit Profile -->
	<div id="editAccount" style="display:none" class="modal">
   		 	<span onclick="document.getElementById('editAccount').style.display='none'" class="close" title="Close Modal">&times;</span>
			<form method= "POST" action="updateProfile" class="modal-content">
			<div class="container">
				 <label for="firstname"><b>First Name</b></label>
				 <input type="text" placeholder="Enter your first name" name="firstname" value="${ sessionScope.USER.firstName }"
					 pattern="[A-Za-z]{1,32}" title="Not including numbers or special characters" style="text-transform: capitalize;"><br>
					 
				 <label for="lastname"><b>Last Name</b></label>
				 <input type="text" placeholder="Enter your last name" name="lastname" value="${ sessionScope.USER.lastName }"
					 pattern="[A-Za-z]{1,32}" title="Not including numbers or special characters" style="text-transform: capitalize;"><br>
				 
				 <label for="email"><b>Email</b></label>
				 <input type="email" placeholder="Enter your email address" name="email" value="${ sessionScope.USER.email }"
				 	 pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"><br>
				 
				 <label for="phone"><b>Phone number</b></label>
				 <input type="text" placeholder="Enter your mobile phone" name="phone" value="${ sessionScope.USER.phone }"
				 	 pattern="08\d{8}" title="Starting with 08 and 10 symbols long"><br>
				
				 <div style="display:none" id="passwordfield">
				 	 <label for="cpass"><b>Current password</b></label>
				 	 <input type="password" id="cpass" placeholder="Enter your password" name="currentPass" ><br>
				 	 
				 	 <label for="npass"><b>New password</b></label>
				 	 <input type="password" id="npass"  placeholder="Enter the new passsword" name="newPass1"><br>
				 	 <input type="password" id="npass2"  placeholder="Repeat the new password" name="newPass2"><br>
				 </div>
				 
				 <input type="button" id="change_pass_btn" onclick="openPassFields()" value="Change Password">
				 <button type="submit" class="savebtn">Save Changes</button>
				 <button type="reset"  class="resetbtn">Reset</button>
			 </div>
			</form>
	
			<script type="text/javascript">
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
		</div>
</body>
</html>