package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features",   // Path to feature files
        glue = {"steps", "hooks"},             // Path to step definitions and hooks
        plugin = {
                "pretty",                                         // Cucumber pretty format
                "html:target/cucumber-reports/cucumber.html",     // HTML report
                "json:target/cucumber-reports/cucumber.json",     // JSON report
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure reporting plugin
                // "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Removed the ExtentCucumberAdapter plugin
        },
        monochrome = true,                               // Output console in a readable format
        tags = "@WhatsAppTest"                           // Tags to run specific tests
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios(); // Run scenarios in parallel
    }
}
