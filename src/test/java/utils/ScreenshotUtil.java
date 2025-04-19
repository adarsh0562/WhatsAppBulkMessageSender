package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.ExtentTestManager.getTest;

public class ScreenshotUtil {

    public static String captureScreenshot(String testName, WebDriver driver) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        Path screenshotDir = Paths.get(System.getProperty("user.dir"), "report", "screenshots");

        Path screenshotPath = screenshotDir.resolve(fileName);

        try {
            Files.createDirectories(screenshotDir);
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), screenshotPath);
            return screenshotPath.toAbsolutePath().toString(); // Return absolute path for ExtentReports
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void logStepWithScreenshot(String stepDescription, WebDriver driver) {
        String path = captureScreenshot("Step_" + System.currentTimeMillis(), driver);
        if (path != null) {
            getTest().info(stepDescription).addScreenCaptureFromPath(path);
        } else {
            getTest().warning("Screenshot capture failed for step: " + stepDescription);
        }
    }
}
