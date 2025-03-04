package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;


public class LoginPage extends BaseLibrary {

    // Locators
    private static final By EMAIL_INPUT = By.cssSelector("input[formcontrolname='email']\n");
    private static final By PASSWORD_INPUT = By.cssSelector("input[formcontrolname='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("button.ant-btn.ant-btn-primary\n");


    @Step("Başarılı bir şekilde klogin sayfasına yönlendirildi.")
    public LoginPage enterEmail(String email) {
        screenshot();
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));
            emailField.clear();
            emailField.sendKeys(email);
        } catch (Exception e) {
            Assert.fail("Login sayfasına yönlendirilemedi");
        }
        return this;
    }

    @Step("Kayıtlı e-posta ve şifre girildi")
    public LoginPage enterPassword(String password) throws InterruptedException {
        try {
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
            passwordField.clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            Assert.fail("Login sayfasına yönlendirilemedi");
        }
        screenshot();
        return this;
    }

    @Step("Giriş yap butonuna basıldı")
    public LoginPage clickLogin(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT));

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));

        String enteredEmail = emailField.getAttribute("value");
        String enteredPassword = passwordField.getAttribute("value");

        if (enteredEmail.equals(email) && enteredPassword.equals(password)) {
            System.out.println("Girilen e-posta ve şifre doğru!");
            wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();

        } else {
            if (!enteredEmail.equals(email)) {
                System.out.println("Girilen e-posta yanlış!");
            }
            if (!enteredPassword.equals(password)) {
                System.out.println("Girilen şifre yanlış!");
            }
        }
        return this;
    }
}
