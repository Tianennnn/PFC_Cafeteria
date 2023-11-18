<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title></head>

<head>
<link rel="stylesheet" href="StyleSheet/Styles.css">
</head>

<script> 

/**
 * Generates a random 6-digit number and updates the web element to display the number.
 */
function getRef(){
	var refNums = <%= request.getAttribute("refNums") %>;
	var refNum = Math.floor(100000 + Math.random() * 900000);
	if(refNums == null){
		alert("There is an error in the system.")
	}
	else{
		// make sure refNum is unique
		while(refNums.includes(refNum)){
			refNum = Math.floor(100000 + Math.random() * 900000);
		}
		
		document.getElementById("refNum").innerHTML = refNum;
		document.getElementById("referenceNum").value = refNum;	
	}
}


</script>

<body onload="getRef()">

<br><br><br><br><br><br><br><br><br>

<form action="Controller" class="Success" method="post"> 
<h1 align="center">Order Confirmation</h1>
<p id="success">
&emsp;Your order is successfully placed.<br>
&emsp;Your reference number is:
<b>#<span id="refNum"></span></b>
<br>
</p>
<p id="goodDay">
Have a good day!
</p>

<input type="hidden" name="page" value="success"/>
<input type="hidden" id="referenceNum" name="referenceNum" value=""/>
<div id="button">
<input type="submit" value="Log Out" />
</div>

</form>
</body>
</html>