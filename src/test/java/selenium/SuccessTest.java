package selenium;

/**
 * Tests the Success page.
 * 
 * @author Andrew Li
 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SuccessTest {
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
		
		WebElement cashPayment = driver.findElement(By.id("method2"));
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		cashPayment.click();
		continueBtn.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
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
        assertEquals("Success", title);
	}
	
	/**
	* Test if the reference number is successfully generated.
	*/
	@Test
	public void refNumLoads() {
		WebElement refNumHolder = driver.findElement(By.id("refNum"));
        String refNum = refNumHolder.getText();
        assertTrue(refNum.matches("\\d{6}"));
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
}
