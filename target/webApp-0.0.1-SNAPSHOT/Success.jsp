<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title></head>

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
margin-top: 10px;
padding-top: 20px;
padding-bottom: 20px;
padding-left: 40px;
padding-right: 40px;
width: 500px;
border-radius: 10px;
background-color: rgba(255,255,255, 0.8);
background-size: 100% 100%;
margin:auto;
}

#success{
font-size:20px;
line-height: 30px;
}

#goodDay{
font-size:20px;
line-height: 30px;
text-align: center;
}

#button{
text-align: center;
}
</style>

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

<form action="Controller" method="post"> 
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