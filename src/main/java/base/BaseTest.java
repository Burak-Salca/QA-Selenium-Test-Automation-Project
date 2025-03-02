package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest extends BaseLibrary {

    @BeforeMethod(description = "Web taraıcı açıldı. Siteye yönlendirildi")
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-features=IsolateOrigins,site-per-process,CrossSiteDocumentBlockingIfIsolating");
        options.addArguments("--flag-switches-begin");
        options.addArguments("--flag-switches-end");
        createMailTmAccount(uniqueEmail,uniqueEmailPassword);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);

    }

    @AfterMethod(description = "Web tarayıcı kapandı")
    public void afterTest(){
        driver.quit();
    }
}
