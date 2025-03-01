package pages;


import base.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MainPage extends Data {

    public void isLoginSuccessful() {
        try {
            WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h4[contains(text(),'Welcome')]")
            ));
            Assert.assertTrue(welcomeElement.isDisplayed(), "Ana sayfaya giriş yapıldı");

        } catch (Exception e) {
            Assert.fail("Ana sayfaya giriş yapılamadı: " + e.getMessage());
        }
    }
}
