package selenium;

/**
 * Tests the Menu page.
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

public class MenuTest {
	WebDriver driver;
	
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/simple/");
		driver.manage().window().maximize();
		
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("tester1");
		pwdBox.sendKeys("test");
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
        assertEquals("Menu", title);
	}
	
	/**
	* Test if the total price of the order is correctly displayed.
	*/
	@Test
	public void orderItems() {
		WebElement burgerMinus = driver.findElement(By.id("burgerMinus"));
		WebElement burgerAdd = driver.findElement(By.id("burgerAdd"));
		WebElement sandwichMinus = driver.findElement(By.id("sandwichMinus"));
		WebElement sandwichAdd = driver.findElement(By.id("sandwichAdd"));
		WebElement chickenMinus = driver.findElement(By.id("chickenMinus"));
		WebElement chickenAdd = driver.findElement(By.id("chickenAdd"));
		WebElement colaMinus = driver.findElement(By.id("colaMinus"));
		WebElement colaAdd = driver.findElement(By.id("colaAdd"));
		
		WebElement burgerCounter = driver.findElement(By.id("burgerCounter"));
		WebElement sandwichCounter = driver.findElement(By.id("sandwichCounter"));
		WebElement chickenCounter = driver.findElement(By.id("friedChickenCounter"));
		WebElement colaCounter = driver.findElement(By.id("colaCounter"));
		WebElement total = driver.findElement(By.id("sum"));
		
		// test remove items when item count is 0
		sandwichMinus.click();
		colaMinus.click();
		String sandwichCount = sandwichCounter.getText();
		String colaCount = colaCounter.getText();
		String totalCost = total.getText();
		assertEquals("0", sandwichCount);
		assertEquals("0", colaCount);
		assertEquals("0.00", totalCost);
		
		// test correct item count and total cost displayed
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		burgerAdd.click();
		burgerAdd.click();
		chickenAdd.click();
		String burgerCount = burgerCounter.getText();
		String chickenCount = chickenCounter.getText();
		totalCost = total.getText();
		assertEquals("2", burgerCount);
		assertEquals("1", chickenCount);
        assertEquals("32.97", totalCost);
	}
	
	/**
	* Test if the page will alert the user when no payment methods are selected.
	*/
	@Test
	public void continueWithoutChoosingAnyPaymentMethod() {
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		continueBtn.click();
		String alertMsg = driver.switchTo().alert().getText();
		assertEquals("Please select one payment method", alertMsg);
	}
	
	/**
	* Test if the page will be directed to the payment page when the user selected the card payment.
	*/
	@Test
	public void continueWithCardPayment() {
		WebElement cardPayment = driver.findElement(By.id("method1"));
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		
		cardPayment.click();
		continueBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		String title = driver.getTitle();
		assertEquals("Payment", title);
	}
	
	/**
	* Test if the page will be directed to the success page when the user selected the cash payment.
	*/
	@Test
	public void continueWithCashPayment() {
		WebElement cashPayment = driver.findElement(By.id("method2"));
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		
		cashPayment.click();
		continueBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		String title = driver.getTitle();
		assertEquals("Success", title);
	}


}
