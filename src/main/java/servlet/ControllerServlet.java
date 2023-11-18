package servlet;

/**
 * The servlet acts as a medium between the front-end and the back-end databases. It retrieves values 
 * from the front-end, and send them to the CustomerDAO class. Then, the servlet will receive back the 
 * processed results from the CustomerDAO class and forward the results to the front-end.
 * 
 * @author Andrew Tian-en Li
 */

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tinylog.Logger;

import model.Customer;
import dao.CustomerDAO;
import dao.AdminDAO;
import servlet.PageController;
import servlet.PageController.Page;

@WebServlet("/Controller")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
    }

	/**
	* Directs request to doPost().
	*
	* @param request A HttpServletRequest object that provides request information for HTTP servlets
	* @param response A HttpServletResponse object that help in sending a response to a servlet.
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	* determine the previous page the user was at and direct the request values to PageController to process the values.
	*
	* @param request A HttpServletRequest object that provides request information for HTTP servlets
	* @param response A HttpServletResponse object that help in sending a response to a servlet.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.info("doPost Method for the Controller Servlet");
		HttpSession session = request.getSession();
		String page = request.getParameter("page");
		// determine what is the previous page and perform corresponding operations.
		switch(page) {
			case "login": 
				PageController login = new PageController();
				request.setAttribute("page", Page.WELCOME);
				login.pageDispatcher(request, response, session);
				break;
				
			case "menu": 
				PageController menu = new PageController();
				request.setAttribute("page", Page.MENU);
				menu.pageDispatcher(request, response, session);
				break;
				
			case "payment": 
				PageController payment = new PageController();
				request.setAttribute("page", Page.PAYMENT);
				payment.pageDispatcher(request, response, session);
				break;
				
			case "success": 
				PageController success = new PageController();
				request.setAttribute("page", Page.SUCCESS);
				success.pageDispatcher(request, response, session);
				break;
				
			case "admin":
				PageController admin = new PageController();
				request.setAttribute("page", Page.ADMIN);
				admin.pageDispatcher(request, response, session);
				break;
			
			default:
				request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
		}
	}

}