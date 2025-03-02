package pages;

import base.BaseLibrary;
import base.Data;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BaseLibrary {

    private final By emailInputLocator = By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item:nth-child(1) > nz-form-control > div > div > nz-input-group > input");

    @Step("Başarılı bir şekilde kayıt olundu. Giriş sayfasına yönlendirildi")
    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator)).sendKeys(email);
        return this;
    }

    @Step("Kayıtlı e-posta ve şifre girildi")
    public LoginPage enterPassword(String text) throws InterruptedException {
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item.ant-form-item.ant-row.relative > nz-form-control > div > div > nz-input-group > input")).sendKeys(text);
        Thread.sleep(2000);
        screenshot();
        return this;
    }

    @Step("Giriş yap butonuna basıldı")
    public LoginPage clickLogin(){
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item:nth-child(3) > nz-form-control > div > div > div > button"))
                .click();
        return this;
    }
}
