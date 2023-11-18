<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>

<head>
<link rel="stylesheet" href="StyleSheet/Styles.css">
<script type="text/javascript" src="Scripts/Functions.js"></script>
</head>

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
}


/**
 * Display the information of each order on the page.
 */
function displayOrders(){
	// get the information of orders from the servlet
	var orders = <%= request.getAttribute("orders") %>;
	
	// check if there are any orders, if so, prompt the user that there are no orders.
	if (orders[0]==null){
		var emptyMsg = document.createElement('div');
		emptyMsg.innerHTML = "<br><p id='emptyMsg'> There are currently no orders to display.</p>";
		document.getElementById('backBoard').appendChild(emptyMsg);
	}
	else{
		let i = 0;
		while (orders[i]!=null){
			var orderNote = document.createElement('div');
			orderNote.setAttribute('class',"orders");
			var order = orders[i];
			
			// set the HTML code to display the order info
			orderNote.innerHTML = 
				"<h2 class='orderHead'>&emsp; &emsp; &nbsp; &nbsp;#" + order.refNum + "</h2>" +
				"<p class='description'>Customer Name: <span class='Admin'>" + order.customerName + "</span></p>" + 
				"<p class='description'># of burgers ordered: <span class='Admin'>" + order.burgerCount + "</span></p>" + 
				"<p class='description'># of fried chickens ordered: <span class='Admin'>" + order.chickenCount + "</span></p>" + 
				"<p class='description'># of sandwich ordered: <span class='Admin'>" + order.sandwichCount + "</span></p>" + 
				"<p class='description'># of cola ordered: <span class='Admin'>" + order.colaCount + "</span></p>" + 
				"<p class='description'>Total cost: <span class='Admin'>$" + order.totalMoney + "</span></p>" +
				"<form action='Controller' method='post'>" + 
				"<input type='hidden' name='refNum' value=" + order.refNum + " />" +
				"<input type='hidden' name='page' value='admin'/>" +
				"<input type='submit' name='btnClicked' value='Delete'/>" + 
				"</form>"; 
				
			 document.getElementById('backBoard').appendChild(orderNote);
			
			i++;
		}
	}
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