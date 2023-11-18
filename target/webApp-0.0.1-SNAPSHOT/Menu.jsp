<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
</head>

<style>
#greeting {
	color: black;
	font-size: 40px;
	font-weight: bold;
	margin-left: 50px;
}

div.horizontal-container {
	display: flex;
}
	         
div.left-panel {
	flex: 1;
	margin-top:20px;
	margin-right: 40px;
	margin-left: 50px;
	margin-bottom: 50px;
	border: 2px solid white;
	padding-top: 10px;
	width: 450px;
	height: 520px;
	border-radius: 10px;
	background-color: rgba(255,255,255, 0.8);
}
	        
div.right-panel {
	flex: 1;
	margin-top:20px;
	margin-right: 40px;
	margin-bottom: 50px;
	border: 2px solid white;
	
	padding-left: 50px;
	padding-right: 20px;
	padding-top: 10px;
	width: 450px;
	height: 520px;
	border-radius: 10px;
	background-color: rgba(255,255,255, 0.8);
}
	        
body::after {
	content: "";
	background: url('cafeteria.jpg');
	background-repeat: no-repeat;
	background-size: 100% 100%;
	opacity: 0.5;
	top: 0; left: 0; bottom: 0; right: 0;
	position: fixed;
	z-index: -1;
}
  
li{
	display:table;
	margin:0px auto 0px auto;
}

span {
  	line-height: 30px;
 	padding: 17px;
	font-size: 20px;
}

#total {
	font-size: 20px;
	display: inline;
}

p.totalMoney{
	font-size: 25px;
	display: inline;
}

p.item{
	font-size:20px;
}
#methods{
	font-size:20px;
	line-height: 30px;
	text-align: center;
}

#errorMsg {
color:red;
}

</style>

<script> 
let burgerCount = 0;
let sandwichCount = 0;
let friedChickenCount = 0;
let colaCount = 0;
let total = 0;

/**
 * Adds input value to total, then update the value of total that displays on the page 
 *
 * @param   adjust The value to be added to total
 */
function addTotal(adjust){
	total += adjust;
	document.getElementById("sum").innerHTML = "<b><i>" + total.toFixed(2) + "</i></b>";
}

/**
 * Subtracts input value from total, then update the value of total that displays on the page 
 *
 * @param   adjust The value to be added to total
 */
function minusTotal(adjust){
	total -= adjust;
	// Sometimes due to rounding, the value of total might be negative, i.e. -0.000.....
	if (total<0){
		total = 0;
	}
	document.getElementById("sum").innerHTML = "<b><i>" + total.toFixed(2) + "</i></b>";
}

/**
 * Adds one to the specified counter, then update the counter that displays on the page 
 *
 * @param   counterName The name of the counter to be incremented by 1
 */
function counterAddOne(counterName){ 
	 if(counterName == "burgerCounter"){
		 burgerCount++;
		 addTotal(9.99);
		 document.getElementById("burgerCounter").innerHTML = "<span>" + burgerCount + "</span>";
	 }
	 else if(counterName == "sandwichCounter"){
		 sandwichCount++;
		 addTotal(8.99);
		 document.getElementById("sandwichCounter").innerHTML = "<span>" + sandwichCount + "</span>";
	 }
	 else if(counterName == "friedChickenCounter"){
		 friedChickenCount++;
		 addTotal(12.99);
		 document.getElementById("friedChickenCounter").innerHTML = "<span>" + friedChickenCount + "</span>";
	 }
	 else if(counterName == "colaCounter"){
		 colaCount++;
		 addTotal(1.99);
		 document.getElementById("colaCounter").innerHTML = "<span>" + colaCount + "</span>";
	 }
}

/**
 * Minus the specified counter by one, then update the counter that displays on the page 
 *
 * @param   counterName The name of the counter to be substracted by 1
 */
function counterMinusOne(counterName){
	 if(counterName == "burgerCounter"){
		 if (burgerCount > 0){
			 burgerCount--;
			 minusTotal(9.99);
			 document.getElementById("burgerCounter").innerHTML = "<span>" + burgerCount + "</span>";
		 }
	 }
	 else if(counterName == "sandwichCounter"){
		 if (sandwichCount > 0){
			 sandwichCount--;
			 minusTotal(8.99);
			 document.getElementById("sandwichCounter").innerHTML = "<span>" + sandwichCount + "</span>";
		 }
	 }
	 else if(counterName == "friedChickenCounter"){
		 if (friedChickenCount > 0){
			 friedChickenCount--;
			 minusTotal(12.99);
			 document.getElementById("friedChickenCounter").innerHTML = "<span>" + friedChickenCount + "</span>";
		 }
	 }
	 else if(counterName == "colaCounter"){
		 if (colaCount > 0){
			 colaCount--;
			 minusTotal(1.99);
			 document.getElementById("colaCounter").innerHTML = "<span>" + colaCount + "</span>";
		 }
	 }
}

/**
 * Checks if one payment method is selected
 *
 * @returns a boolean value that indicates whether a payment method is selected
 */
