package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    By closePopupButton = By.xpath("//span[contains(text(),'✕')]");
    By searchBox = By.name("q");
    By electronicsMenu = By.xpath("//span[contains(text(),'Electronics')]");
    By dropdownMenu = By.xpath("//a[@title='Mobiles']");

    public boolean closeLoginPopup() {
        try {
        	driver.navigate().refresh();
        	Thread.sleep(2000);
            driver.findElement(closePopupButton).click();
            return true;
        } catch (Exception e) {
            System.out.println("Login popup not found.");
            return false;
        }
    }

    public void searchProduct(String productName) {
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchBox).submit();
    }

    public void hoverOnElectronics() {
        WebElement menu = driver.findElement(electronicsMenu);
        Actions actions = new Actions(driver);
        actions.moveToElement(menu).perform();
    }

    public boolean isDropdownVisible() {
    	WebElement divElement = driver.findElement(dropdownMenu); 
    	
    	if(divElement.isDisplayed()){
    	    System.out.println("Dropdown is visible");
    	    return true;
    	} else {
    	    System.out.println("Dropdown is NOT visible");
    	    return false;
    	}
    }
}
