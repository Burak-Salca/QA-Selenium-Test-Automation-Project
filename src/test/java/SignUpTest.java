import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Headers;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

public class SignUpTest {

    @Test
    public void signUpSuccessful() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Temel ayarlar
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Private Access Token ve Turnstile için ek ayarlar
        options.addArguments("--disable-features=IsolateOrigins,site-per-process,CrossSiteDocumentBlockingIfIsolating");
        options.addArguments("--flag-switches-begin");
        options.addArguments("--flag-switches-end");


        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://app.forceget.com/system/account/register");
        Thread.sleep(2500);

        // Benzersiz değerler oluştur
        String uniqueEmail = generateUniqueEmail();
        String uniquePhone = generateUniquePhone();

        // Form doldurma işlemleri
        driver.findElement(By.cssSelector("#firstName")).sendKeys("Burak");
        driver.findElement(By.cssSelector("#lastName")).sendKeys("Salça");
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > div.flex.custom-gap > nz-form-item.ant-form-item.ant-row.mb-0.min-w-100px > nz-form-control > div > div > nz-input-group > forceget-country-dropdown > nz-select > nz-select-top-control > nz-select-search > input")).sendKeys("+90");
        driver.findElement(By.cssSelector("#cdk-overlay-0 > nz-option-container > div > cdk-virtual-scroll-viewport > div.cdk-virtual-scroll-content-wrapper > nz-option-item > div > div")).click();

        // Benzersiz telefon numarası kullan
        driver.findElement(By.cssSelector("#phoneNumber")).sendKeys(uniquePhone);

        driver.findElement(By.cssSelector("#companyName")).sendKeys("Company");

        // Benzersiz email kullan
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > nz-form-item:nth-child(5) > nz-form-control > div > div > nz-input-group > input")).sendKeys("turquoise4287@edny.net");

        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[3]/nz-form-item/nz-form-control/div/div/nz-select/nz-select-top-control/nz-select-search/input")).sendKeys("Test Engineer");
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("#cdk-overlay-1 > nz-option-container > div > cdk-virtual-scroll-viewport > div.cdk-virtual-scroll-content-wrapper > nz-option-item > div")).click();
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[4]/nz-form-item/nz-form-control/div/div/nz-input-group/input")).sendKeys("Siyah.0699*");
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[5]/nz-form-item/nz-form-control/div/div/nz-input-group/input")).sendKeys("Siyah.0699*");
        driver.findElement(By.className("checkbox-box")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"cdk-overlay-2\"]/nz-modal-container/div/div/div[3]/div/button")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/button")).click();

        // Cloudflare doğrulaması için çerezleri yönet
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("Mevcut çerezler:");
        for (Cookie cookie : cookies) {
            // Tüm önemli çerezleri kontrol et
            if (cookie.getName().contains("cf_") ||
                cookie.getName().contains("__cf") ||
                cookie.getName().contains("_cfuvid") ||
                cookie.getName().contains("turnstile")) {
                try {
                    System.out.println("Eklenen çerez: " + cookie.getName());
                    driver.manage().addCookie(cookie);
                } catch (Exception e) {
                    System.out.println("Çerez eklenirken hata: " + cookie.getName());
                }
            }
        }

        // Sayfayı yenile

        Thread.sleep(2000);

        // Cloudflare doğrulamasını bekle ve kontrol et
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean challengeExists = !wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("[src*='challenges.cloudflare.com']")));

            if (challengeExists) {
                System.out.println("Cloudflare doğrulaması hala aktif");
            } else {
                System.out.println("Cloudflare doğrulaması başarıyla geçildi");
            }
        } catch (Exception e) {
            System.out.println("Cloudflare durumu kontrol edilirken hata oluştu");
        }
    }

    // Sınıfın başında tanımlanan yardımcı metodlar
    private String generateUniqueEmail() {
        long timestamp = System.currentTimeMillis();
        return "test.user." + timestamp + "@hotmail.com";
    }

    private String generateUniquePhone() {
        Random random = new Random();
        int phoneNumber = random.nextInt(900000) + 100000; // 100000 ile 999999 arası
        return "505" + phoneNumber; // 507 ile başlayan 9 haneli numara
    }

}