<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
</head>

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

#container {
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

form{
text-align: center;
}

#errorMsg {
color:red;
}
</style>

<script> 

/**
 * Shows the customer's saved cards. For each card, displays a checkbox for user to select. 
 */
function displayCards(){
	var card1 = <%= request.getAttribute("card1") %>
	var card2 = <%= request.getAttribute("card2") %>
	var card3 = <%= request.getAttribute("card3") %>
	
	// Initially, hide the select options for the cards
	document.getElementById('card1Entry').style.display = "none";
	document.getElementById('card2Entry').style.display = "none";
	document.getElementById('card3Entry').style.display = "none";
	
	if(card1!=null){
		// show the select option of card1
		document.getElementById('card1Entry').style.display = "";
		document.getElementById("card1Num").innerHTML = card1;
		if(card2!=null){
			// show the select option of card2
			document.getElementById('card2Entry').style.display = "";
			document.getElementById("card2Num").innerHTML = card2;
			if(card3!=null){
				// show the select option of card3
				document.getElementById('card3Entry').style.display = "";
				document.getElementById("card3Num").innerHTML = card3;
			}
		}
	}
	else{
		// When the user has no saved cards:
		// Display the message
		document.getElementById("noCards").innerHTML = "You don't have any saved cards.";
		// Hide the "Delete" button and the "Pay" button
		document.getElementById('deleteBtn').style.display = "none";
		document.getElementById('payBtn').style.display = "none";
	}
	
}

/**
 * Checks whether the input card number is blank. If so, alert the user.
 *
 * @returns  A boolean value that indicates whether the input card number is blank.
 */
function isNewNumNonEmpty(){ 
	 var result = false;
	 var newNum = document.getElementById("newNum").value; 
	 if (newNum == ""){ 
	 	 alert("Card numbers cannot be blank."); 
	 }
	 else{
		 result = true;
	 }
	 return result;
}

/**
 * Checks whether the input card number is a 16-digit number. If not, alert the user.
 *
 * @returns  A boolean value that indicates whether the input card number is a 16-digit number.
 */
function is16Digits(){ 
	 var result = false;
	 var newNum = document.getElementById("newNum").value; 
	 if (newNum.length == 16){ 
		 result = true;
	 }
	 else{
		 result = false;
		 alert("Card numbers must be 16-digit long."); 
	 }
	 return result;
}


/**
 * Checks whether the input card number is non-empty and 16-digit long. If not, alert the user.
 *
 * @returns  A boolean value that indicates whether the input card number is valid.
 */
 
function validateCard(){
	var result = false;
	if(isNewNumNonEmpty()){
		result = is16Digits();
	}
	else{
		result = false;
	}
	return result;
}

/**
 * Checks whether the user has chooosed at least one payment card. If not, alert the user.
 *
 * @returns  A boolean value that indicates if the at least one payment card is selecteds.
 */
function checkInput(){
	 // check if any card is selected
	 var card1 = document.getElementById("card1").checked; 
	 var card2 = document.getElementById("card2").checked;
	 var card3 = document.getElementById("card3").checked;
	 if( card1 || card2 || card3){
		 return true;
	 }else{
		 alert("Please select one card to delete or pay."); 
		 return false;
	 }
}

</script>

<body onload="displayCards()">

<br><br><br><br><br><br><br><br><br>
<div id="container">
<h1 align="center">Card Payment</h1>
<h2>Saved Cards:</h2>
<form action="Controller" method="post" onsubmit="return checkInput()"> 

<div id="methods">
	  <span id="noCards"></span>
	  <div id="card1Entry">
      <input type="radio" id="card1" name="card" value="one" />
      <label for="card1" id="card1Num"> </label>
      <br>
      </div>
      <div id="card2Entry">
      <input type="radio" id="card2" name="card" value="two"/>
      <label for="card2" id="card2Num"> </label>
      <br>
      </div>
      <div id="card3Entry">
      <input type="radio" id="card3" name="card" value="three"/>
      <label for="card3" id="card3Num"> </label>
      <br>
      </div>
</div>
<br>
<span id="errorMsg">

<%
if(request.getAttribute("deleteStatus") == "error"){
	out.print("Sorry there is a problem in the system. Please try later.");
}
else{
	out.print("");
}
%>	
</span>

<input type="hidden" name="page" value="payment"/>
<input type="submit" id="deleteBtn" name="btnClicked" value="Delete"/>
&emsp; &emsp; &emsp; &emsp;
<input type="submit" id="payBtn" name="btnClicked" value="Pay"/>
</form>
<br>

<h2> Pay Using A New Card:</h2>
<form action="Controller" method="post" onsubmit="return validateCard()"> 
<div>
Your new card number: <input type="number" name="newNum" id="newNum"/>
<br>
</div>
<span id="errorMsg">
<%
if(request.getAttribute("saveResult") == "Fail"){
	out.print("Sorry there is a problem in the system. Please try later.");
}
else if(request.getAttribute("saveResult") == "Full"){
	out.print("You can only save maxmun 3 cards.");
}
else if(request.getAttribute("saveResult") == "Duplicate"){
	out.print("The card is already saved.");
}
else{
	out.print("");
}
%>	
</span>
<br>
<input type="hidden" name="page" value="payment">
<input type="submit" id="save" name="btnClicked" value="Save"/>
&emsp; &emsp; &emsp; &emsp;
<input type="submit" id="payNew" name="btnClicked" value="Pay"/>
</form>

</div>
</body>
</html>