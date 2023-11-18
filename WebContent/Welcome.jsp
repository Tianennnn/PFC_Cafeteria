<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title></head>

<head>
<link rel="stylesheet" href="StyleSheet/Styles.css">
<script type="text/javascript" src="Scripts/Functions.js"></script>
</head>

<body>
         
<br><br><br><br><br><br><br><br><br>

<h1 align="center"><FONT COLOR=#a11d00>PFC Cafeteria</FONT></h1>
<h2 align="center"><FONT COLOR=#a11d00>Welcome to Pandora Fried Chicken Cafeteria!</FONT></h2>

<div style="text-align: center;">
<form action="Controller" class="welcome" method="post" onsubmit="return validate()"> 
Please enter your username <input type="text" name="userName" id="username"/>
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