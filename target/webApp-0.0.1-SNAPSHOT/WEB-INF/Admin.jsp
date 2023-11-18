<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>

<style>

body::after {
	content: "";
	background: url('cafeteria.jpg');
	background-repeat: no-repeat;
	background-size: 100% 100%;
	opacity: 0.5;
	top: 0; left: 0; bottom: 0; right: 0;
	position: fixed;
	z-index: -2;
}

#backBoard{
	margin-right: 40px;
	margin-left: 40px;
	margin-bottom: 40px;
	margin-top: 50px;
	padding-top: 10px;
	padding-bottom: 30px;
	padding-left: 10px;
	padding-right:10px;
	border: 2px solid white;
	border-radius: 10px;
	background-color: rgba(255,255,255, 0.8);
	z-index: -1;
	
	min-height: 250px;
	
	text-align:center;
}
	         
div.orders {
	margin-right: 20px;
	margin-left: 20px;
	margin-bottom: 20px;
	margin-top: 20px;
	padding-top: 10px;
	padding-bottom: 30px;
	padding-left: 50px;
	padding-right: 10px;
	width: 25% ; 
	height: 330px;
	
	display: inline-block;
	vertical-align: bottom;
	
	background: url('note.png');
	background-repeat: no-repeat;
	background-size: 100% 100%;
	
	text-align: left;
	font-size: 18px; 
}

p.description {
	font-weight: bold;
}

span{
	color: blue;
}

</style>

<script>

/**
 * Check the status of deleting an order. Alert the user if deleting is failed.
 */
function checkStatus(){
	 var orders = <%= request.getAttribute("orders") %>;
	var status = <%= request.getAttribute("deleteStatus") %>;
	if(status == "error"){
		alert("There is a problem in the system. Please try delete again later.");
	}
	alert("adadda"+ orders);
}

/**
 * Display the information of each order on the page.
 */
function displayOrders(){
	// Gets the information of orders from the servlet.
	var orders = <%= request.getAttribute("orders") %>;
	var ordersList = parseOrders(orders);
	var ordersCount = ordersList.length;
	
	// for each order create a div
	for(let i=0; i<ordersCount; i++){
		var orderNote = document.createElement('div');
		orderNote.setAttribute('class',"orders");
		var orderInfo = ordersList[i];
		
		// extract the info
		var customerName = orderInfo[0];
		var burgerCount = orderInfo[1];
		var sandwichCount = orderInfo[2];
		var chickenCount = orderInfo[3];
		var colaCount = orderInfo[4];
		var totalMoney = orderInfo[5];
		var refNum = orderInfo[6];
		 
		// set the HTML code to display the order info
		orderNote.innerHTML = 
			"<h2>&emsp; &emsp; &nbsp; &nbsp;#" + refNum + "</h2>" +
			"<p class='description'>Customer Name: <span>" + customerName + "</span></p>" + 
			"<p class='description'># of burgers ordered: <span>" + burgerCount + "</span></p>" + 
			"<p class='description'># of fried chickens ordered: <span>" + chickenCount + "</span></p>" + 
			"<p class='description'># of sandwich ordered: <span>" + sandwichCount + "</span></p>" + 
			"<p class='description'># of cola ordered: <span>" + colaCount + "</span></p>" + 
			"<p class='description'>Total cost: <span>$" + totalMoney + "</span></p>" +
			"<form action='Controller' method='post'>" + 
			"<input type='hidden' name='refNum' value='" + refNum + "'/>" +
			"<input type='hidden' name='page' value='admin'/>" +
			"<input type='submit' name='btnClicked' value='Delete'/>" + 
			"</form>"; 
			
		 document.getElementById('backBoard').appendChild(orderNote);
	}
	
}

/**
 * A helper function that breaks the long orders String:
 * "order1;order2...;order3" 
 * into lists of orders:
 * [order1, order2, ..., order3]
 * And each order will be a list of information variables that like this:
 * [<customerName>, <burgerCount>, ..., <refNum>]
 *
 * @param  The raw long string that contains the information of orders.
 */
function parseOrders(orders){
	var ordersSeperated = orders.split(";");
	// Due to the way the raw string is formatted, there is an empty order at the end of the list after the split.
	ordersSeperated.pop();
	var ordersCount = ordersSeperated.length;
	for(let i =0; i<ordersCount; i++){
		ordersSeperated[i] = ordersSeperated[i].split(",");
	}
	return ordersSeperated;
}

</script>

<body onload="checkStatus(); displayOrders()">

<div id="backBoard">
<h1>Archive</h1>
<form action="Controller" method="post"> 
<input type="hidden" name="page" value="admin"/>
<input type="submit" name='btnClicked' value="Log Out" />
</form>

</div>


</body>

</html>