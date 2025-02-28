import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Guerrilla Mail üzerinden geçici e-posta oluştur ve SID token'ı al
        String uniqueEmail = generateRandomEmailFromGuerrillaMail();
        if(uniqueEmail == null) {
            System.out.println("E-posta oluşturulamadı, testi sonlandırıyoruz.");
            driver.quit();
            return;
        }
        String uniquePhone = generateUniquePhone();

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

        // Şimdi maili kontrol etmek için ayrı bir driver oluşturuyoruz
        ChromeDriver mailDriver = new ChromeDriver(options);
        mailDriver.get("https://www.guerrillamail.com/");
        Thread.sleep(3000); // Sayfanın tam yüklenmesi için bekleyelim





        // Cloudflare doğrulaması için çerezleri yönetme
        Set<Cookie> cookies = driver.manage().getCookies();
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

        // Guerrilla Mail üzerinden doğrulama kodunu çekme
        String verificationCode = getVerificationCodeFromGuerrillaMail();
        System.out.println("Alınan doğrulama kodu: " + verificationCode);

        // Test tamamlanmadan driver'ı kapatabilirsiniz.
        driver.quit();
    }

    // Guerrilla Mail API'si kullanarak geçici e-posta oluşturma
    private String generateRandomEmailFromGuerrillaMail() {
        try {
            URL url = new URL("https://api.guerrillamail.com/ajax.php?f=get_email_address");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("Guerrilla Mail Response code: " + responseCode);
            if (responseCode != 200) {
                throw new Exception("HTTP response code: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(content.toString());
            String email = jsonResponse.getString("email_addr");
            guerrillaSidToken = jsonResponse.getString("sid_token");
            System.out.println("Oluşturulan Guerrilla Mail email: " + email);
            System.out.println("SID Token: " + guerrillaSidToken);
            return email;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guerrilla Mail API'si ile gelen kutusunu kontrol edip doğrulama kodunu çeken metot
    private String getVerificationCodeFromGuerrillaMail() {
        try {
            int attempts = 0;
            while (attempts < 10) {
                // Gelen maillerin listesini al
                URL url = new URL("https://api.guerrillamail.com/ajax.php?f=get_email_list&offset=0&sid_token="
                        + guerrillaSidToken + "&seq=0");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                        + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                String response = content.toString().trim();
                // Yanıt geçerli JSON formatında mı kontrol edelim
                if(response.isEmpty() || !response.startsWith("{")){
                    System.out.println("Geçersiz get_email_list yanıtı: " + response);
                    attempts++;
                    Thread.sleep(5000);
                    continue;
                }

                JSONObject jsonResponse = new JSONObject(response);
                JSONArray emailList = jsonResponse.getJSONArray("list");
                if (emailList.length() > 0) {
                    // İlk maile ait bilgileri al
                    JSONObject firstEmail = emailList.getJSONObject(0);
                    int emailId = firstEmail.getInt("mail_id");

                    // Seçilen mailin detaylarını al
                    URL fetchUrl = new URL("https://api.guerrillamail.com/ajax.php?f=fetch_email&id="
                            + emailId + "&sid_token=" + guerrillaSidToken);
                    HttpURLConnection fetchConn = (HttpURLConnection) fetchUrl.openConnection();
                    fetchConn.setRequestMethod("GET");
                    fetchConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
                    fetchConn.setRequestProperty("Accept", "application/json");
                    fetchConn.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
                    fetchConn.setConnectTimeout(5000);
                    fetchConn.setReadTimeout(5000);

                    BufferedReader fetchIn = new BufferedReader(new InputStreamReader(fetchConn.getInputStream()));
                    StringBuilder fetchContent = new StringBuilder();
                    while ((inputLine = fetchIn.readLine()) != null) {
                        fetchContent.append(inputLine);
                    }
                    fetchIn.close();
                    fetchConn.disconnect();

                    String fetchResponse = fetchContent.toString().trim();
                    if(fetchResponse.isEmpty() || !fetchResponse.startsWith("{")){
                        System.out.println("Geçersiz fetch_email yanıtı: " + fetchResponse);
                        attempts++;
                        Thread.sleep(5000);
                        continue;
                    }

                    JSONObject emailDetail = new JSONObject(fetchResponse);
                    String body = emailDetail.getString("mail_body");
                    // Örneğin doğrulama kodu 6 haneli sayı ise:
                    Pattern pattern = Pattern.compile("(\\d{6})");
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                }
                attempts++;
                Thread.sleep(15000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Benzersiz telefon numarası oluşturma metodu
    private String generateUniquePhone() {
        Random random = new Random();
        int phoneNumber = random.nextInt(900000) + 100000;
        return "505" + phoneNumber;
    }
}
