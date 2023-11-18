package selenium;

/**
 * Tests the Welcome page, especially the login functionalities.
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

public class WelcomeTest {
	WebDriver driver;
	
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/simple/");
		driver.manage().window().maximize();
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
        assertEquals("Welcome", title);
	}
	
	/**
	* Test if a user is able to log in using valid username and correct password.
	*/
	@Test
	public void ValidCorrectLogIn() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("tester1");
		pwdBox.sendKeys("test");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		// test if the page is directed to the menu page.
		String newTitle = driver.getTitle();
		assertEquals("Menu", newTitle);
	}
	
	/**
	* Test if users will get an alert when log in using blank username and/or blank password.
	*/
	@Test
	public void BlankLogIn() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		String alertMsg = driver.switchTo().alert().getText();
		assertEquals("Username cannot be blank", alertMsg);
		driver.switchTo().alert().accept();
		usernameBox.sendKeys("random");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		String alertMsg2 = driver.switchTo().alert().getText();
		assertEquals("Password cannot be blank", alertMsg2);
	}
	
	/**
	* Test if the page will display proper error message when log in using unregistered username.
	*/
	@Test
	public void UnregisteredLogIn() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("unregistered");
		pwdBox.sendKeys("test");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		
		WebElement message = driver.findElement(By.id("errorMsg"));
        String value = message.getText();
        assertEquals("The user is not registered", value);
	}
	
	/**
	* Test if the page will display proper error message when log in using incorrect password.
	*/
	@Test
	public void IncorrectLogIn() {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		WebElement usernameBox = driver.findElement(By.id("username"));
		WebElement pwdBox = driver.findElement(By.id("password"));
		WebElement LogInButton = driver.findElement(By.id("LogInBtn"));
		usernameBox.sendKeys("tianennnn");
		pwdBox.sendKeys("test");
		LogInButton.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		
		WebElement message = driver.findElement(By.id("errorMsg"));
        String value = message.getText();
        assertEquals("The password is incorrect", value);
	}
}
