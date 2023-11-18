package servlet;

/**
 * A helper class to perform operations based on the passed-in values and the button the user clicked.
 * 
 * @author Andrew Tian-en Li
 */

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.tinylog.Logger;

import dao.CustomerDAO;
import dao.AdminDAO;

public class ButtonController {
	public enum Button{
		PAY, DELETE_CARD, DELETE_ORDER, SAVE_CARD
	}
	
	// constant variables
	final String REFERENCE_NUMBER = "refNum";
	final String REFERENCE_NUMBER_LIST = "refNums";
	final String DELETE_STATUS = "deleteStatus";
	final String ERROR = "error";
	
	/**
	* performs operations based on the the button the user clicked
	*
	* @param request A HttpServletRequest object that provides request information for HTTP servlets
	* @param response A HttpServletResponse object that help in sending a response to a servlet.
	* @param session A HttpSession object that stores the information about the customer across multiple pages.
	* @param customerDAO A CustomerDAO object that used to modify the information of an customer.
	*/
	public void buttonDispatcher(HttpServletRequest request, HttpServletResponse response, HttpSession session, CustomerDAO customerDAO) throws ServletException, IOException {
		Logger.info("entered ButtonController");
		String refNums = "";
		String refNum = "";
		String cardIndexDelete = "";
		String NumToSave = "";
		String saveResult = "";
		AdminDAO admin;
		
		Button button = (Button) request.getAttribute("Button");
		
		switch (button) {
			case PAY:
				// send reference numbers to Success page
				admin = newAdminDAO();
				refNums = "'" + admin.getRefNums() + "'";
				request.setAttribute(REFERENCE_NUMBER_LIST, refNums);
				request.getRequestDispatcher("/Success.jsp").forward(request, response);
				break;
				
			case DELETE_CARD:
				// delete the saved card information
				cardIndexDelete = request.getParameter("card");
				boolean deleteStatus= customerDAO.deleteCard(cardIndexDelete);
				if(!deleteStatus) {
					request.setAttribute(DELETE_STATUS, ERROR);
				}
				break;
				
			case SAVE_CARD:
				// save the card information
				NumToSave = request.getParameter("newNum");
				saveResult = customerDAO.saveCard(NumToSave);
				request.setAttribute("saveResult", saveResult);
				break;
			
			case DELETE_ORDER:
				refNum = (String) request.getParameter(REFERENCE_NUMBER);
				admin = newAdminDAO();
				boolean deleteResult = admin.deleteOrder(refNum);
				// get back to the Admin page after deletion
				if(!deleteResult) {
					request.setAttribute(DELETE_STATUS, ERROR);
					request.getRequestDispatcher("/Admin.jsp").forward(request, response);
				}
				else {
					request.setAttribute(DELETE_STATUS, refNum);
					// send the updated Orders list to the Admin page
					try{
						JSONArray ordersJSON = admin.getOrders();
						request.setAttribute("orders", ordersJSON);
						request.getRequestDispatcher("/Admin.jsp").forward(request, response);
					}
					catch(SQLException e) {
						request.setAttribute(DELETE_STATUS, ERROR);
						request.getRequestDispatcher("/Admin.jsp").forward(request, response);
					}
				}
				break;
		}
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
}