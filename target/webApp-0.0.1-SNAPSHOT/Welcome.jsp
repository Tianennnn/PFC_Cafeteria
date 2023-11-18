<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title></head>

<style>

body::after {
  content: "";
  background: url("cafeteria.jpg");
  background-repeat:no-repeat;
  background-size: 100% 100%;
  opacity: 0.5;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  position: fixed;
  z-index: -1;   
}

form {
border: 2px solid white;
margin-top:10px;
padding-top: 20px;
padding-bottom: 20px;
width: 500px;
border-radius: 10px;
background-color: rgba(255,255,255, 0.8);
background-size: 100% 100%;
margin:auto;
}

#errorMsg {
color:red;
}
</style>

<script> 

/**
 * Checks if the input username or password is blank. If so, alert the user. 
 */
function validate(){ 
	 var username = document.getElementById("username").value; 
	 var password = document.getElementById("password").value;
	 if (username == null || username==""){ 
	 	 alert("Username cannot be blank"); 
	 	 return false; 
	 }
	 else if(password==null || password==""){ 
	 	 alert("Password cannot be blank"); 
	 	 return false; 
	 } 
	 else{
		 return true;
	 }
}

</script>

<body>
         
<br><br><br><br><br><br><br><br><br>

<h1 align="center"><FONT COLOR=#a11d00>PFC Cafeteria</FONT></h1>
<h2 align="center"><FONT COLOR=#a11d00>Welcome to Pandora Fried Chicken Cafeteria!</FONT></h2>

<div style="text-align: center;">
<form action="Controller" method="post" onsubmit="return validate()"> 
Please enter your username <input type="text" name="username" id="username"/>
<br>
Please enter your password <input type="password" name="password" id="password"/>
<br>

<span id="errorMsg">
<%
if (request.getAttribute("loginResult") == "unregistered"){
	out.print("The user is not registered");
}
else if(request.getAttribute("loginResult") == "error"){
	out.print("Sorry there is a problem in the system. Please try login later.");
}
else if(request.getAttribute("loginResult") == "incorrect"){
	out.print("The password is incorrect");
}
else{
	out.print("");
}
%>	
</span> 
<br>
<input type="hidden" name="page" value="login"/>
<input type="submit" value="Login" id="LogInBtn"/>
</form>
</div>

</body>
</html>