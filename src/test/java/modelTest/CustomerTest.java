package modelTest;

import model.Customer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {
	Customer customer;
	
	@BeforeEach
	void setUp() {
		customer = new Customer("testUsername", "testPassword");
	}
	
	@Test
	void testGetUsername() {
		String expectedUsername = "testUsername";
		assertEquals(customer.getUsername(), expectedUsername);
	}
	
	@Test
	void testGetPassword() {
		String expectedPassword = "testPassword";
		assertEquals(customer.getPassword(), "testPassword");
	}
}
