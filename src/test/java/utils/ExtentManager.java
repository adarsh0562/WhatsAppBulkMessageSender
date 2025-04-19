package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        String reportPath = System.getProperty("user.dir")+"//report//testng//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

        reporter.config().setDocumentTitle("WhatsApp Automation Report");
        reporter.config().setReportName("Adarsh Raj - WhatsApp Automation Testing");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setTimelineEnabled(true);

        // Use relative path for logo stored in the report/assets folder
        String logoRelativePath = System.getProperty("user.dir")+"//report//testng//logo.png";

        // Improved logo and title layout with left-aligned logo and clean styling
        String titleWithLogo = "<div style='display: flex; align-items: center;'>" +
                            "<img src='" + logoRelativePath + "' style='width: 100px; height: 100px; margin-right: 15px;'/>" +
                            "<span style='font-size: 22px; font-weight: bold; color: white;'>Adarsh Raj - WhatsApp Automation Report</span>" +
                            "</div>";

        
        //String titleWithLogo = "<img src='" + logoRelativePath + "' style='max-width: 150px; height: 150px;'/> Adarsh Raj - WhatsApp Automation Report";
        reporter.config().setReportName(titleWithLogo);

        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Tester", "Adarsh Raj");
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Framework", "TestNG + Cucumber + Selenium");
    }
}
