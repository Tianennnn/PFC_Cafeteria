package dao;

/**
 * A class to retrieve information of customers from database as well as to update them.
 * 
 * @author Andrew Li
 */


import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import model.Customer;
import org.tinylog.Logger;

public class CustomerDAO {
	public Customer customer;
	private DataSource pool;
//	private String db_url = "jdbc:postgresql://tianennnn:123698745@localhost:5432/simpledb";
	
	// queries
	static final String GET_ALL_CUSTOMERS_WITH_THE_USERNAME = "SELECT * FROM customers WHERE Username = ?";
	static final String GET_PASSWORD_WITH_THE_USERNAME = "SELECT Password FROM customers WHERE Username = ?";
	static final String GET_CUSTOMER_NAME_WITH_THE_USERNAME = "select FirstName from customers where Username = ?";
	static final String GET_CARD1_WITH_THE_USERNAME = "SELECT cardnum1 FROM customers WHERE Username = ?";
	static final String GET_CARD2_WITH_THE_USERNAME = "SELECT cardnum2 FROM customers WHERE Username = ?";
	static final String GET_CARD3_WITH_THE_USERNAME = "SELECT cardnum3 FROM customers WHERE Username = ?";
	static final String UPDATE_CARD1_WITH_THE_USERNAME = "UPDATE customers SET cardnum1 = ? WHERE Username= ?";
	static final String UPDATE_CARD2_WITH_THE_USERNAME = "UPDATE customers SET cardnum2 = ? WHERE Username= ?";
	static final String UPDATE_CARD3_WITH_THE_USERNAME = "UPDATE customers SET cardnum3 = ? WHERE Username= ? ";
	static final String INSERT_ORDER = "INSERT INTO orders VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	
	
	public CustomerDAO(Customer customer) {
	      this.customer = customer;
	}
	
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
	* Determines if the customer is registered or not.
	* @return boolean true when the customer is registered, otherwise false.
	* @throws SQLException
	*/
	public boolean isRegistered() throws SQLException{
		boolean result = false;
		Logger.info("isRegiestered Method for CustomerDAO");
		String query = GET_ALL_CUSTOMERS_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("isRegistered: Connected to the database");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			result = rset.next();
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		// checking if any records in the database has the username matches the input username
		return result;
	}
	
