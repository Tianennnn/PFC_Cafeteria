package selenium;

/**
 * Tests the Admin page.
 * 
 * @author Andrew Li
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.*;
import org.tinylog.Logger;

public class AdminTest {
	WebDriver driver;
	
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/simple/");
		driver.manage().window().maximize();
		
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("admin");
		pwdBox.sendKeys("admin");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	@AfterEach
	public void cleanup() {
		driver.quit();
	}
	
	/**
	* Test if the page is successfully loaded.
	*/
	@Test
	public void pageLoads() {
		String title = driver.getTitle();
        assertEquals("Admin", title);
	}
	
	/**
	* Test if the user will be directed to the welcome page after clicking the log out button.
	*/
	@Test
	public void logOutBtn() {
		WebElement logOutBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Log Out']"));
		logOutBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		String title = driver.getTitle();
		assertEquals("Welcome", title);
	}
	
	/**
	* Test if the page will display proper messages when no orders.
	*/
	@Test
	public void noOrders() {
		WebElement orders = driver.findElement(By.className("orders"));
        String order1 = orders.getText();
        
        if(order1.equals("")) {
        	WebElement emptyMsg = driver.findElement(By.id("emptyMsg"));
        	String emptyMsgContent = emptyMsg.getText();
        	assertEquals("There are currently no orders to display.", emptyMsgContent);
        }
	}

	/**
	* Place an order and test if the order information is shown in Admin Page.
	*/
	@Test
	public void testInsertNewOrder() {
		// back to Welcome Page
		WebElement adminLogOutBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Log Out']"));
		adminLogOutBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

		// log in as a customer
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("tester1");
		pwdBox.sendKeys("test");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

		// place an order
		WebElement burgerAdd = driver.findElement(By.id("burgerAdd"));
		burgerAdd.click();
		WebElement cashPayment = driver.findElement(By.id("method2"));
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		cashPayment.click();
		continueBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		
		// get the reference number of the new order
		WebElement refNumHolder = driver.findElement(By.id("refNum"));
        String newRefNum = "#"+refNumHolder.getText();

		// log out
		WebElement successLogOutBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Log Out']"));
		successLogOutBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		
		// log in back as an admin
		WebElement usernameBox2 = driver.findElement(By.id("username"));
		WebElement pwdBox2 = driver.findElement(By.id("password"));
		WebElement LogInButton2 = driver.findElement(By.id("LogInBtn"));
		usernameBox2.sendKeys("admin");
		pwdBox2.sendKeys("admin");
		LogInButton2.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

		// check if the newly placed order is shown in the page
		List<WebElement> orderHeads = driver.findElements(By.className("orderHead"));
		String refNum = orderHeads.get(orderHeads.size()-1).getText().trim();
		assertEquals(refNum, newRefNum);
	}
	
	/**
	* Place an order and test if the order information is shown in Admin Page.
	*/
	@Test
	public void testDeleteOrder() {
		List<WebElement> orderHeads = driver.findElements(By.className("orderHead"));
		if(orderHeads.size()>0) {
			// delete the first order
			String deletedRefNum = orderHeads.get(0).getText().trim();
			WebElement deleteBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Delete']"));
			deleteBtn.click();
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			
			// check if the deleted order is still shown in the page
			List<WebElement> orderHeadsAfter = driver.findElements(By.className("orderHead"));
			String refNum = orderHeadsAfter.get(0).getText().trim();
			assertNotEquals(refNum, deletedRefNum);
		}
	}
	
}
