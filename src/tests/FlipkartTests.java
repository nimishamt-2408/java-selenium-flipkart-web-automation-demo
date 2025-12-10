package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;

import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import base.BaseClass;

public class FlipkartTests extends BaseClass {
	
    @Test(priority = 1)
    public void launchFlipkartAndVerifyTitle() {
        try {
        	String title = driver.getTitle();
        	
	        Assert.assertTrue(title.contains("Online Shopping"), "Title verification failed!");
	        test.log(LogStatus.PASS, "Test: Verify homepage title");
    	} catch (Exception e) {
			test.log(LogStatus.FAIL, "Homepage title verification failed due to exception: " + e.getMessage());
			addScreenCapture("Failed_HomepageTitleTest", true);
			throw e;
		} catch (AssertionError ae) {
			test.log(LogStatus.FAIL, "Homepage title verification failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_HomepageTitleTest", true);
			throw ae;
		}
    }

    @Test(priority = 2)
    public void handleLoginPopup() {
    	try {
	    	HomePage homePage = new HomePage(driver);
	    	
	    	Assert.assertTrue(homePage.closeLoginPopup(), "Login popup handling failed!");
	    	test.log(LogStatus.PASS, "Test: Handle login popup");
    	} catch (Exception e) {
    	            test.log(LogStatus.FAIL, "Login popup handling failed due to exception: " + e.getMessage());
    	            addScreenCapture("Failed_LoginPopupHandling", true);
    	            throw e;
		} catch (AssertionError ae) {
			test.log(LogStatus.FAIL, "Login popup handling failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_LoginPopupHandling", true);
			throw ae;
		}
    }

    @Test(priority = 3)
    public void searchProduct() {
    	try {
	    	HomePage homePage = new HomePage(driver);
	        homePage.searchProduct("mobile");
	        Assert.assertTrue(driver.getCurrentUrl().contains("mobile"), "Search results not displayed!");
	    	test.log(LogStatus.PASS, "Test: Search for product 'mobile'");
    	} catch (Exception e) {
            test.log(LogStatus.FAIL, "Product search failed due to exception: " + e.getMessage());
            addScreenCapture("Failed_ProductSearch", true);
            throw e;
    	} catch (AssertionError ae) {
            test.log(LogStatus.FAIL, "Product search failed due to assertion error: " + ae.getMessage());
            addScreenCapture("Failed_ProductSearch", true);
            throw ae;
    	}
    }

    @Test(priority = 4)
    public void captureProductListings() {
		try {
	    	SearchResultsPage searchPage = new SearchResultsPage(driver);
	        List<WebElement> products = searchPage.getAllProducts();
	        System.out.println("Total products listed: " + products.size());
	    	addScreenCapture("Product listing page", false);
	        
	        for (int i = 0; i < 5; i++) {
	            System.out.println((i + 1) + ": " + products.get(i).getText());
	        }
	        
	        Assert.assertTrue(products.size() > 0, "No products found.");
	        test.log(LogStatus.PASS, "Test: Capture product listings");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Capturing product listings failed due to exception: " + e.getMessage());
			addScreenCapture("Failed_CaptureProductListings", true);
			throw e;
		}catch(AssertionError ae) {
			test.log(LogStatus.FAIL, "Capturing product listings failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_CaptureProductListings", true);
			throw ae;
		}    
    }

    @Test(priority = 5)
    public void clickFirstProductAndHandleTab() throws InterruptedException {
    	try {
	    	SearchResultsPage searchPage = new SearchResultsPage(driver);
	        String parentWindow = driver.getWindowHandle();
	        searchPage.clickFirstProduct();
	        Thread.sleep(3000);
	        boolean switched = false;
	
	        Set<String> allWindows = driver.getWindowHandles();
	        for (String handle : allWindows) {
	            if (!handle.equals(parentWindow)) {
	                driver.switchTo().window(handle);
	                switched = true;
	                break;
	            }
	        }
	        
	        // Assert that the window switch actually happened
	        Assert.assertTrue(switched, "Failed to switch to the new window!");
	        test.log(LogStatus.PASS, "Test: Click first product and handle new tab");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Clicking first product and handling tab failed due to exception: " + e.getMessage());
			addScreenCapture("Failed_ClickFirstProduct", true);
			throw e;
		} catch (AssertionError ae) {
			test.log(LogStatus.FAIL,
					"Clicking first product and handling tab failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_ClickFirstProduct", true);
			throw ae;
		}
    }

