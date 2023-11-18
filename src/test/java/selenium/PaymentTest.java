package selenium;

/**
 * Tests the Payment page.
 * 
 * @author Andrew Li
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class PaymentTest {
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
		
		WebElement cardPayment = driver.findElement(By.id("method1"));
		WebElement continueBtn = driver.findElement(By.cssSelector("input[type='submit'][value='Continue']"));
		cardPayment.click();
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
        assertEquals("Payment", title);
	}
	
	/**
	* Test if the page will display proper messages when the user has no saved cards.
	*/
	@Test
	public void noSavedCardMsgDisplayed() {
		WebElement card1 = driver.findElement(By.id("card1Num"));
        String card1Num = card1.getText();
        if(card1Num.equals("")) {
        	WebElement noCardsMsg = driver.findElement(By.id("noCards"));
        	String noCardsMsgValue = noCardsMsg.getText();
            assertEquals("You don't have any saved cards.", noCardsMsgValue);
        }
	}
	
	/**
	* Test if users will be prompted by proper error message if they try to save more than 3 cards.
	*/
	@Test
	public void SaveMaxNumCards() {
		WebElement card3 = driver.findElement(By.id("card3Num"));
        String card3Num = card3.getText();
		// when the user already saved 3 cards
        if(!card3Num.equals("")) {
        	WebElement newNumBox = driver.findElement(By.id("newNum"));
        	newNumBox.sendKeys("6767676767676767");
        	WebElement saveBtn = driver.findElement(By.id("save"));
        	saveBtn.click();
        	WebElement errorMsg = driver.findElement(By.id("errorMsg"));
        	String errorMsgText = errorMsg.getText();
    		assertEquals("You can only save maxmun 3 cards.", errorMsgText);
        }
	}

	/**
	* Test if users will be prompted by proper error message if they try to save a card that is already saved.
	*/
	@Test
	public void SaveDuplicateCard() {
		WebElement card1 = driver.findElement(By.id("card1Num"));
        String card1Num = card1.getText();
		WebElement card3 = driver.findElement(By.id("card3Num"));
        String card3Num = card3.getText();
		// when there is still space to save new cards and at least one card is saved
        if(card3Num.equals("") && card1Num != "") {
        	WebElement newNumBox = driver.findElement(By.id("newNum"));
        	// try save a card that is identical to card1's number
			newNumBox.sendKeys(card1Num);
        	WebElement saveBtn = driver.findElement(By.id("save"));
        	saveBtn.click();
        	WebElement errorMsg = driver.findElement(By.id("errorMsg"));
        	String errorMsgText = errorMsg.getText();
    		assertEquals("The card is already saved.", errorMsgText);
        }
	}

	/**
	* Test if users can save a new card
	*/
	@Test
	public void SaveNewCard() {
		WebElement card1 = driver.findElement(By.id("card1Num"));
        String card1Num = card1.getText();
		WebElement card2 = driver.findElement(By.id("card2Num"));
        String card2Num = card2.getText();
		WebElement card3 = driver.findElement(By.id("card3Num"));
        String card3Num = card3.getText();
		// when there is still space to save new cards
        if(card3Num.equals("") ) {
        	WebElement newNumBox = driver.findElement(By.id("newNum"));
			String newCardNum = "1234123412341234";
			// make sure the new card is not identical to card1 and card2
			if(newCardNum != card1Num && newCardNum != card2Num){
				newNumBox.sendKeys(newCardNum);
				WebElement saveBtn = driver.findElement(By.id("save"));
				saveBtn.click();
				// check if the new card is successfully saved and displayed on page
				WebElement card1After = driver.findElement(By.id("card1Num"));
        		String card1NumAfter = card1.getText();
				WebElement card2After = driver.findElement(By.id("card2Num"));
        		String card2NumAfter = card2.getText();
				WebElement card3After = driver.findElement(By.id("card3Num"));
        		String card3NumAfter = card3.getText();
				boolean result = false;
				if(card1NumAfter.equals(newCardNum) || card2NumAfter.equals(newCardNum) || card3NumAfter.equals(newCardNum)){
					result = true;
				}
				assertTrue(result);
			}
        }
	}
	
	/**
	* Test if users can delete saved cards.
	*/
	@Test
	public void deleteSavedCard() {
		WebElement card1 = driver.findElement(By.id("card1Num"));
        String card1Num = card1.getText();
        // test delete card1
        if(!card1Num.equals("")) {
        	WebElement selectCard1 = driver.findElement(By.id("card1"));
        	selectCard1.click();
        	WebElement deleteBtn = driver.findElement(By.id("deleteBtn"));
        	deleteBtn.click();
        	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        	
        	// test if the page remains at the Payment page
        	String title = driver.getTitle();
            assertEquals("Payment", title);
            
            // test if the card is deleted
            WebElement newCard1 = driver.findElement(By.id("card1Num"));
            String newCard1Num = newCard1.getText();
            assertNotEquals(newCard1Num, card1Num);
            
            // save the deleted card back
            WebElement newNumBox = driver.findElement(By.id("newNum"));
        	newNumBox.sendKeys(card1Num);
        	WebElement saveBtn = driver.findElement(By.id("save"));
        	saveBtn.click();
        }
	}
	
	/**
	* Test if users can pay using a new card.
	*/
	@Test
	public void payUsingNewCard() {
        WebElement newNumBox = driver.findElement(By.id("newNum"));
        newNumBox.sendKeys("1212121212121212");
        WebElement payBtn = driver.findElement(By.id("payNew"));
        payBtn.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		String title = driver.getTitle();
		assertEquals("Success", title);
	}
	
	/**
	* Test if users can pay using a saved card.
	*/
	@Test
	public void payUsingSavedCard() {
		WebElement selectCard1 = driver.findElement(By.id("card1"));
    	selectCard1.click();
    	WebElement payBtn = driver.findElement(By.id("payBtn"));
    	payBtn.click();
    	driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    	String title = driver.getTitle();
		assertEquals("Success", title);
	}
	
	/**
	* Test if users will be alerted when they try to pay or save with a blank card number
	*/
	@Test
	public void payOrSaveEmptyCard() {
		WebElement newNumBox = driver.findElement(By.id("newNum"));
		newNumBox.sendKeys("");
		// test pay button
        WebElement payBtn = driver.findElement(By.id("payNew"));
        payBtn.click();
        String alertMsg = driver.switchTo().alert().getText();
		assertEquals("Card numbers cannot be blank.", alertMsg);
		driver.switchTo().alert().accept();
		// test save button
        WebElement saveBtn = driver.findElement(By.id("save"));
    	saveBtn.click();
    	String alertMsg2 = driver.switchTo().alert().getText();
    	assertEquals("Card numbers cannot be blank.", alertMsg);
	}
	
	/**
	* Test if users will be alerted when they try to pay or save with a non 16-digit card number
	*/
	@Test
	public void payOrSaveInvalidCard() {
		WebElement newNumBox = driver.findElement(By.id("newNum"));
        newNumBox.sendKeys("121212212");
        // test pay button
        WebElement payBtn = driver.findElement(By.id("payNew"));
        payBtn.click();
        String alertMsg = driver.switchTo().alert().getText();
		assertEquals("Card numbers must be 16-digit long.", alertMsg);
		driver.switchTo().alert().accept();
		// test save button
        WebElement saveBtn = driver.findElement(By.id("save"));
    	saveBtn.click();
    	assertEquals("Card numbers must be 16-digit long.", alertMsg);
	}
	
	/**
	* Test if the page will alert the user when they try to pay and no saved card are selected.
	*/
	@Test
	public void payWithoutChoosingAnySavedCard() {
		WebElement payBtn = driver.findElement(By.id("payBtn"));
    	payBtn.click();
		String alertMsg = driver.switchTo().alert().getText();
		assertEquals("Please select one card to delete or pay.", alertMsg);
	}
}
