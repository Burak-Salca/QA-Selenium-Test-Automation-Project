package pages;

import base.Data;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MailPage extends Data {

    public static WebDriver mailDriver;
    public static WebDriverWait mailWait;

    @Step("E-posta web tarayıcıda açıldı")
    public MailPage openMail(){
        mailDriver = new ChromeDriver();
        mailWait = new WebDriverWait(mailDriver, Duration.ofSeconds(10));
        mailDriver.get("https://mail.tm/en/");
        return this;
    }

    @Step("E-posta giriş sayfası açıldı")
    public void openLoginMail() throws InterruptedException {
        mailDriver.findElement(By.cssSelector("#__nuxt > div.h-screen.flex.overflow-hidden.bg-gray-100.antialiased.dark\\:bg-gray-900 > div.w-0.flex.flex-1.flex-col.overflow-hidden > div > div > div.flex.items-center.md\\:ml-6.sm\\:ml-4 > button.rounded-\\[calc\\(var\\(--ui-radius\\)\\*1\\.5\\)\\].font-medium.inline-flex.items-center.focus\\:outline-hidden.disabled\\:cursor-not-allowed.aria-disabled\\:cursor-not-allowed.disabled\\:opacity-75.aria-disabled\\:opacity-75.transition-colors.px-2\\.5.py-1\\.5.text-sm.gap-1\\.5.text-\\(--ui-primary\\).hover\\:text-\\(--ui-primary\\)\\/75.disabled\\:text-\\(--ui-primary\\).aria-disabled\\:text-\\(--ui-primary\\).focus-visible\\:ring-2.focus-visible\\:ring-inset.focus-visible\\:ring-\\(--ui-primary\\).group.flex-1.justify-between > span")).click();
        Thread.sleep(200);
        mailDriver.findElement(By.xpath("//*[@id=\"reka-dropdown-menu-content-v-1-9\"]/div[2]/button[2]")).click();
    }

    @Step("E-posta alanı girildi")
    public MailPage enterMail(String text){
        mailDriver.findElement(By.cssSelector("#v-1-15")).sendKeys(text);
        return this;
    }

    @Step("Şifre alanı girildi")
    public MailPage enterPassword(String text) throws InterruptedException {
        Thread.sleep(500);
        mailDriver.findElement(By.xpath("//*[@id=\"v-1-16\"]")).sendKeys(text);
        Thread.sleep(1000);
        return this;
    }

    @Step("Giriş yap butonuna basıldı")
    public MailPage clickLogin(){
        mailDriver.findElement(By.cssSelector("body > div.fixed.bg-\\(--ui-bg\\).divide-y.divide-\\(--ui-border\\).flex.flex-col.focus\\:outline-none.data-\\[state\\=open\\]\\:animate-\\[scale-in_200ms_ease-out\\].data-\\[state\\=closed\\]\\:animate-\\[scale-out_200ms_ease-in\\].top-1\\/2.left-1\\/2.-translate-x-1\\/2.-translate-y-1\\/2.w-\\[calc\\(100vw-2rem\\)\\].max-w-lg.max-h-\\[calc\\(100vh-2rem\\)\\].sm\\:max-h-\\[calc\\(100vh-4rem\\)\\].rounded-\\[calc\\(var\\(--ui-radius\\)\\*2\\)\\].shadow-lg.ring.ring-\\(--ui-border\\) > div > div > div.mt-5.sm\\:grid.sm\\:grid-flow-row-dense.sm\\:grid-cols-2.sm\\:mt-8.sm\\:gap-3 > span.w-full.flex.rounded-md.shadow-sm.sm\\:col-start-2 > button")).click();
        return this;
    }

    @Step("Doğrulama e-posta alındı")
    public MailPage clickMessage() throws InterruptedException {
        mailDriver.findElement(By.xpath("//*[@id=\"__nuxt\"]/div[1]/div[2]/main/div[2]/div[2]/ul/li/a/div")).click();
        Thread.sleep(2000);
        return this;
    }

    public String getVerifyCode(){
        // İlgili iframe'i locate edip geçiş yapıyoruz
        WebElement iframeElement = mailWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("iFrameResizer0")));
        mailDriver.switchTo().frame(iframeElement);

        // OTP elementini locate edin; burada, div içinde p etiketini arıyoruz
        WebElement otpElement = mailWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[style*='text-align: center'] p")));
        String otp = otpElement.getText().trim();
        System.out.println("Ayıklanan OTP: " + otp);

        return otp;
    }

}
