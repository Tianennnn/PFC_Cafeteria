package dao;

/**
 * A class to interact with the "orders" table and manipulate the data.
 * 
 * @author Andrew Li
 */

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import org.tinylog.Logger;
import org.json.*;

public class AdminDAO {
	
	private DataSource pool;
	
	// queries
	static final String GET_ALL_ORDERS = "SELECT * FROM orders";
	static final String DELETE_ORDER_WITH_THE_REFERENCE_NUMBER = "DELETE FROM orders WHERE reference = ?";
	static final String GET_ALL_REFERENCE_NUMBERS = "SELECT reference FROM orders";
	
	/**
	* A helper function to get database connection.
	* @throws SQLException
	*/
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		try{
			if(pool==null) {
				InitialContext ctx = new InitialContext();
				pool = (DataSource)ctx.lookup("java:comp/env/jdbc/dbcp");
			}
		}
		catch(NamingException e) {
			throw new SQLException("Errors in connecting to database.");
		}
		conn = pool.getConnection();
		return conn;
	}
	
	/**
	* Gets information of orders. In order for the information to be readable in Javascript, the information
	* of orders is converted to JSONArray.
	* @return String The information of orders.
	* @throws SQLException
	*/
	public JSONArray getOrders() throws SQLException{
		Logger.info("getOrders Method for AdminDAO");
		JSONArray ordersJSON = new JSONArray ();
		
		String query = GET_ALL_ORDERS;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("getOrders: Connected to the database");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rset = pstmt.executeQuery();
			// process the result set
			while(rset.next()) {
				JSONObject order = new JSONObject();
				// store the informations of the order
				String customerName = rset.getString("firstname");
				order.put("customerName", customerName);
				String burgerCount = rset.getString("burgerCount");
				order.put("burgerCount", burgerCount);
				String sandwichCount = rset.getString("sandwichCount");
				order.put("sandwichCount", sandwichCount);
				String chickenCount = rset.getString("chickenCount");
				order.put("chickenCount", chickenCount);
				String colaCount = rset.getString("colaCount");
				order.put("colaCount", colaCount);
				String totalMoney = rset.getString("totalmoney");
				order.put("totalMoney", totalMoney);
				String refNum = rset.getString("reference");
				order.put("refNum", refNum);
				// append the information of the order to orders;
				ordersJSON.put(order);
			}
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return ordersJSON;
	}
	
	/**
	* Deletes the order with the given reference number
	* @param refNum The reference number of the order to be deleted from the database. 
	* 
	* @return boolean A variable indicating the status of deleting
	* 				  - True if the card is successfully deleted
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean deleteOrder(String refNum) {
		Logger.info("deleteOrder Method for AdminDAO");
		Connection conn = null;
		boolean result;
		// Connect to the database
		try {
			conn = getConnection();
			Logger.info("deleteOrder: Connected to the database");
			String query = DELETE_ORDER_WITH_THE_REFERENCE_NUMBER;
	
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, refNum);
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows!= 1) {
				result = false;
			}
			else {
				result = true;
			}
			result = true;
		}
		catch(SQLException e) {
			result =  false;
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException ex) {
				result =  false;
			}
		}
		return result;
	}
	
	/**
	* Gets all the reference numbers of existing orders.

	* @return String A string representation of all the reference numbers of existing orders, separated by spaces.
	* @throws SQLException
	*/
	public String getRefNums(){
		Logger.info("getRefNums Method for AdminDAO");
		Connection conn = null;
		String refNums="";
		
		String query = GET_ALL_REFERENCE_NUMBERS;
		try {
			// Connect to the database
			conn = getConnection();
			Logger.info("getRefNums: Connected to the database");
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rset = pstmt.executeQuery();
			
			// process the result set
			while(rset.next()) {
				String refNum = rset.getString("reference");
				// append the reference number to the result string
				refNums = refNums + refNum + " ";
			}
		}catch(SQLException e) {
			refNums="";
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException ex) {
				refNums="";
			}
		}
		return refNums;
	}
}
