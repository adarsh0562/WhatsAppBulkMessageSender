package hooks;

import com.aventstack.extentreports.ExtentTest;
import config.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class Hooks {

    private static ExtentTest test;

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        // Automatically initializes ExtentReports if null
        test = ExtentManager.getExtentReports().createTest(scenario.getName());
        ExtentTestManager.setTest(test);
    }

    @Before(order = 2)
    public void setUp() {
        DriverManager.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed()) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName(), driver);
            ScreenshotUtil.logStepWithScreenshot("Scenario Failed: " + scenario.getName(), driver);
            scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", "Failure Screenshot");
        }

        DriverManager.quitDriver();
        ExtentTestManager.removeTest();
    }

    @AfterAll
    public static void afterAll() {
        if (ExtentManager.getExtentReports() != null) {
            ExtentManager.getExtentReports().flush();
        }
    }
}
