package pages;

import base.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Data {

    private final By emailInputLocator = By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item:nth-child(1) > nz-form-control > div > div > nz-input-group > input");

    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator)).sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String text) throws InterruptedException {
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item.ant-form-item.ant-row.relative > nz-form-control > div > div > nz-input-group > input")).sendKeys(text);
        Thread.sleep(2000);
        return this;
    }

    public LoginPage clickLogin(){
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > login1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > div > form > nz-form-item:nth-child(3) > nz-form-control > div > div > div > button"))
                .click();
        return this;
    }
}
