package base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * BaseClass provides common test setup and teardown utilities: - Initializes
 */
public class BaseClass {
	public WebDriver driver;
    protected static ExtentTest test;
    protected static ExtentReports report;
	
	/**
	 * Test setup executed once before any tests in the class. - Sets geckodriver
	 * system property. - Creates a FirefoxDriver, maximizes window, sets implicit
	 * wait. - Navigates to Flipkart homepage.
	 */
	@BeforeClass
    public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER_PATH"));
		driver = new ChromeDriver();
    	
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        String reportPath = System.getProperty("user.dir") + "\\FlipkartReportResults.html";
		report = new ExtentReports(reportPath);
		 report.addSystemInfo("Host Name", "Localhost")
         .addSystemInfo("Environment", "QA")
         .addSystemInfo("User Name", "Nimisha");
		test = report.startTest("FlipkartTests");
    }

	/** Test teardown executed once after all tests in the class.
     * 
     */
    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
		if (driver != null) {
            try {
                driver.quit();
            } finally {
                driver = null;
            }
        }
		report.endTest(test);
		report.flush(); // Writes everything to HTML
    }
	
	/**
     * Captures a screenshot and saves it under the `screenshots` folder.
     *
     * Returns the absolute path to the saved PNG file.
     * In case of an IOException the stack trace is printed and the method still returns the intended path.
     *
     * @param testName name used to form the screenshot filename (e.g. test method name)
     * @return destinationPath absolute path where the screenshot was saved
     */
    public String captureScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") + "\\screenshots\\" + testName + ".png";
        try {
			Files.copy(source.toPath(), Paths.get(destinationPath));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
        return destinationPath; // return screenshot path
    }
}