    @Test(priority = 6)
    public void verifyProductDetails() {
    	try {
		    ProductPage productPage = new ProductPage(driver);
	        String name = productPage.getProductName();
	        String price = productPage.getProductPrice();
	        System.out.println("Product Name: " + name);
	        System.out.println("Product Price: " + price);
	        
	        Assert.assertTrue(!name.isEmpty() && !price.isEmpty(), "Product details missing!");
	        test.log(LogStatus.PASS, "Test: Verify product details");
    	} catch (Exception e) {
            test.log(LogStatus.FAIL, "Verifying product details failed due to exception: " + e.getMessage());
            addScreenCapture("Failed_VerifyProductDetails", true);
            throw e;
    	} catch (AssertionError ae) {
			test.log(LogStatus.FAIL, "Verifying product details failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_VerifyProductDetails", true);
			throw ae;
    	}
    }

    @Test(priority = 7)
    public void takeScreenshot() throws IOException, InterruptedException {
    	try {
        	HomePage homePage = new HomePage(driver);
        	homePage.searchProduct("mobile");
        	Thread.sleep(2000);
        	File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        	File destFile = new File(System.getProperty("user.dir") + "\\screenshots\\" + "flipkart_search.png");
    	    Files.copy(srcFile, destFile);
    	    
    	    Assert.assertTrue(destFile.exists(), "Screenshot was not captured successfully!");
    	    test.log(LogStatus.PASS, "Test: Take screenshot of search results");
    	} catch (IOException e) {
			test.log(LogStatus.FAIL, "Taking screenshot failed due to exception: " + e.getMessage());
			addScreenCapture("Failed_TakeScreenshot", true);
			throw e;
    	} catch (AssertionError ae) {
    		test.log(LogStatus.FAIL, "Taking screenshot failed due to assertion error: " + ae.getMessage());
    	}
        
        
    }
    
    @Test(priority = 8)
    public void mouseHoverMenu() throws InterruptedException {
    	try {
	    	HomePage homePage = new HomePage(driver);
	    	homePage.hoverOnElectronics();
	        Thread.sleep(2000);
	    	addScreenCapture("MouseHover Electronics Menu", false);
	    	
	        Assert.assertTrue(homePage.isDropdownVisible(), "Electronics menu hover failed!");
	        test.log(LogStatus.PASS, "Test: Mouse hover on Electronics menu");
    	} catch (Exception e) {
	        test.log(LogStatus.FAIL, "Mouse hover on Electronics menu failed due to exception: " + e.getMessage());
	        addScreenCapture("Failed_MouseHoverElectronicsMenu", true);
	        throw e;
    	} catch (AssertionError ae) {
			test.log(LogStatus.FAIL,
					"Mouse hover on Electronics menu failed due to assertion error: " + ae.getMessage());
			addScreenCapture("Failed_MouseHoverElectronicsMenu", true);
			throw ae;
    	}
    }
    
    /**
     * Captures a screenshot and attaches it to the test report.
     *
     * @param screenshotName the desired name for the saved screenshot file
     */
    public void addScreenCapture(String screenshotName, boolean isFailure) {
    	String screenshotPath = captureScreenshot(screenshotName + System.currentTimeMillis());
		if (isFailure) {
			test.log(LogStatus.FAIL, "Screenshot on failure: "
					+ test.addScreenCapture(captureScreenshot(screenshotName + System.currentTimeMillis())));
		} else {
			test.log(LogStatus.INFO, screenshotName + " Screenshot: " + test.addScreenCapture(screenshotPath));
		}
    }

}
