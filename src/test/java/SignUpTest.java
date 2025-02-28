import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SignUpTest {

    // Guerrilla Mail için SID token bilgisini saklamak için
    private static String guerrillaSidToken;

    @Test
    public void signUpSuccessful() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-features=IsolateOrigins,site-per-process,CrossSiteDocumentBlockingIfIsolating");
        options.addArguments("--flag-switches-begin");
        options.addArguments("--flag-switches-end");

        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://app.forceget.com/system/account/register");
        Thread.sleep(2500);

        String uniquePhone = generateUniquePhone();
        String uniqueEmailBody = generateUniqueEmailBody();
        String uniqueEmail = genarteUniqueEmail();
        String uniqueEmailPassword = generateRandomEmailPassword();

        createMailTmAccount(uniqueEmail,uniqueEmailPassword);

        // Form doldurma işlemleri
        driver.findElement(By.cssSelector("#firstName")).sendKeys("Burak");
        driver.findElement(By.cssSelector("#lastName")).sendKeys("Salça");
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > div.flex.custom-gap > nz-form-item.ant-form-item.ant-row.mb-0.min-w-100px > nz-form-control > div > div > nz-input-group > forceget-country-dropdown > nz-select > nz-select-top-control > nz-select-search > input"))
                .sendKeys("+90");
        driver.findElement(By.cssSelector("#cdk-overlay-0 > nz-option-container > div > cdk-virtual-scroll-viewport > div.cdk-virtual-scroll-content-wrapper > nz-option-item > div > div"))
                .click();

        driver.findElement(By.cssSelector("#phoneNumber")).sendKeys(uniquePhone);
        driver.findElement(By.cssSelector("#companyName")).sendKeys("Company");

        // Guerrilla Mail adresini formda kullanıyoruz
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > nz-form-item:nth-child(5) > nz-form-control > div > div > nz-input-group > input"))
                .sendKeys(uniqueEmail);

        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[3]/nz-form-item/nz-form-control/div/div/nz-select/nz-select-top-control/nz-select-search/input"))
                .sendKeys("Test Engineer");
        Thread.sleep(1500);
        driver.findElement(By.cssSelector("#cdk-overlay-1 > nz-option-container > div > cdk-virtual-scroll-viewport > div.cdk-virtual-scroll-content-wrapper > nz-option-item > div"))
                .click();
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[4]/nz-form-item/nz-form-control/div/div/nz-input-group/input"))
                .sendKeys("Siyah.0699*");
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[5]/nz-form-item/nz-form-control/div/div/nz-input-group/input"))
                .sendKeys("Siyah.0699*");
        driver.findElement(By.className("checkbox-box")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"cdk-overlay-2\"]/nz-modal-container/div/div/div[3]/div/button"))
                .click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/button"))
                .click();

        WebDriverWait overlayWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        overlayWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#cdk-overlay-3 > nz-modal-container > div > div > div")));
        System.out.println("Verify penceresi açıldı!");

        // Yeni bir tarayıcı penceresi açıp mail.tm sitesine gitmek:
        ChromeDriver mailDriver = new ChromeDriver(options);
        mailDriver.get("https://mail.tm/en/");

        Thread.sleep(500);

        mailDriver.findElement(By.cssSelector("#__nuxt > div.h-screen.flex.overflow-hidden.bg-gray-100.antialiased.dark\\:bg-gray-900 > div.w-0.flex.flex-1.flex-col.overflow-hidden > div > div > div.flex.items-center.md\\:ml-6.sm\\:ml-4 > button.rounded-\\[calc\\(var\\(--ui-radius\\)\\*1\\.5\\)\\].font-medium.inline-flex.items-center.focus\\:outline-hidden.disabled\\:cursor-not-allowed.aria-disabled\\:cursor-not-allowed.disabled\\:opacity-75.aria-disabled\\:opacity-75.transition-colors.px-2\\.5.py-1\\.5.text-sm.gap-1\\.5.text-\\(--ui-primary\\).hover\\:text-\\(--ui-primary\\)\\/75.disabled\\:text-\\(--ui-primary\\).aria-disabled\\:text-\\(--ui-primary\\).focus-visible\\:ring-2.focus-visible\\:ring-inset.focus-visible\\:ring-\\(--ui-primary\\).group.flex-1.justify-between > span")).click();
        mailDriver.findElement(By.xpath("//*[@id=\"reka-dropdown-menu-content-v-1-9\"]/div[2]/button[2]")).click();
        Thread.sleep(2000);
        mailDriver.findElement(By.xpath("/html/body/div[8]/div/div/div[1]/form/div[1]/div[2]/div/input")).sendKeys(uniqueEmail);
        mailDriver.findElement(By.xpath("//*[@id=\"v-1-16\"]")).sendKeys(uniqueEmailPassword);
        Thread.sleep(2000);
        mailDriver.findElement(By.xpath("/html/body/div[8]/div/div/div[2]/span[1]/button")).click();
        Thread.sleep(1000);
        mailDriver.findElement(By.xpath("//*[@id=\"__nuxt\"]/div[1]/div[2]/main/div[2]/div[2]/ul/li/a/div")).click();
        Thread.sleep(10000);
        WebElement otpElement = mailDriver.findElement(By.xpath("/html/body/center/div/table/tbody/tr/td[2]/table/tbody/tr/td/div/table/tbody/tr[2]/td/div/table/tbody/tr/td/div/div/span/font/span/span/div/p"));
        System.out.println("OTP Kodu: " + otpElement.getText().trim());


        // Cloudflare doğrulaması için çerezleri yönetme
        /*Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("Mevcut çerezler:");
        for (Cookie cookie : cookies) {
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
        Thread.sleep(2000);

        try {
            boolean challengeExists = !wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("[src*='challenges.cloudflare.com']")));
            if (challengeExists) {
                System.out.println("Cloudflare doğrulaması hala aktif");
            } else {
                System.out.println("Cloudflare doğrulaması başarıyla geçildi");
            }
        } catch (Exception e) {
            System.out.println("Cloudflare durumu kontrol edilirken hata oluştu");
        }*/
    }

    private String generateUniqueEmailBody() {
        long timestamp = System.currentTimeMillis();
        String userName = generateRandomEmailPassword();
        return userName + timestamp;
    }

    private String genarteUniqueEmail(){
        String emailBody = generateUniqueEmailBody();
        return emailBody + "@edny.net";
    }

    private String generateRandomEmailPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = "";
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password += chars.charAt(index);
        }
        return password;
    }

    private String generateUniquePhone() {
        Random random = new Random();
        int phoneNumber = random.nextInt(900000) + 100000;
        return "505" + phoneNumber;
    }

    public static void createMailTmAccount(String email, String password) {
        try {

            URL url = new URL("https://api.mail.tm/accounts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject payload = new JSONObject();
            payload.put("address", email);
            payload.put("password", password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 201 && responseCode != 200) {
                System.out.println("Hesap oluşturulamadı. HTTP Response Code: " + responseCode);
                BufferedReader errIn = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errContent = new StringBuilder();
                String line;
                while ((line = errIn.readLine()) != null) {
                    errContent.append(line);
                }
                errIn.close();
                System.out.println("Error: " + errContent.toString());
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseContent = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(responseContent.toString());
            System.out.println("Hesap oluşturuldu: " + jsonResponse);
            System.out.println("Oluşturulan mail: " + email);
            System.out.println("Oluşturulan şifre: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
