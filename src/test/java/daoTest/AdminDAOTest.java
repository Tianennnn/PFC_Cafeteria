package daoTest;

import model.Customer;
import dao.CustomerDAO;
import dao.AdminDAO;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;

/**
 * A class to test the AdminDAO class.
 * 
 * @author Andrew Li
 */

@ExtendWith(MockitoExtension.class)
public class AdminDAOTest {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rset;
	private AdminDAO adminDAOMock;
	
	@BeforeEach
	void setup() throws SQLException {
		adminDAOMock = spy(AdminDAO.class);
		conn = mock(Connection.class);
		pstmt = mock(PreparedStatement.class);
		doReturn(conn).when(adminDAOMock).getConnection();
		when(conn.prepareStatement(any(String.class))).thenReturn(pstmt);
		rset = mock(ResultSet.class);
	}
	
	/**
	 * Test the getOrders method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetOrders() throws SQLException {
		when(pstmt.executeQuery()).thenReturn(rset);
		when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(rset.getString("firstname")).thenReturn("Andrew").thenReturn("Tester");
		when(rset.getString("burgerCount")).thenReturn("1").thenReturn("2");
		when(rset.getString("sandwichCount")).thenReturn("0").thenReturn("0");
		when(rset.getString("chickenCount")).thenReturn("1").thenReturn("0");
		when(rset.getString("colaCount")).thenReturn("0").thenReturn("4");
		when(rset.getString("totalmoney")).thenReturn("23.98").thenReturn("27.94");
		when(rset.getString("reference")).thenReturn("263765").thenReturn("873456");
		JSONArray result = adminDAOMock.getOrders();
		assertEquals("[{\"sandwichCount\":\"0\",\"chickenCount\":\"1\",\"refNum\":\"263765\",\"totalMoney\":\"23.98\",\"colaCount\":\"0\",\"burgerCount\":\"1\",\"customerName\":\"Andrew\"},{\"sandwichCount\":\"0\",\"chickenCount\":\"0\",\"refNum\":\"873456\",\"totalMoney\":\"27.94\",\"colaCount\":\"4\",\"burgerCount\":\"2\",\"customerName\":\"Tester\"}]", result.toString());
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}
	
	/**
	 * Test the deleteOrder method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testDeleteOrder() throws SQLException {
		when(pstmt.executeUpdate()).thenReturn(1);
		boolean result = adminDAOMock.deleteOrder("123456");
		assertTrue(result);
		verify(pstmt, times(1)).setString(1, "123456");
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}
	
	/**
	 * Test the getRefNums method
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetRefNums() throws SQLException {
		when(pstmt.executeQuery()).thenReturn(rset);
		when(rset.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(rset.getString("reference")).thenReturn("234234").thenReturn("876678").thenReturn("345980");
		String result = adminDAOMock.getRefNums();
		assertEquals("234234 876678 345980 ", result);
		// check if the database connection is closed
		verify(conn, times(1)).close();
	}
	
}
