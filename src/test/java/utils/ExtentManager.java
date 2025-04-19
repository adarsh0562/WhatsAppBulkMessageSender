package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extentReports;

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            setupReport();
        }
        return extentReports;
    }

    public static void setupReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/report/testng/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

        // Basic report config
        reporter.config().setDocumentTitle("WhatsApp Automation Report");
        reporter.config().setReportName("Adarsh Raj - WhatsApp Automation Testing");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setTimelineEnabled(true);

        // Copy custom logo to report folder
        try {
            Path source = Paths.get("src/test/resources/logo.png"); // Make sure logo exists here
            Path destination = Paths.get(System.getProperty("user.dir") + "/report/testng/logo.png");
            Files.createDirectories(destination.getParent());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to copy logo: " + e.getMessage());
        }

        // Inject custom logo (replaces default Extent logo)
        reporter.config().setJs(
                "document.querySelector('.logo').innerHTML = \"<img src='logo.png' style='height:53px;width:70px;padding: 0;'>\";"
        );

        // Create ExtentReports instance and attach reporter
        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Tester", "Adarsh Raj");
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Framework", "TestNG + Cucumber + Selenium");
    }
}
