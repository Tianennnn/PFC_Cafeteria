package servlet;

/**
 * A helper function to perform operations and dispatch users to different pages based on the current page and parameters.
 * 
 * @author Andrew Tian-en Li
 */


import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.tinylog.Logger;

import model.Customer;
import servlet.ButtonController.Button;
import dao.CustomerDAO;
import dao.AdminDAO;

public class PageController {
	
	public enum Page{
		WELCOME, MENU, PAYMENT, SUCCESS, ADMIN
	}
	
	// constant variables
	final String USERNAME = "userName";
	final String PASSWORD = "password";
	final String LOGIN_RESULT = "loginResult";
	final String ERROR = "error";
	final String CUSTOMER_NAME = "customerName";
	final String BUTTON_CLICKED = "btnClicked";
	final String REFERENCE_NUMBER_LIST = "refNums";
	final String UNREGISTERED = "unregistered";
	final String INCORRECT = "incorrect";
	final String BURGER_COUNT = "burgerCount";
	final String SANDWICH_COUNT = "sandwichCount";
	final String CHICKEN_COUNT = "friedChickenCount";
	final String COLA_COUNT = "colaCount";
	final String TOTAL_MONEY = "totalMoney";
	final String PAYMENT_STATUS = "paymentStatus";
	
	
	/**
	* performs operations based on the previous page the user was at and dispatch the user to different pages depending
	* on the result of the data processed.
	* @param request A HttpServletRequest object that provides request information for HTTP servlets
	* @param response A HttpServletResponse object that help in sending a response to a servlet.
	* @param session A HttpSession object that stores the information about the customer across multiple pages.
	*/
	public void pageDispatcher (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		Logger.info("entered PageController");
		
		// declare and initialize variables
		String btnClicked = "";
		String refNum = "";
		String refNums = "";
		String customerName = "";
		String burgerCount = "";
		String sandwichCount = "";
		String friedChickenCount = "";
		String colaCount = "";
		String totalMoney = "";
		
		Page page = (Page) request.getAttribute("page");
		String userName = (String) session.getAttribute(USERNAME);
		String password = (String) session.getAttribute(PASSWORD);
		Customer customer = newCustomer(userName, password);
		CustomerDAO customerDAO = newCustomerDAO(customer);
		
		switch(page) {
			case WELCOME:
				Logger.info("case WELCOME");
				userName = request.getParameter(USERNAME);
				password = request.getParameter(PASSWORD);
				customer =  newCustomer(userName, password);
				customerDAO = newCustomerDAO(customer);
				// First, check if the user is registered as well as if the password matches the username.
				boolean validLogin  = isLoginValid(request, response, customerDAO);
				if(validLogin){
					try {
						// Then, check if the user is an admin
						if(userName.equals("admin")) {
							AdminDAO admin = newAdminDAO();
							JSONArray ordersJSON = admin.getOrders();
							request.setAttribute("orders", ordersJSON);
							request.getRequestDispatcher("/Admin.jsp").forward(request, response);
						}
						else {			// the user is a customer
							// retrieve the customer's first name and proceed to Menu page
							customerName = "";
							customerName = customerDAO.getName();
							request.setAttribute(CUSTOMER_NAME, customerName);
							request.setAttribute(PASSWORD, password);
							request.setAttribute(USERNAME, userName);
							request.getRequestDispatcher("/Menu.jsp").forward(request, response);
						}
					}
					catch(SQLException e) {
						request.setAttribute(LOGIN_RESULT, ERROR);
						request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
					}
				}
				break;
				
			case MENU:
				// saves the orders information to the session
				burgerCount = (String) request.getParameter(BURGER_COUNT);
				session.setAttribute(BURGER_COUNT, burgerCount);
				sandwichCount = (String) request.getParameter(SANDWICH_COUNT);
				session.setAttribute(SANDWICH_COUNT, sandwichCount);
				friedChickenCount = (String) request.getParameter(CHICKEN_COUNT);
				session.setAttribute(CHICKEN_COUNT, friedChickenCount);
				colaCount = (String) request.getParameter(COLA_COUNT);
				session.setAttribute(COLA_COUNT, colaCount);
				totalMoney = (String) request.getParameter(TOTAL_MONEY);
				session.setAttribute(TOTAL_MONEY, totalMoney);
				
				String payment = request.getParameter("method");
				// determine what is the user's payment method and perform corresponding operations.
				if(payment.equals("cash")) {
					AdminDAO admin = newAdminDAO();
					refNums = "'" + admin.getRefNums() + "'";
					request.setAttribute(REFERENCE_NUMBER_LIST, refNums);
					request.getRequestDispatcher("/Success.jsp").forward(request, response);
				}
				else if (payment.equals("card")){  
					String card1 = "", card2 = "", card3 = "";
					try {
						card1 = customerDAO.getCard1();
						card2 = customerDAO.getCard2();
						card3 = customerDAO.getCard3();
					}
					catch(SQLException e) {
						// if there are errors with database connection, return to Menu page
						request.setAttribute(PAYMENT_STATUS, ERROR);
						request.getRequestDispatcher("/Menu.jsp").forward(request, response);
					}
					request.setAttribute("card1", card1);
					request.setAttribute("card2", card2);
					request.setAttribute("card3", card3);
					request.getRequestDispatcher("/Payment.jsp").forward(request, response);
				}
				break;
				
			case PAYMENT:
				btnClicked = request.getParameter(BUTTON_CLICKED);
				
				switch(btnClicked) {
				case "Pay":
					// send reference numbers to Success page.
					ButtonController pay = newButtonController();
					request.setAttribute("Button", Button.PAY);
					pay.buttonDispatcher(request, response, session, customerDAO);
					return;
					// break;
					
				case "Delete":
					// delete the saved card information
					ButtonController deleteCard = newButtonController();
					request.setAttribute("Button", Button.DELETE_CARD);
					deleteCard.buttonDispatcher(request, response, session, customerDAO);
					break;
				
				case "Save":
					// save the card information
					ButtonController saveCard = newButtonController();
					request.setAttribute("Button", Button.SAVE_CARD);
					saveCard.buttonDispatcher(request, response, session, customerDAO);
				}
				
				// send the updated saved cards information to Payment page
				String card1 = "", card2 = "", card3 = "";
				try {
					card1 = customerDAO.getCard1();
					card2 = customerDAO.getCard2();
					card3 = customerDAO.getCard3();
				}
				catch(SQLException e) {
					// if there are errors with database connection, return to Payment page
					request.setAttribute(PAYMENT_STATUS, ERROR);
					request.getRequestDispatcher("/Payment.jsp").forward(request, response);
				}
				request.setAttribute("card1", card1);
				request.setAttribute("card2", card2);
				request.setAttribute("card3", card3);
				request.getRequestDispatcher("/Payment.jsp").forward(request, response);
				break;
				
			case SUCCESS:
				refNum = (String) request.getParameter("referenceNum");
				
				customerName = (String) session.getAttribute(CUSTOMER_NAME);
				burgerCount = (String) session.getAttribute(BURGER_COUNT);
				sandwichCount = (String) session.getAttribute(SANDWICH_COUNT);
				friedChickenCount = (String) session.getAttribute(CHICKEN_COUNT);
				colaCount = (String) session.getAttribute(COLA_COUNT);
				totalMoney = (String) session.getAttribute(TOTAL_MONEY);
				boolean saveResult = customerDAO.saveOrder(customerName, burgerCount, sandwichCount,
									friedChickenCount, colaCount, totalMoney, refNum);
				if(!saveResult) {
					// if there are errors with database connection, remain in Success page
					request.setAttribute(PAYMENT_STATUS, ERROR);
					request.getRequestDispatcher("/Success.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
				}
				break;
				
			case ADMIN:
				btnClicked = request.getParameter(BUTTON_CLICKED);
				if (btnClicked.equals("Log Out")) {
					request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
				}
				else if(btnClicked.equals("Delete")) {
					// delete the order
					ButtonController deleteOrder = newButtonController();
					request.setAttribute("Button", Button.DELETE_ORDER);
					deleteOrder.buttonDispatcher(request, response, session, null);
				}
				break;
		}
	}
	
	/**
	* A helper function to check if the user is registered as well as if the password matches the username.
	* @param request A HttpServletRequest object that provides request information for HTTP servlets
	* @param response A HttpServletResponse object that help in sending a response to a servlet.
	* @param customerDAO A CustomerDAO object that accesses the information of customers from database.
	*/
	public boolean isLoginValid(HttpServletRequest request, HttpServletResponse response, CustomerDAO customerDAO) throws ServletException, IOException {
		boolean loginResult = false;
		// First check if the user is registered
		// if not, take user back to the Welcome page with the proper error message
		boolean isRegistered = false;
		try {
			isRegistered = customerDAO.isRegistered();
		}
		catch(SQLException e) {
			request.setAttribute(LOGIN_RESULT, ERROR);
			request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
		}
		if(!isRegistered) {
			request.setAttribute(LOGIN_RESULT, UNREGISTERED);
			request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
		}
		else {
			// i.e. isRegistered == true;
			// then determine if password is correct for the user
			boolean is_pwd_correct = false;
			try {
				is_pwd_correct = customerDAO.isPasswordCorrect();
			}
			catch(SQLException e) {
				request.setAttribute(LOGIN_RESULT, ERROR);
				request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
			}
			if(!is_pwd_correct) {
				request.setAttribute(LOGIN_RESULT, INCORRECT);
				request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
			}
			else {
				loginResult = true;
			}
		}
		return loginResult;
	}
	
	/**
	* A helper function to create a new CustomerDAO object (for testing purpose to improve testability)
	* @param request a HttpServletRequest object that provides request information for HTTP servlets
	* @param response a HttpServletResponse object that help in sending a response to a servlet.
	* 
	* @return CustomerDAO A newly created CustomerDAO object.
	*/
	public CustomerDAO newCustomerDAO(Customer customer){
		CustomerDAO customerDAO = new CustomerDAO(customer);
		return customerDAO;
	}
	
	/**
	* A helper function to create a new Customer object (for testing purpose to improve testability)
	* @param request a HttpServletRequest object that provides request information for HTTP servlets
	* @param response a HttpServletResponse object that help in sending a response to a servlet.
	* 
	* @return Customer A newly created Customer object.
	*/
	public Customer newCustomer(String username, String pwd) {
		Customer customer = new Customer(username, pwd);
		return customer;
	}
	
	/**
	* A helper function to create a new AdminDAO object (for testing purpose to improve testability)
	* @param request a HttpServletRequest object that provides request information for HTTP servlets
	* @param response a HttpServletResponse object that help in sending a response to a servlet.
	* 
	* @return AdminDAO A newly created AdminDAO object.
	*/
	public AdminDAO newAdminDAO() {
		AdminDAO admin = new AdminDAO();
		return admin;
	}

	/**
	* A helper function to create a new ButtonController (for testing purpose to improve testability)
	* @param request a HttpServletRequest object that provides request information for HTTP servlets
	* @param response a HttpServletResponse object that help in sending a response to a servlet.
	* 
	* @return ButtonController A newly created ButtonController class.
	*/
	public ButtonController newButtonController(){
		ButtonController button = new ButtonController();
		return button;
	}
}
