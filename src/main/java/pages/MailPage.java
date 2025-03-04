package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class MailPage extends BaseLibrary {

    // Locators
    private static final By PROFILE_INPUT = By.cssSelector("button[aria-label='Account']");
    private static final By LOGIN_POPUP_BUTTON = By.cssSelector("button[role='menuitem']:has(span.truncate):nth-of-type(2)");
    private static final By LOGIN_POPUP = By.xpath("//div[contains(@class, 'p-4 sm:p-6')]//h3[contains(text(), 'Log in to your account')]\n");
    private static final By EMAIL_INPUT = By.cssSelector("#v-1-15");
    private static final By PASSWORD_INPUT = By.cssSelector("#v-1-16");
    private static final By LOGIN_BUTTON = By.xpath("//button[contains(text(), 'Login')]");
    private static final By MAIL_MESSAGE = By.xpath("//div[contains(@class, 'truncate text-sm font-medium leading-5 text-indigo-600 dark:text-indigo-400')]");
    private static final By IFRAME = By.id("iFrameResizer0");
    private static final By OTP_ELEMENT = By.cssSelector("div[style*='text-align: center'] p");

    @Step("E-posta web tarayıcıda açıldı")
    public MailPage openMail() throws InterruptedException {
        mailDriver = new ChromeDriver();
        mailWait = new WebDriverWait(mailDriver, Duration.ofSeconds(10));
        mailDriver.get("https://mail.tm/en/");
        Thread.sleep(500);
        return this;
    }

    @Step("E-posta giriş sayfası açıldı")
    public MailPage openLoginMail() throws InterruptedException {
        try {
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_INPUT));
            mailWait.until(ExpectedConditions.elementToBeClickable(PROFILE_INPUT)).click();
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_POPUP_BUTTON));
            mailWait.until(ExpectedConditions.elementToBeClickable(LOGIN_POPUP_BUTTON)).click();
            screenshotMail();
        }catch (Exception e){
            Assert.fail("Mail login sayfası açılamadı");
        }
        return this;
    }

    @Step("E-posta alanı girildi")
    public MailPage enterMail(String text) {
        try {
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_POPUP));
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT)).sendKeys(text);
            screenshotMail();
        }catch (Exception e){
            Assert.fail("Login popup sayfasına e-posta dresi girilemedi");
        }
        return this;
    }

    @Step("Şifre alanı girildi")
    public MailPage enterPassword(String text) throws InterruptedException {
        mailWait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_POPUP));
        mailWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT)).sendKeys(text);
        screenshotMail();
        Thread.sleep(2000);
        return this;
    }

    @Step("Giriş yap butonuna basıldı")
    public MailPage clickLogin(String text) {
        WebElement passwordField = mailWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        String enteredPassword = passwordField.getAttribute("value");
        System.out.println(enteredPassword);
        if (enteredPassword.equals(text)) {
            mailWait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            mailWait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
            System.out.println("Mail login işlemi başarılı");
        } else {
            Assert.fail("Girilen şifre yanlış! Mail adresine giriş yapılamıyor");
        }
        return this;
    }

    @Step("Doğrulama e-posta alındı")
    public MailPage clickMessage() throws InterruptedException {
        WebElement element = mailWait.until(ExpectedConditions.visibilityOfElementLocated(MAIL_MESSAGE));
        screenshotMail();
        String actualText = element.getText();
        if (actualText.equals("FORCEGET")) {
            System.out.println("FORCEGET şirketinden gelen mail bulundu.");
            mailWait.until(ExpectedConditions.elementToBeClickable(MAIL_MESSAGE)).click();
        } else {
            Assert.fail("FROCEGET şirketinden gelen bir mail bulunamadı!");
        }
        return this;
    }

    @Step("Doğrulama e-postası açıldı")
    public String getVerifyCode() {
        String verifyCode = null;
        try {
            WebElement iframeElement = mailWait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME));
            mailDriver.switchTo().frame(iframeElement);
            WebElement otpElement = mailWait.until(ExpectedConditions.visibilityOfElementLocated(OTP_ELEMENT));
            screenshotMail();
            verifyCode = otpElement.getText().trim();
            System.out.println("Ayıklanan doğrulama kodu: " + verifyCode);
            mailDriver.quit();
        } catch (Exception e) {
            Assert.fail("Doğrulama kodu bulunamadı.");
        }
        return verifyCode;
    }
}
