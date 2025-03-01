package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class RegisterPage extends BaseTest {

    public RegisterPage enterFirstName(String text){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#firstName"))).sendKeys(text);
        return this;
    }

    public RegisterPage enterLastName(String text){
        driver.findElement(By.cssSelector("#lastName")).sendKeys(text);
        return this;
    }

    public RegisterPage enterCountryCode(String text){
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > div.flex.custom-gap > nz-form-item.ant-form-item.ant-row.mb-0.min-w-100px > nz-form-control > div > div > nz-input-group > forceget-country-dropdown > nz-select > nz-select-top-control > nz-select-search > input"))
                .sendKeys(text);
        return this;
    }

    public RegisterPage clickCountryCode(){
        driver.findElement(By.xpath("//*[@id=\"cdk-overlay-0\"]/nz-option-container/div/cdk-virtual-scroll-viewport/div[1]/nz-option-item/div/div"))
                .click();
        return this;
    }

    public RegisterPage enterPhoneNumber(String text){
        driver.findElement(By.cssSelector("#phoneNumber")).sendKeys(text);
        return this;
    }

    public RegisterPage enterCompany(String text){
        driver.findElement(By.cssSelector("#companyName")).sendKeys(text);
        return this;
    }

    public RegisterPage enterEmail(String text){
        driver.findElement(By.cssSelector("body > app-root > app-full-layout > sign-up1 > div > div.flex-1.flex.items-center.justify-center.main-content-form > perfect-scrollbar > div > div.ps-content > div > form > nz-form-item:nth-child(5) > nz-form-control > div > div > nz-input-group > input"))
                .sendKeys(text);
        return this;
    }

    public RegisterPage enterTitle(String text){
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[3]/nz-form-item/nz-form-control/div/div/nz-select/nz-select-top-control/nz-select-search/input"))
                .sendKeys(text);
        return this;
    }

    public RegisterPage clickTitle(){
        driver.findElement(By.cssSelector("#cdk-overlay-1 > nz-option-container > div > cdk-virtual-scroll-viewport > div.cdk-virtual-scroll-content-wrapper > nz-option-item > div"))
                .click();
        return this;
    }

    public RegisterPage enterFirstPassword(String text){
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[4]/nz-form-item/nz-form-control/div/div/nz-input-group/input"))
                .sendKeys(text);
        return this;
    }

    public RegisterPage enterSecondPassword(String text){
        driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/form/div[5]/nz-form-item/nz-form-control/div/div/nz-input-group/input"))
                .sendKeys(text);
        return this;
    }

    public void clickCheckBox(){
        driver.findElement(By.className("checkbox-box")).click();
    }

    public void clickAccept(){
        driver.findElement(By.cssSelector("#cdk-overlay-2 > nz-modal-container > div > div > div.ant-modal-footer.ng-tns-c2116847144-12.ng-star-inserted > div > button > span"))
                .click();
    }

    public void clickSubmit() {
        WebElement submitButton = driver.findElement(By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/button"));
        if(!submitButton.isEnabled()){
            Assert.fail("Form eksik ya da yanlış dolduruldu");
        }
        else{
            submitButton.click();
        }
    }

    public void enterVerifyCode(String otp){
        List<WebElement> otpInputs = driver.findElements(By.cssSelector("div[formarrayname='otp'] input"));
        // OTP string'ini karakter karakter inputlara gönderelim:
        for (int i = 0; i < otp.length() && i < otpInputs.size(); i++) {
            otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

}