	/**
	* Determines if the input password is correct for the input username
	* @return boolean true if the input password is correct, otherwise returns false.
	* @throws SQLException
	*/
	public boolean isPasswordCorrect() throws SQLException{
		Logger.info("isPasswordCorrect Method for CustomerDAO");
		boolean result = false;
		String query = GET_PASSWORD_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("isPasswordCorrect: Connected to the database");
			
		try {
			Logger.info("asdadawdasdadwa: " + conn.isClosed());
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();

		    // process the query result
			rset.next();
			String correct_pwd = rset.getString("Password");
			String input_pwd = customer.getPassword();
			result = correct_pwd.equals(input_pwd);
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return result;
	}
	
	/**
	* Retrieves the first name of the customer from the database.
	* @return String the first name of the customer.
	* @throws SQLException
	*/
	public String getName() throws SQLException{
		Logger.info("getName Method for CustomerDAO");
		String name = "";
		String query = GET_CUSTOMER_NAME_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("getName: Connected to the database");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			
			// process the query result
			rset.next();
			name = rset.getString("FirstName");
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return name;
	}
	
	/**
	* Retrieves the card number of card1 from the database
	* @return String the card number of card1
	* @throws SQLException
	*/
	public String getCard1() throws SQLException{
		Logger.info("getCard1 Method for CustomerDAO");
		String card1 = "";
		String query = GET_CARD1_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("getCard1: Connected to the database");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			    
			// process the query result
			rset.next();
			card1 = rset.getString("cardnum1");
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return card1;
	}
	
	/**
	* Retrieves the card number of card2 from the database
	* @return String the card number of card2
	* @throws SQLException
	*/
	public String getCard2() throws SQLException{
		Logger.info("getCard2 Method for CustomerDAO");
		String card2 = "";
		String query = GET_CARD2_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("getCard2: Connected to the database");
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			    
			// process the query result
			rset.next();
			card2 = rset.getString("cardnum2");
			conn.close();
		}
		catch(SQLException e) {
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return card2;
	}
	
	/**
	* Retrieves the card number of card3 from the database
	* @return String the card number of card3
	* @throws SQLException
	*/
	public String getCard3() throws SQLException{
		Logger.info("getCard3 Method for CustomerDAO");
		String card3 = ""; 
		String query = GET_CARD3_WITH_THE_USERNAME;
		// Connect to the database
		Connection conn = getConnection();
		Logger.info("getCard3: Connected to the database");

		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, username);
			ResultSet rset = pstmt.executeQuery();
			 
			// process the query result
			rset.next();
			card3 = rset.getString("cardnum3");
			conn.close();
		}
		catch(SQLException e){
			conn.close();
			throw new SQLException("Errors in executing queries.");
		}
		return card3;
	}
	
	/**
	* A helper function to delete the card with the given index
	* @param index The index of the card to be deleted. 
	* 		       The value should be either "one" or "two" or "three", for card1, card2, card3 respectively
	* @return boolean A variable indicating the status of deleting
	* 				  - True if the card is successfully deleted
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean clearCardRecord(String index) {
		Logger.info("clearCardRecord Method for CustomerDAO");
		Connection conn = null;
		boolean result;
		// Connect to the database
		try {
			conn = getConnection();
			Logger.info("clearCardRecord: Connected to the database");
		
			String query = "";
			// determine which card to clear based on given input
			switch(index) {
				case "one":
					query = UPDATE_CARD1_WITH_THE_USERNAME;
					break;
				
				case "two":
					query = UPDATE_CARD2_WITH_THE_USERNAME;
					break;
				
				case "three":
					query = UPDATE_CARD3_WITH_THE_USERNAME;
					break;
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, null);
			pstmt.setString(2, username);
			int affectedRows = pstmt.executeUpdate();
			
			if(affectedRows != 1) {
				result =  false;
			}
			else {
				result =  true;
			}
		}
		catch(SQLException e) {
			result =  false;
		}
		finally{
			try {
				conn.close();
			}
			catch(SQLException ex){
				result =  false;
			}
		}
		return result;
	}
		
	
	/**
	* A helper function to store the card number of card2 to card1
	* @return boolean A variable indicating the status of deleting
	* 				  - True if card2 is successfully "moved up"
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean moveCard2Up(){
		Logger.info("moveCard2Up Method for CustomerDAO");
		Connection conn = null;
		boolean result;
		
		try {
			// Connect to the database
			conn = getConnection();
			Logger.info("moveCard2Up: Connected to the database");
				
			String query = UPDATE_CARD1_WITH_THE_USERNAME;
			PreparedStatement pstmt = conn.prepareStatement(query);
			// get the card number of card2
			String card2Num = getCard2();
			String username = customer.getUsername();
			pstmt.setString(1, card2Num);
			pstmt.setString(2, username);
			// set the card number of card1 to card2Num
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows != 1) {
				result =  false;
			}
			else {
				// clear the card number of card2
				boolean clearResult = clearCardRecord("two");
				result = clearResult;
			}
		}
		catch(SQLException e) {
			result = false;
		}
		finally {
			try {
				conn.close();
			}
			catch (SQLException ex){
				result = false;
			}
		}
		return result;
	}
	
	/**
	* A helper function to store the card number of card3 to card2
	* @return boolean A variable indicating the status of deleting
	* 				  - True if card3 is successfully "moved up"
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean moveCard3Up(){
		Logger.info("moveCard3Up Method for CustomerDAO");
		Connection conn = null;
		boolean result;
		try {
			// Connect to the database
			conn = getConnection();
			Logger.info("moveCard3Up: Connected to the database");
				
			String query = UPDATE_CARD2_WITH_THE_USERNAME;
			PreparedStatement pstmt = conn.prepareStatement(query);
			// get the card number of card3
			String card3Num = getCard3();
			String username = customer.getUsername();
			pstmt.setString(1, card3Num);
			pstmt.setString(2, username);
			// set the card number of card2 to card3Num
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows == 0) {
				result = false;
			}
			else {
				// clear the card number of card3
				boolean clearResult = clearCardRecord("three");
				result = clearResult;
			}
		}
		catch(SQLException e) {
			result =  false;
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException ex){
				result =  false;
			}
		}
		return result;
	}
	
	/**
	* Deletes the card with the given index and then organizes the card records so that
	* cardX cannot be null if card(X+1) is not null.
	* @param index The index of the card to be deleted. 
	* 		       The value should be either "one" or "two" or "three", for card1, card2, card3 respectively
	* @return boolean A variable indicating the status of deleting
	* 				  - True if the card is successfully deleted
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean deleteCard(String index){
		Logger.info("deleteCard Method for CustomerDAO");
		try {
			if(index.equals("one")) {
				// delete card1
				boolean clearResult = clearCardRecord("one");
				if(!clearResult) {
					return false;
				}
				String card2Num = getCard2();
				// organize the card records
				if(card2Num!=null) {
					boolean move2Result = moveCard2Up();
					if(!move2Result) {
						return false;
					}
					String card3Num = getCard3();
					if(card3Num!=null) {
						boolean move3Result = moveCard3Up();
						if(!move3Result) {
							return false;
						}
					}
				}
			}
			else if(index.equals("two")) {
				// delete card2
				boolean clearResult = clearCardRecord("two");
				if(!clearResult) {
					return false;
				}
				// organize the card records
				String card3Num = getCard3();
				if(card3Num!=null) {
					boolean move3Result = moveCard3Up();
					if(!move3Result) {
						return false;
					}
				}
			}
			else {		// if(index.equals("three"))
				boolean clearResult = clearCardRecord("three");
				if(!clearResult) {
					return false;
				}
			}
		}
		catch(SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	* A helper function to store the given card number to the card with the given index
	* @param newNum The card number to be saved.
	* @param index The index of the card to be deleted. 
	* 		       The value should be either "one" or "two" or "three", for card1, card2, card3 respectively
	* @return boolean A variable indicating the status of saving
	* 				  - True if the card number is successfully saved to the intended card
	* 				  - False if there is any error with database connection or query execution
	*/
	public boolean saveNum(String newNum, String index) {
		Logger.info("saveNum Method for CustomerDAO");
		Connection conn = null;
		boolean result;
		String query = "";
		// determine which card slot to save based on given index
		switch(index) {
			case "one":
				query = UPDATE_CARD1_WITH_THE_USERNAME;
				break;
			
			case "two":
				query = UPDATE_CARD2_WITH_THE_USERNAME;
				break;
			
			case "three":
				query = UPDATE_CARD3_WITH_THE_USERNAME;
				break;
		}
		
		try {
			// Connect to the database
			conn = getConnection();
			Logger.info("saveNum: Connected to the database");
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			String username = customer.getUsername();
			pstmt.setString(1, newNum);
			pstmt.setString(2, username);
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows == 0) {
				result = false;
			}
			else {
				result =  true;
			}
		}
		catch(SQLException e){
			result =  false;
		}
		finally{
			try{
				conn.close();
			}
			catch(SQLException ex){
				result =  false;
			}
		}
		return result;
	}
	
	/**
	* Store the given card number to the next empty card slot.
	* @param newNum The card number to be saved.
	* @return String A variable indicating the status of saving
	* 				  - "Duplicate" if there is already a card with the same card number saved for the customer
	* 				  - "Full" if all three card slots has been saved.
	* 				  - "Fail" if there is any error with database connection or query execution
	* 				  - "Success" if the card number is successfully saved
	*/
	public String saveCard(String newNum) {
		Logger.info("saveCard Method for CustomerDAO");
		String index;		// points to the card slot to save the new card number
		try {
			String card1Num = getCard1();
			// check if card1 is already occupied
			if(card1Num != null) {
				if(newNum.equals(card1Num)) {
					return "Duplicate";
				}
				String card2Num = getCard2();
				// check if card2 is already occupied
				if(card2Num != null) {
					if(newNum.equals(card2Num)) {
						return "Duplicate";
					}
					String card3Num = getCard3();
					// check if card3 is already occupied
					if(card3Num != null) {
						if(newNum.equals(card3Num)) {
							return "Duplicate";
						}
						// all three card slot is occupied
						return "Full";
					}
					else {
						// card3 is not occupied and is open to save newNum
						index = "three";
					}
				}
				else {
					// card2 is not occupied and is open to save newNum
					index = "two";
				}
			}
			else {
				// card1 is not occupied and is open to save newNum
				index = "one";
			}
			String saveResult;
			// save newNum to the card slot with the index just determined
			if(saveNum(newNum, index)) {
				saveResult = "Success";
			}
			else {
				saveResult = "Fail";
			}
			return saveResult;
		}
		catch(SQLException e) {
			return "Fail";
		}
	}
	
	/**
	* Create a new order record and store the given order information.
	* @param customerName The name of the customer to be saved.
	* @param burgerCount The count of burger that the customer ordered.
	* @param sandwichCount The count of sandwich that the customer ordered.
	* @param friedChickenCount The count of fried chicken that the customer ordered.
	* @param colaCount The count of cola that the customer ordered.
	* @param totalMoney The total cost of the customer's order.
	* @param refNum The reference number of the order to be saved.
	* 
	* @return boolean A variable indicating the status of saving,
	* 				  returns false if there is any error with database connection or query execution
	* 				  Otherwise, return true if the order information is successfully saved
	*/
	  
	public boolean saveOrder(String customerName, String burgerCount, String sandwichCount,
							String friedChickenCount, String colaCount, String totalMoney, String refNum) {
		Logger.info("saveOrder Method for CustomerDAO");
		Connection conn = null;
		boolean result;
		String query = INSERT_ORDER;
		
		try {
			// Connect to the database
			conn = getConnection();
			Logger.info("saveOrder: Connected to the database");
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, customerName);
			pstmt.setString(2, burgerCount);
			pstmt.setString(3, sandwichCount);
			pstmt.setString(4, friedChickenCount);
			pstmt.setString(5, colaCount);
			pstmt.setString(6, totalMoney);
			pstmt.setString(7, refNum);
			Logger.info(pstmt.toString());
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows != 1) {
				result = false;
			}
			else {
				result =  true;
			}
		}
		catch(SQLException e){
			result =  false;
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException ex) {
				result = false;
			}
		}
		return result;
	}
}
