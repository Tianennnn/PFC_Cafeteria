package model;

/**
 * This class represents a customer. Each customer has a specific username and password.

 * @author Andrew Tian-en Li
 */

import org.tinylog.Logger;

public class Customer {
	private String username;
	private String password;
	
	/** Creates a customer with the account.
	 * @param username The username of the customer’s account.
	 * @param password The password of the customer's account.
	*/
	public Customer(String username, String password) {
	      this.username = username;
	      this.password = password;
	}
	
	/** Gets the customer’s username.
	 * @return A string representing the employee’s username.
	*/
	public String getUsername() {
		Logger.info("getUsername Method for the Customer class");
		return username;
	}
	
	/** Gets the customer’s password.
	 * @return A string representing the employee’s password.
	*/
	public String getPassword() {
		Logger.info("getPassword Method for the Customer class");
		return password;
	}
}
