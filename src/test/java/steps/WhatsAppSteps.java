package steps;

import config.ConfigReader;
import config.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.WhatsAppPage;
import utils.ExcelUtils;
import utils.FileUtils;
import utils.ScreenshotUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WhatsAppSteps {

    private final WebDriver driver;
    private final WhatsAppPage whatsAppPage;

    public WhatsAppSteps() {
        this.driver = DriverManager.initializeDriver();
        this.whatsAppPage = new WhatsAppPage(driver);
    }

    @Given("User is logged into WhatsApp Web")
    public void userLogsIntoWhatsApp() {
        whatsAppPage.openWhatsAppWeb();
        //whatsAppPage.waitForLogin();
        try{
            Thread.sleep(6000);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        ScreenshotUtil.logStepWithScreenshot("User is logged into WhatsApp Web", driver);
    }

    @When("User sends messages with images from Excel file")
    public void userSendsMessagesWithImages() {
        List<String[]> contacts = ExcelUtils.getContacts();
        String messageTemplate = FileUtils.readFile(System.getProperty("user.dir") + ConfigReader.get("MESSAGE_FILE_PATH"));

        if (messageTemplate.isEmpty()) {
            System.out.println("⚠️ Error: Message template file is empty or not found.");
            return;
        }

        for (String[] contact : contacts) {
            int rowNum = Integer.parseInt(contact[0]);
            String name = contact[1];
            String number = contact[2];

            String personalizedMessage = messageTemplate.replace("{{Name}}", name);
            String encodedMessage = encodeForWhatsApp(personalizedMessage);

            boolean status = whatsAppPage.sendMessageWithImage(
                    number, encodedMessage,
                    System.getProperty("user.dir") + ConfigReader.get("BANNER_PATH")
            );

            ExcelUtils.updateStatus(rowNum, status ? "Sent" : "Failed", status ? "green" : "red");
        }

        ExcelUtils.saveChanges();
        ScreenshotUtil.logStepWithScreenshot("User sends messages with images from Excel file", driver);
    }

    private String encodeForWhatsApp(String message) {
        try {
            return URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }
}
