<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FilmotekaBG</title>
<head>
<link rel="stylesheet" href="css/user_form.css">
</head>
<body>

<h2>Login Form</h2>

<form method ="POST" action= "login">
  <div class="imgcontainer">
    <img src="img/avatar.png" alt="Avatar" class="avatar">
  </div>

  <div class="container">
    <label for="username"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="username" value = "sgekov" required>

    <label for="password"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="password" value = "Sgekov123" required>
        
    <button type="submit">Login</button>
  </div>

  <div class="container" style="background-color:#f1f1f1">
    <span class="register">Don't have an account?  
    	<span onclick="document.getElementById('id01').style.display='block'" style="width:auto;" style="color:blue;" class="signup"><b><i>Sign up now.</i></b></span>
    </span>
  </div>
</form>

<div id="id01" class="modal">
  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
  <form method="POST" class="modal-content" action="register">
    <div class="container">
      <h1>Sign Up</h1>
      <p>Please fill in this form to create an account.</p>
      <hr>
      <label for="username"><b>Username</b></label>
      <input id ="regUserName" type="text" placeholder="Enter Username" name="username" required
      pattern="^(?=.*[a-z]).{4,}" title="At least 4 characters long">
      
      <label for="email"><b>Email</b></label>
      <input id ="regEmail" type="email" placeholder="Enter Email" name="email" required>

      <label for="psw"><b>Password</b></label>
      <input id ="regPassword" type="password" placeholder="Enter Password" name="password" required 
      pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$" 
      title = "At least 6 characters containing: 1 Uppercase letter, 1 Lowercase letter and 1 number">
      <img id = "imgPassword" src="" alt="" title="">
      
      <label for="firstname"><b>First Name</b></label>
      <input type="text" placeholder="Enter First Name" name="firstName" required>
      
      <label for="lastname"><b>Last Name</b></label>
      <input type="text" placeholder="Enter Last Name" name="lastName" required>

      <div class="clearfix">
        <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
        <button type="submit" class="signupbtn">Sign Up</button>
      </div>
    </div>
  </form>
</div>

</html>