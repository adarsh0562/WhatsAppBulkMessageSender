package pages;

import config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class WhatsAppPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//button[@title=\"Attach\"]") WebElement attachButton;
    @FindBy(xpath = "//span[text()=\"Photos & videos\"]") WebElement imageOption;

    By attachButtonBY = By.xpath("//button[@title=\"Attach\"]");
    By imageOptionBY = By.xpath("//span[text()=\"Photos & videos\"]");
    By messageInputBox = By.xpath("//div[@aria-label='Type a message']");

    // ✅ WebDriver now comes from Hooks via DriverManager
    public WhatsAppPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openWhatsAppWeb() {
        driver.get(ConfigReader.get("WHATSAPP_BASE_URL"));
    }

    public boolean sendMessage(String number, String message) {
        String url = ConfigReader.get("WHATSAPP_BASE_URL") + "/send?phone=91" + number + "&text=" + message;
        driver.get(url);
        try {
            Thread.sleep(5000);
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendMessageWithImage(String number, String message, String imagePath) {
        String url = ConfigReader.get("WHATSAPP_BASE_URL") + "/send?phone=91" + number + "&text=" + message;
        driver.get(url);
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(messageInputBox));
        try {
            Thread.sleep(3000);

            // Click on the attachment button
            this.wait.until(ExpectedConditions.elementToBeClickable(attachButtonBY));
            attachButton.click();
            //Thread.sleep(2000);

            // Click on "Photos & Videos"
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(imageOptionBY));
            imageOption.click();
            //Thread.sleep(5000);

            // Upload the image using Robot class
            uploadFile(imagePath);
            //Thread.sleep(3000);

            // Send the message
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(5000);

            return true;
        } catch (Exception e) {
            System.out.println("Error sending message with image: " + e.getMessage());
            return false;
        }
    }

    private void uploadFile(String filePath) throws AWTException {
        Robot robot = new Robot();
        robot.delay(3000);

        // Copy file path to clipboard
        StringSelection selection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

        // Paste (CTRL + V)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(2000);

        // Press Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);
    }

    public void waitForLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("canvas[aria-label='Scan me!']")));
    }
}
