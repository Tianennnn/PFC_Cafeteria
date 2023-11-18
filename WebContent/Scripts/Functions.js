<%@ page contentType="text/javascript" %>
/**
* First checks if the input username or password is blank. If so, alert the user. 
* Then, checks if the inputs contain any special characters that could compose SQL injection attacks. 
*/
const invalidCharacters = ["\'", "--", "#", "%", ";", "+", "||", "=", ">", "<", "!"];

function validate(){ 
	 var result = false;
	 // checks if the inputs are blank
	 var username = document.getElementById("username").value; 
	 var password = document.getElementById("password").value;
	 if (username == null || username==""){ 
	 	 alert("Username cannot be blank"); 
	 }
	 else if(password==null || password==""){ 
	 	 alert("Password cannot be blank"); 
	 } 
	 else{
		 // Inputs are not blank
		 result = true;
		 // for each invalid character, checks if the inputs contain the character
		 for (character of invalidCharacters){
			 if (username.includes(character)){
				 result = false;
				 alert("Username contains the invalid character: \"" + character + "\".");
				 break;
			 }
			 if (password.includes(character)){
				 result = false;
				 alert("Password contains the invalid character: \"" + character + "\"");
				 break;
			 }
		 }
	 }
	 return result;
}

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
		 document.getElementById("burgerCounter").innerHTML = "<span class='Menu'>" + burgerCount + "</span>";
	 }
	 else if(counterName == "sandwichCounter"){
		 sandwichCount++;
		 addTotal(8.99);
		 document.getElementById("sandwichCounter").innerHTML = "<span class='Menu'>" + sandwichCount + "</span>";
	 }
	 else if(counterName == "friedChickenCounter"){
		 friedChickenCount++;
		 addTotal(12.99);
		 document.getElementById("friedChickenCounter").innerHTML = "<span class='Menu'>" + friedChickenCount + "</span>";
	 }
	 else if(counterName == "colaCounter"){
		 colaCount++;
		 addTotal(1.99);
		 document.getElementById("colaCounter").innerHTML = "<span class='Menu'>" + colaCount + "</span>";
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
			 document.getElementById("burgerCounter").innerHTML = "<span class='Menu'>" + burgerCount + "</span>";
		 }
	 }
	 else if(counterName == "sandwichCounter"){
		 if (sandwichCount > 0){
			 sandwichCount--;
			 minusTotal(8.99);
			 document.getElementById("sandwichCounter").innerHTML = "<span class='Menu'>" + sandwichCount + "</span>";
		 }
	 }
	 else if(counterName == "friedChickenCounter"){
		 if (friedChickenCount > 0){
			 friedChickenCount--;
			 minusTotal(12.99);
			 document.getElementById("friedChickenCounter").innerHTML = "<span class='Menu'>" + friedChickenCount + "</span>";
		 }
	 }
	 else if(counterName == "colaCounter"){
		 if (colaCount > 0){
			 colaCount--;
			 minusTotal(1.99);
			 document.getElementById("colaCounter").innerHTML = "<span class='Menu'>" + colaCount + "</span>";
		 }
	 }
}

/**
 * Checks if one payment method is selected
 *
 * @returns a boolean value that indicates whether a payment method is selected
 */
function checkMethod(){
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



