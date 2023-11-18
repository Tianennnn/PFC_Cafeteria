package daoTest;

import model.Customer;
import dao.CustomerDAO;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;

/**
 * A class to test the CustomerDAO class.
 * 
 * @author Andrew Li
 */

@ExtendWith(MockitoExtension.class)
public class CustomerDAOTest {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rset;
	private CustomerDAO customerDAOMock;
	private Customer customer;
	
	@BeforeEach
	void setup() throws SQLException {
		customer = mock(Customer.class);
		CustomerDAO customerDAO = new CustomerDAO(customer);
		customerDAOMock = spy(customerDAO);
		conn = mock(Connection.class);
		pstmt = mock(PreparedStatement.class);
		doReturn(conn).when(customerDAOMock).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(pstmt);
		rset = mock(ResultSet.class);
	}
	
	/**
	 * Test the isRegistered method when the user is registered
	 * 
	 * @throws SQLException
	 */
	@Test
	void testIsRegistered() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester2");
		when(rset.next()).thenReturn(true).thenReturn(false);
		when(pstmt.executeQuery()).thenReturn(rset);
		boolean result = customerDAOMock.isRegistered();
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "tester2");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the isRegistered method when the user is not registered
	 * 
	 * @throws SQLException
	 */
	@Test
	void testIsNotRegistered() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester8");
		when(rset.next()).thenReturn(false);
		when(pstmt.executeQuery()).thenReturn(rset);
		boolean result = customerDAOMock.isRegistered();
		assertFalse(result);
		verify(pstmt, times(1)).setString(1, "tester8");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the isPasswordCorrect method when the input password is correct
	 * 
	 * @throws SQLException
	 */
	@Test
	void testIsPasswordCorrect() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester2");
		when(customerDAOMock.customer.getPassword()).thenReturn("test");
		when(rset.next()).thenReturn(true);
		when(rset.getString("Password")).thenReturn("test");
		when(pstmt.executeQuery()).thenReturn(rset);
		boolean result = customerDAOMock.isPasswordCorrect();
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "tester2");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the isPasswordCorrect method when the input password is correct
	 * 
	 * @throws SQLException
	 */
	@Test
	void testIsPasswordNotCorrect() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester2");
		when(customerDAOMock.customer.getPassword()).thenReturn("123456");
		when(rset.next()).thenReturn(true);
		when(rset.getString("Password")).thenReturn("test");
		when(pstmt.executeQuery()).thenReturn(rset);
		boolean result = customerDAOMock.isPasswordCorrect();
		assertFalse(result);
		verify(pstmt, times(1)).setString(1, "tester2");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the getName method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetName() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester2");
		when(rset.next()).thenReturn(true);
		when(rset.getString("FirstName")).thenReturn("Tester2");
		when(pstmt.executeQuery()).thenReturn(rset);
		String result = customerDAOMock.getName();
		assertTrue(result=="Tester2");
		verify(pstmt, times(1)).setString(1, "tester2");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the getCard1 method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetCard1() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		when(rset.next()).thenReturn(true);
		when(rset.getString("cardnum1")).thenReturn("7467629871325120");
		when(pstmt.executeQuery()).thenReturn(rset);
		String result = customerDAOMock.getCard1();
		assertTrue(result=="7467629871325120");
		verify(pstmt, times(1)).setString(1, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the getCard2 method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetCard2() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		when(rset.next()).thenReturn(true);
		when(rset.getString("cardnum2")).thenReturn("2891547890063567");
		when(pstmt.executeQuery()).thenReturn(rset);
		String result = customerDAOMock.getCard2();
		assertTrue(result=="2891547890063567");
		verify(pstmt, times(1)).setString(1, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the getCard3 method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetCard3() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		when(rset.next()).thenReturn(true);
		when(rset.getString("cardnum3")).thenReturn("9991547916225432");
		when(pstmt.executeQuery()).thenReturn(rset);
		String result = customerDAOMock.getCard3();
		assertTrue(result=="9991547916225432");
		verify(pstmt, times(1)).setString(1, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the clearCardRecord method when input is "one"
	 * 
	 * @throws SQLException
	 */
	@Test
	void testClearCard1Record() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.clearCardRecord("one");
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, null);
		verify(pstmt, times(1)).setString(2, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the clearCardRecord method when input is "two"
	 * 
	 * @throws SQLException
	 */
	@Test
	void testClearCard2Record() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.clearCardRecord("two");
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, null);
		verify(pstmt, times(1)).setString(2, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the moveCard2Up method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testMoveCard2Up() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		doReturn("2891547890063567").when(customerDAOMock).getCard2();
		doReturn(true).when(customerDAOMock).clearCardRecord("two");
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.moveCard2Up();
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "2891547890063567");
		verify(pstmt, times(1)).setString(2, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the moveCard3Up method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testMoveCard3Up() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester3");
		doReturn("9991547916225432").when(customerDAOMock).getCard3();
		doReturn(true).when(customerDAOMock).clearCardRecord("three");
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.moveCard3Up();
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "9991547916225432");
		verify(pstmt, times(1)).setString(2, "tester3");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the saveNum method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testSaveNum() throws SQLException {
		when(customerDAOMock.customer.getUsername()).thenReturn("tester2");
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.saveNum("1212121212121212", "one");
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "1212121212121212");
		verify(pstmt, times(1)).setString(2, "tester2");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}

	/**
	 * Test the saveCard method with the scenerio is that card1 and card2 is not null, but card3 is null
	 * 
	 * @throws SQLException
	 */
	@Test
	void testSaveCard() throws SQLException {
		doReturn("2891547890063567").when(customerDAOMock).getCard1();
		doReturn("7467629871325120").when(customerDAOMock).getCard2();
		doReturn(null).when(customerDAOMock).getCard3();
		customerDAOMock.saveCard("1212121212121212");
		verify(customerDAOMock, times(1)).saveNum("1212121212121212", "three");
	}

	/**
	 * Test the saveCard method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testSaveOrder() throws SQLException {
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = customerDAOMock.saveOrder("Andrew", "1", "1", "1", "1", "23.99", "123456");
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "Andrew");
		verify(pstmt, times(1)).setString(2, "1");
		verify(pstmt, times(1)).setString(3, "1");
		verify(pstmt, times(1)).setString(4, "1");
		verify(pstmt, times(1)).setString(5, "1");
		verify(pstmt, times(1)).setString(6, "23.99");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}
}

