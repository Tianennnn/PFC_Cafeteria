<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
</head>

<head>
<link rel="stylesheet" href="StyleSheet/Styles.css">
<script type="text/javascript" src="Scripts/Functions.js"></script>
</head>

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

</script>

<body onload="displayCards()">

<br><br><br><br><br><br><br><br><br>
<div id="container">
<h1 align="center">Card Payment</h1>
<h2>Saved Cards:</h2>
<form action="Controller" class ="Payment" method="post" onsubmit="return checkInput()"> 

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
<span id="deleteErrorMsg">

<%
if(request.getAttribute("deleteStatus") == "error"){
	out.print("Sorry there is a problem in the system. Please try later.\n");
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
<form action="Controller" class ="Payment" method="post" onsubmit="return validateCard()"> 
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