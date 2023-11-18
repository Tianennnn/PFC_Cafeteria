<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
</head>

<head>
<link rel="stylesheet" href="StyleSheet/Styles.css">
<script type="text/javascript" src="Scripts/Functions.js"></script>
</head>


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
<img style="vertical-align:middle" src="Images/burger.png" alt="Beef Burger" width="70" height="70" />
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $9.99 </p>
<p class="item"> Turkey Sandwich &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img style="vertical-align:middle" src="Images/sandwich.png" alt="Turkey Sandwich" width="90" height="90" /> 
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $8.99 </p>
<p class="item"> Signature Fried Chicken &nbsp; &nbsp;
<img style="vertical-align:middle" src="Images/chicken.png" alt="Signature Fried Chicken" width="90" height="90" />
&nbsp; &nbsp; $12.99 </p>
<p class="item"> Iced Cola &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<img style="vertical-align:middle" src="Images/cola.png" alt="Iced cola" width="90" height="90" />
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; $1.99 </p>
</div>
	        
<div class="right-panel" id="order" >
<h1 align="center">Order</h1>
<span class="Menu">Beef Burger &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp;</span>
	<button id="burgerMinus" onclick="counterMinusOne('burgerCounter')">-</button> &nbsp;
	<p id="burgerCounter" style="display: inline;"><span class="Menu">0</span></p>
	&nbsp; <button id="burgerAdd" onclick="counterAddOne('burgerCounter')">+</button>
<br>
<span class="Menu">Turkey Sandwich &emsp; &emsp; &emsp; &emsp; &emsp; &nbsp;</span>
	<button id="sandwichMinus" onclick="counterMinusOne('sandwichCounter')">-</button> &nbsp;
	<p id="sandwichCounter" style="display: inline;"><span class="Menu">0</span></p>
	&nbsp; <button id="sandwichAdd" onclick="counterAddOne('sandwichCounter')">+</button>
<br>
<span class="Menu">Signature Fried Chicken &emsp; &emsp; &emsp; </span>
	<button id="chickenMinus" onclick="counterMinusOne('friedChickenCounter')">-</button> &nbsp;
	<p id="friedChickenCounter" style="display: inline;"><span class="Menu">0</span></p>
	&nbsp; <button id="chickenAdd" onclick="counterAddOne('friedChickenCounter')">+</button>
<br>
<span class="Menu">Iced Cola &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; </span>
	<button id="colaMinus" onclick="counterMinusOne('colaCounter')">-</button>&nbsp;
	<p id="colaCounter" style="display: inline;"><span class="Menu">0</span></p>
	&nbsp;<button id="colaAdd" onclick="counterAddOne('colaCounter')">+</button>
<br>
<br>
<br>
<p id="total"><i>Your total is: </i></p>&emsp;<p class="totalMoney"><i><b>$</b></i></p>
<p id="sum" class="totalMoney"><b><i>0.00</i></b></p>
<br>
<br>

<form action="Controller" method="post" onsubmit="storeVariables(); return checkMethod();">
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
