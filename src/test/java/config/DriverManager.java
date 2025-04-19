package config;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver initializeDriver() {
        if (driverThreadLocal.get() == null) {
            String browserName = System.getProperty("browser") != null
                    ? System.getProperty("browser")
                    : ConfigReader.get("BROWSER");

            System.out.println("Launching browser: " + browserName);

            WebDriver driver;

            switch (browserName.toLowerCase()) {
                case "chrome":
                case "chromeheadless":
                    System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");

                    // ✅ Chrome persistent user-data directory
                    String chromeUserDataDir = Paths.get(System.getProperty("user.dir"), "chrome-user-data").toString();
                    File chromeProfile = new File(chromeUserDataDir);
                    chromeProfile.mkdirs(); // Create if not exists

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("user-data-dir=" + chromeUserDataDir);
                    chromeOptions.addArguments("--remote-allow-origins=*");

                    if (browserName.equalsIgnoreCase("chromeheadless")) {
                        chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                    }

                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                case "firefoxheadless":
                    System.setProperty("webdriver.gecko.driver", "C:\\driver\\geckodriver.exe");

                    // ✅ Firefox persistent profile
                    String firefoxProfilePath = Paths.get(System.getProperty("user.dir"), "firefox-user-data").toString();
                    File firefoxProfileFolder = new File(firefoxProfilePath);
                    firefoxProfileFolder.mkdirs(); // Create if not exists

                    FirefoxProfile firefoxProfile = new FirefoxProfile(firefoxProfileFolder);
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setProfile(firefoxProfile);

                    if (browserName.equalsIgnoreCase("firefoxheadless")) {
                        firefoxOptions.addArguments("--headless");
                    }

                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                case "edgeheadless":
                    System.setProperty("webdriver.edge.driver", "C:\\driver\\msedgedriver.exe");

                    // ✅ Edge persistent user-data directory
                    String edgeUserDataDir = Paths.get(System.getProperty("user.dir"), "edge-user-data").toString();
                    File edgeProfile = new File(edgeUserDataDir);
                    edgeProfile.mkdirs(); // Create if not exists

                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("user-data-dir=" + edgeUserDataDir);

                    if (browserName.equalsIgnoreCase("edgeheadless")) {
                        edgeOptions.addArguments("--headless", "--disable-gpu");
                    }

                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("❌ Unsupported browser: " + browserName);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driverThreadLocal.set(driver);
        }

        return driverThreadLocal.get();
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