function checkInput(){
	 var payOnline = document.getElementById("method1").checked; 
	 var payInPerson = document.getElementById("method2").checked;
	 if( payOnline || payInPerson){
		 return true;
	 }else{
		 alert("Please select one payment method"); 
		 return false;
	 }
}

/**
 * Saves the values of all the food counters as well as the value of total cost.
 */
function storeVariables(){
	document.getElementById('burgerCount').value = burgerCount;
	document.getElementById('sandwichCount').value = sandwichCount;
	document.getElementById('friedChickenCount').value = friedChickenCount;
	document.getElementById('colaCount').value = colaCount;
	document.getElementById('totalMoney').value = total.toFixed(2);
}
</script>

<body>
<% String userName = request.getAttribute("userName").toString(); %>
<% session.setAttribute("userName", userName); %>
<% String password = request.getAttribute("password").toString(); %>
<% session.setAttribute("password", password); %>
<% String customerName = request.getAttribute("customerName").toString(); %>
<% session.setAttribute("customerName", customerName); %>
<br>
<h1 id="greeting">Hi, 
<% out.print(customerName);%>
</h1>

<div class="horizontal-container">

<div class="left-panel" align="center">
<h1>Menu</h1>
<p class="item"> Beef Burger &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img style="vertical-align:middle" src="burger.png" alt="Beef Burger" width="70" height="70" />
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $9.99 </p>
<p class="item"> Turkey Sandwich &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img style="vertical-align:middle" src="sandwich.png" alt="Turkey Sandwich" width="90" height="90" /> 
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $8.99 </p>
<p class="item"> Signature Fried Chicken &nbsp; &nbsp;
<img style="vertical-align:middle" src="chicken.png" alt="Signature Fried Chicken" width="90" height="90" />
&nbsp; &nbsp; $12.99 </p>
<p class="item"> Iced Cola &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img style="vertical-align:middle" src="cola.png" alt="Iced cola" width="90" height="90" />
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $1.99 </p>
</div>
	        
<div class="right-panel" id="order" >
<h1 align="center">Order</h1>
<span>Beef Burger &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;</span>
	<button id="burgerMinus" onclick="counterMinusOne('burgerCounter')">-</button> &nbsp;
	<p id="burgerCounter" style="display: inline;"><span>0</span></p>
	&nbsp; <button id="burgerAdd" onclick="counterAddOne('burgerCounter')">+</button>
<br>
<span>Turkey Sandwich &emsp; &emsp; &emsp; &emsp; &emsp; &nbsp;</span>
	<button id="sandwichMinus" onclick="counterMinusOne('sandwichCounter')">-</button> &nbsp;
	<p id="sandwichCounter" style="display: inline;"><span>0</span></p>
	&nbsp; <button id="sandwichAdd" onclick="counterAddOne('sandwichCounter')">+</button>
<br>
<span>Signature Fried Chicken &emsp; &emsp; &emsp; </span>
	<button id="chickenMinus" onclick="counterMinusOne('friedChickenCounter')">-</button> &nbsp;
	<p id="friedChickenCounter" style="display: inline;"><span>0</span></p>
	&nbsp; <button id="chickenAdd" onclick="counterAddOne('friedChickenCounter')">+</button>
<br>
<span>Iced Cola &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; </span>
	<button id="colaMinus" onclick="counterMinusOne('colaCounter')">-</button>&nbsp;
	<p id="colaCounter" style="display: inline;"><span>0</span></p>
	&nbsp;<button id="colaAdd" onclick="counterAddOne('colaCounter')">+</button>
<br>
<br>
<br>
<p id="total"><i>Your total is: </i></p>&emsp;<p class="totalMoney"><i><b>$</b></i></p>
<p id="sum" class="totalMoney"><b><i>0.00</i></b></p>
<br>
<br>

<form action="Controller" method="post" onsubmit="storeVariables(); return checkInput();">
	<h1 align="center">Payment Methods</h1>
    <div id="methods">
      <input type="radio" id="method1" name="method" value="card" />
      <label for="method1">Online by credit cards</label>
      <br>
      <input type="radio" id="method2" name="method" value="cash" />
      <label for="method2">In-person at the counter</label>
      <br>
	  <br>
	  <span id="errorMsg">
	  <%
	  if (request.getAttribute("paymentStatus") == "error"){
	  	  out.print("Sorry there is a problem in the system. Please try again later.");
	  }
	  else{
		  out.print("");
	  }
	  %>	
	  </span> 
      <input type="submit" value="Continue"/>
    </div>
<input type="hidden" name="page" value="menu"/>

<input type="hidden" id="burgerCount" name="burgerCount" value=0/>
<input type="hidden" id="sandwichCount" name="sandwichCount" value=0/>
<input type="hidden" id="friedChickenCount" name="friedChickenCount" value=0/>
<input type="hidden" id="colaCount" name="colaCount" value=0/>
<input type="hidden" id="totalMoney" name="totalMoney" value=0/>

</form>

</div>
</div>

</body>
</html>
