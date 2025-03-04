package pages;


import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MainPage extends BaseLibrary {

    private static final By WELCOME_MESSAGE = By.xpath("//h4[contains(text(),'Welcome')]");

    @Step("Başarılı giriş yapıldı. Ana sayfaya yönlendirildi")
    public void isLoginSuccessful() {
        try {
            WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(WELCOME_MESSAGE));
            screenshot();
            Assert.assertTrue(welcomeElement.isDisplayed(), "Welcome mesajı görünmüyor. Ana sayfaya giriş yapılamadı.");
        } catch (Exception e) {
            Assert.fail("Ana sayfaya giriş yapılamadı: " + e.getMessage());
        }
    }
}
