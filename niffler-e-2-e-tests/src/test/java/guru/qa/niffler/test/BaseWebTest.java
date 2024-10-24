package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.WebTest;
import org.openqa.selenium.chrome.ChromeOptions;

@WebTest
public class BaseWebTest {
    static {
        System.setProperty("webdriver.chrome.driver", "/Users/dgkyzmichev/Desktop/drivers/chromedriver-mac-x64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--ignore-certificate-errors");
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }
}
