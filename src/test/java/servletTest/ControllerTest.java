package servletTest;

import servlet.ButtonController;
import servlet.PageController;
import servlet.ButtonController.Button;
import servlet.PageController.Page;

import dao.AdminDAO;
import dao.CustomerDAO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
	private PageController controller;
	private CustomerDAO customerDAOMock;
	private AdminDAO adminDAOMock;
	private PageController controllerMock;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private RequestDispatcher RequestDispatcherMock;
	private ButtonController buttonControllerMock;
	
	@BeforeEach
	void setup() throws SQLException {
		controller = new PageController();
		controllerMock = spy(controller);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		session =  mock(HttpSession.class);
		adminDAOMock = mock(AdminDAO.class);
		customerDAOMock = mock(CustomerDAO.class);
		RequestDispatcherMock = mock(RequestDispatcher.class);
		buttonControllerMock = mock(ButtonController.class);
	}
	
	/**
	 * Test the case when page is the welcome page and the user is not registered.
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testWelcomeWhenUserUnregister() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.WELCOME).when(request).getAttribute("page");
		doReturn("Tester39").when(request).getParameter("userName");
		doReturn("test").when(request).getParameter("password");
		when(controllerMock.newCustomerDAO(any())).thenReturn(customerDAOMock);
		when(customerDAOMock.isRegistered()).thenReturn(false);
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Welcome.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).getRequestDispatcher("/Welcome.jsp");
		verify(request, times(1)).setAttribute("loginResult", "unregistered");
	}
	
	/**
	 * Test the case when page is the welcome page and the user is registered but password is incorrect.
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testWelcomeWhenUserIncorrectPwd() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.WELCOME).when(request).getAttribute("page");
		doReturn("Tester39").when(request).getParameter("userName");
		doReturn("test").when(request).getParameter("password");
		when(controllerMock.newCustomerDAO(any())).thenReturn(customerDAOMock);
		when(customerDAOMock.isRegistered()).thenReturn(true);
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Welcome.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);
		doReturn(false).when(customerDAOMock).isPasswordCorrect();
		
		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).getRequestDispatcher("/Welcome.jsp");
		verify(request, times(1)).setAttribute("loginResult", "incorrect");
	}
	
	/**
	 * Test the case when page is the welcome page and the user is registered and password is correct, 
	 * and user is a customer.
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testWelcomeWhenUserLoggedInAsCustomer() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.WELCOME).when(request).getAttribute("page");
		doReturn("Tester3").when(request).getParameter("userName");
		doReturn("test").when(request).getParameter("password");
		when(controllerMock.newCustomerDAO(any())).thenReturn(customerDAOMock);
		when(customerDAOMock.isRegistered()).thenReturn(true);
		doReturn(true).when(customerDAOMock).isPasswordCorrect();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Menu.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);
		doReturn("Beth").when(customerDAOMock).getName();
				
		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).getRequestDispatcher("/Menu.jsp");
		verify(request, times(1)).setAttribute("customerName", "Beth");
		verify(request, times(1)).setAttribute("userName", "Tester3");
		verify(request, times(1)).setAttribute("password", "test");
	}

	/**
	 * Test the case when page is the welcome page and the user is registered and password is correct, 
	 * and user is an admin.
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testWelcomeWhenUserLoggedInAsAdmin() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.WELCOME).when(request).getAttribute("page");
		doReturn("admin").when(request).getParameter("userName");
		doReturn("admin").when(request).getParameter("password");
		when(controllerMock.newCustomerDAO(any())).thenReturn(customerDAOMock);
		when(customerDAOMock.isRegistered()).thenReturn(true);
		doReturn(true).when(customerDAOMock).isPasswordCorrect();
		doReturn(adminDAOMock).when(controllerMock).newAdminDAO();
		doReturn(null).when(adminDAOMock).getOrders();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Admin.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request, times(1)).setAttribute(eq("orders"), any());
		verify(request,times(1)).getRequestDispatcher("/Admin.jsp");
	}
	
	/**
	 * Test the case when page is the Menu page and the user chose to pay by cash
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testMenuWhenUserPayCash() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.MENU).when(request).getAttribute("page");
		doReturn("0").when(request).getParameter("burgerCount");
		doReturn("0").when(request).getParameter("sandwichCount");
		doReturn("0").when(request).getParameter("friedChickenCount");
		doReturn("0").when(request).getParameter("colaCount");
		doReturn("0").when(request).getParameter("totalMoney");
		doReturn("cash").when(request).getParameter("method");
		doReturn(adminDAOMock).when(controllerMock).newAdminDAO();
		doReturn("123456").when(adminDAOMock).getRefNums();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Success.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(session, times(1)).setAttribute("burgerCount", "0");
		verify(session, times(1)).setAttribute("sandwichCount", "0");
		verify(session, times(1)).setAttribute("friedChickenCount", "0");
		verify(session, times(1)).setAttribute("colaCount", "0");
		verify(session, times(1)).setAttribute("totalMoney", "0");
		verify(request, times(1)).setAttribute("refNums", "'123456'");
		verify(request,times(1)).getRequestDispatcher("/Success.jsp");
	}
	
	/**
	 * Test the case when page is the Menu page and the user chose to pay by card
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testMenuWhenUserPayCard() throws SQLException, ServletException, IOException {
		// configure the behaviours
		doReturn(Page.MENU).when(request).getAttribute("page");
		doReturn("0").when(request).getParameter("burgerCount");
		doReturn("0").when(request).getParameter("sandwichCount");
		doReturn("0").when(request).getParameter("friedChickenCount");
		doReturn("0").when(request).getParameter("colaCount");
		doReturn("0").when(request).getParameter("totalMoney");
		doReturn("card").when(request).getParameter("method");
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn("1234569876543256").when(customerDAOMock).getCard1();
		doReturn("2323232326543256").when(customerDAOMock).getCard2();
		doReturn("9898756276543256").when(customerDAOMock).getCard3();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Payment.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(session, times(1)).setAttribute("burgerCount", "0");
		verify(session, times(1)).setAttribute("sandwichCount", "0");
		verify(session, times(1)).setAttribute("friedChickenCount", "0");
		verify(session, times(1)).setAttribute("colaCount", "0");
		verify(session, times(1)).setAttribute("totalMoney", "0");
		verify(request, times(1)).setAttribute("card1", "1234569876543256");
		verify(request, times(1)).setAttribute("card2", "2323232326543256");
		verify(request, times(1)).setAttribute("card3", "9898756276543256");
		verify(request,times(1)).getRequestDispatcher("/Payment.jsp");
	}
	

	/**
	 * Test the case when page is the Payment page and the button clicked is "Pay"
	 * 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testPaymentWhenBtnIsPay() throws ServletException, IOException {
		// configure the behaviours	
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn(Page.PAYMENT).when(request).getAttribute("page");
		doReturn("Pay").when(request).getParameter("btnClicked");
		doReturn(buttonControllerMock).when(controllerMock).newButtonController();
		doNothing().when(buttonControllerMock).buttonDispatcher(request, response, session, customerDAOMock);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request, times(1)).setAttribute("Button", Button.PAY);
	}

	/**
	 * Test the case when page is the Payment page and the button clicked is "Delete"
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testPaymentWhenBtnIsDelete() throws SQLException, ServletException, IOException {
		// configure the behaviours	
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn(Page.PAYMENT).when(request).getAttribute("page");
		doReturn("Delete").when(request).getParameter("btnClicked");
		doReturn(buttonControllerMock).when(controllerMock).newButtonController();
		doNothing().when(buttonControllerMock).buttonDispatcher(request, response, session, customerDAOMock);
		doReturn("1234569876543256").when(customerDAOMock).getCard1();
		doReturn("2323232326543256").when(customerDAOMock).getCard2();
		doReturn("").when(customerDAOMock).getCard3();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Payment.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);
		
		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request, times(1)).setAttribute("Button", Button.DELETE_CARD);
		verify(request, times(1)).setAttribute("card1", "1234569876543256");
		verify(request, times(1)).setAttribute("card2", "2323232326543256");
		verify(request, times(1)).setAttribute("card3", "");
	}

	/**
	 * Test the case when page is the Payment page and the button clicked is "Save"
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testPaymentWhenBtnIsSave() throws SQLException, ServletException, IOException {
		// configure the behaviours	
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn(Page.PAYMENT).when(request).getAttribute("page");
		doReturn("Save").when(request).getParameter("btnClicked");
		doReturn(buttonControllerMock).when(controllerMock).newButtonController();
		doNothing().when(buttonControllerMock).buttonDispatcher(request, response, session, customerDAOMock);
		doReturn("1234569876543256").when(customerDAOMock).getCard1();
		doReturn("2323232326543256").when(customerDAOMock).getCard2();
		doReturn("9123289756543256").when(customerDAOMock).getCard3();
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Payment.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);
		
		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request, times(1)).setAttribute("Button", Button.SAVE_CARD);
		verify(request, times(1)).setAttribute("card1", "1234569876543256");
		verify(request, times(1)).setAttribute("card2", "2323232326543256");
		verify(request, times(1)).setAttribute("card3", "9123289756543256");
	}


	/**
	 * Test the case when page is the Success page
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testSuccess() throws SQLException, ServletException, IOException {
		// configure the behaviours	
		doReturn("tester4").when(session).getAttribute("userName");
		doReturn("test").when(session).getAttribute("password");
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn(Page.SUCCESS).when(request).getAttribute("page");
		doReturn("123456").when(request).getParameter("referenceNum");
		doReturn("Louis").when(session).getAttribute("customerName");
		doReturn("0").when(session).getAttribute("burgerCount");
		doReturn("0").when(session).getAttribute("sandwichCount");
		doReturn("0").when(session).getAttribute("friedChickenCount");
		doReturn("0").when(session).getAttribute("colaCount");
		doReturn("0").when(session).getAttribute("totalMoney");
		doReturn(true).when(customerDAOMock).saveOrder("Louis", "0", "0", "0", "0", "0", "123456");
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Welcome.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);
		
		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).getRequestDispatcher("/Welcome.jsp");
	}

	/**
	 * Test the case when page is the Admin page and the button clicked is "Log Out"
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testAdminWhenBtnIsLogOut() throws ServletException, IOException {
		// configure the behaviours	
		doReturn(customerDAOMock).when(controllerMock).newCustomerDAO(any());
		doReturn(Page.ADMIN).when(request).getAttribute("page");
		doReturn("Log Out").when(request).getParameter("btnClicked");
		doReturn(RequestDispatcherMock).when(request).getRequestDispatcher("/Welcome.jsp");
		doNothing().when(RequestDispatcherMock).forward(request, response);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).getRequestDispatcher("/Welcome.jsp");
	}

	/**
	 * Test the case when page is the Admin page and the button clicked is "Log Out"
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Test
	void testAdminWhenBtnIsDelete() throws SQLException, ServletException, IOException {
		// configure the behaviours	
		doReturn(Page.ADMIN).when(request).getAttribute("page");
		doReturn("Delete").when(request).getParameter("btnClicked");
		doReturn(buttonControllerMock).when(controllerMock).newButtonController();
		doNothing().when(buttonControllerMock).buttonDispatcher(request, response, session, null);

		// invoke the method
		controllerMock.pageDispatcher(request, response, session);
		
		// verify interactions
		verify(request,times(1)).setAttribute("Button", Button.DELETE_ORDER);
	}
}








