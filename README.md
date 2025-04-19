
# 📲 WhatsApp Bulk Message Automation 🧪

Automate sending WhatsApp messages (including images 📸) to multiple contacts using a robust testing framework built with Selenium, TestNG, Cucumber, and integrated with Extent Reports & Allure for detailed reporting.

---

## 🚀 Project Overview

This automation framework streamlines the process of sending WhatsApp messages via **WhatsApp Web** using data from Excel sheets, including images. It ensures reliability, traceability, and a visually rich test execution report.

---

## 🛠️ Tech Stack & Tools

| Purpose                  | Technology/Tool                     |
|--------------------------|-------------------------------------|
| 👨‍💻 Language              | Java 21                             |
| 🌐 Browser Automation     | Selenium WebDriver                  |
| 🧪 Test Execution         | TestNG + Cucumber BDD               |
| 📊 Reporting             | Extent Reports, Allure              |
| 📁 Data Management       | Apache POI (Excel)                  |
| 🔧 Build Tool            | Maven                               |
| 🖥️ IDE                   | IntelliJ IDEA                       |
| 🛡️ Dependency Management | Maven `pom.xml`                     |
| 📷 Screenshots           | Selenium `TakesScreenshot`          |

---

## ✨ Features

- ✅ **Automated Login** to WhatsApp Web using QR Code
- ✅ **Read Messages & Images** from Excel
- ✅ **Send Text + Image** messages to multiple contacts
- ✅ **Cucumber BDD Test Flow**
- ✅ **Screenshots on Pass/Fail/Skip**
- ✅ **Real-Time Reports** with Extent & Allure
- ✅ **Thread-safe Driver & Report Management**
- ✅ **Dynamic Extent Report** with banner/logo
- ✅ **Custom logging and debugging options**
- ✅ **Session persistence and driver cleanup**

---

## ✅ Advantages

- 📄 Excel-driven test cases make maintenance easy.
- 📸 Screenshots embedded in reports help in debugging.
- 💡 Cucumber makes it readable and business-friendly.
- 🌐 Cross-browser support ready.
- 📈 Beautiful visual reports for stakeholders.

---

## ⚠️ Limitations

- 🔄 Requires WhatsApp Web session scanning every time (unless persisted).
- 🖼️ Images must be in a predefined folder.
- 🔐 Session management needs enhancement for long-term use.
- 🧪 TestNG & Cucumber dual usage might need careful runner setup.

---

## 📂 Project Structure

```
📦 WhatsApp-Bulk-Message-Sender
├── 📁 config
│   └── ConfigReader.java, DriverManager.java
├── 📁 features
│   └── SendWhatsAppMessages.feature
├── 📁 hooks
│   └── Hooks.java
├── 📁 listeners
│   └── ExtentTestNGListener.java
├── 📁 runner
│   └── TestRunner.java
├── 📁 steps
│   └── WhatsAppSteps.java
├── 📁 utils
│   └── ScreenshotUtil.java, ExtentManager.java, ExtentTestManager.java
├── 📁 test-data
│   └── contacts.xlsx
├── 📁 images
│   └── image1.jpg, image2.jpg
├── 📄 pom.xml
```

---

## 📸 Screenshots

> Add example screenshots of:
> - Extent Report UI
> - Allure Report dashboard
> - WhatsApp Web automation in progress

---

## 📦 How to Run

```bash
# Clone the repository
git clone https://github.com/<your-username>/WhatsApp-Bulk-Message-Sender.git
cd WhatsApp-Bulk-Message-Sender

# Run tests and generate report
mvn clean test allure:serve
```

---

## 🧑‍💻 Author

**Adarsh Raj**  
📧 rajadarsh711@gmail.com  
🔗 [LinkedIn](https://linkedin.com/in/adarsh-raj-0625951b2)  
🌐 [Portfolio](https://cloudians.netlify.app)

---

## 🌟 Show Your Support

If you find this project helpful, please give it a ⭐ on GitHub. Contributions, issues, and feature requests are welcome!



