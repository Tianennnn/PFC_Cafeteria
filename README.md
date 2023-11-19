# PFC Cafeteria
A simple food ordering system that allows users to place food orders and view all history orders. This is a dynamic web application built using Servlet, JSP, JavaScript and CSS. 

### Welcome Page
<img src="https://github.com/Tianennnn/PFC_Cafeteria/blob/main/Screenshots/Welcome%20Page.png" width="550" height="300">
This page allows users to log in using a username and password. When the username and password combination matches a record in the database, the user will be logged in successfully,
otherwise, a line of message will be displayed to inform the user that the username and password combination is invalid.

### Menu Page
<img src="https://github.com/Tianennnn/PFC_Cafeteria/blob/main/Screenshots/Menu%20Page.png" width="550" height="300">
Once logged in, users will be directed to the Menu page, where users can look at the menu on the left for a list of available foods and place orders on the right. Users can select
the count for each of the items they want and the total price of the order will be calculated in real-time. Before users continue to the payment page, they can also choose to pay by cash
or pay by cards.

### Payment Page
<img src="https://github.com/Tianennnn/PFC_Cafeteria/blob/main/Screenshots/Payment%20Page.png" width="550" height="300">
If a user chooses to pay by cards, they will be directed to the Card Payment Page. The page will display all the payment cards that the user has saved previously if any, and the user can just select
one of the saved payment cards to pay. The user can also type the card information of a new payment card and pay with the new card or save the card. 
<br><br>
(For this application, the payment system is fake.
A payment card number can be any combination of random 16 digits. No actual fees will be charged even if a user inputs a real payment card number. Users are not recommended to input real payment card numbers.)

### Success Page
<img src="https://github.com/Tianennnn/PFC_Cafeteria/blob/main/Screenshots/Success%20Page.png" width="550" height="300">
This page confirms to the user that the order has been successfully placed. The user is now able to see a reference number for that order and log out from here.

### Admin Page
<img src="https://github.com/Tianennnn/PFC_Cafeteria/blob/main/Screenshots/Admin%20Page.png" width="550" height="300">
When a user is logged in as an administrator using the special combination of username and password in the Welcome Page (by default, the username is "admin" and the password is also "admin"), 
the user will be directed to the Admin page, where the user can view all the orders that have been placed by different customers. For each order, the user as an administrator can see the order's reference number, 
the name of the customer, the items that the customer ordered, and lastly, the total cost of the order. The user can also click on the "delete" button for each order to delete the order from the
database.
