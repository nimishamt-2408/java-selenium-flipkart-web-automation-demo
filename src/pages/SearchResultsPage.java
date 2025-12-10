package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SearchResultsPage {
    WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By productTitles = By.xpath("//div[contains(@class,'RG5Slk')]");

    public List<WebElement> getAllProducts() {
        return driver.findElements(productTitles);
    }

    public void clickFirstProduct() {
        getAllProducts().get(0).click();
    }
}
