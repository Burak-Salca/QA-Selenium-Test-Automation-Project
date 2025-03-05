package pages;

import base.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;


public class RegisterPage extends BaseTest {


    private static final By FIRST_NAME_INPUT = By.cssSelector("#firstName");
    private static final By LAST_NAME_INPUT = By.cssSelector("#lastName");
    private static final By COUNTRY_CODE_INPUT = By.cssSelector("input.ant-select-selection-search-input");
    private static final By COUNTRY_CODE_OPTION = By.cssSelector("span[_ngcontent-ng-c3869635075]");
    private static final By PHONE_NUMBER_INPUT = By.cssSelector("#phoneNumber");
    private static final By COMPANY_INPUT = By.cssSelector("#companyName");
    private static final By EMAIL_INPUT = By.cssSelector("input[formcontrolname='email']");
    private static final By TITLE_INPUT = By.cssSelector("nz-select[formcontrolname='jobTitle'] input.ant-select-selection-search-input");
    private static final By TITLE_OPTION = By.cssSelector("div.ant-select-item-option-content");
    private static final By FIRST_PASSWORD_INPUT = By.cssSelector("input[formcontrolname='password']");
    private static final By SECOND_PASSWORD_INPUT = By.cssSelector("input[formcontrolname='passwordConfirm']");
    private static final By CHECKBOX = By.className("checkbox-box");
    private static final By ACCEPT_BUTTON = By.cssSelector("nz-modal-container div.ant-modal-footer button.ant-btn.ant-btn-primary");
    private static final By SUBMIT_BUTTON = By.xpath("/html/body/app-root/app-full-layout/sign-up1/div/div[1]/perfect-scrollbar/div/div[1]/div/button");
    private static final By OTP_INPUTS = By.cssSelector("div[formarrayname='otp'] input");
    private static final By SIGN_IN_LINK = By.xpath("//a[contains(text(), 'Sign In')]");

    @Step("First name girildi")
    public RegisterPage enterFirstName(String text) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_INPUT)).sendKeys(text);
        }catch (Exception e){
            Assert.fail("Sayfa yüklenmedi. Tekrar deneyin.");
        }
        return this;
    }

    @Step("Last name girildi")
    public RegisterPage enterLastName(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME_INPUT)).sendKeys(text);
        return this;
    }

    @Step("Telefon için ülke kodu girildi")
    public RegisterPage enterCountryCode(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_CODE_INPUT)).sendKeys(text);
        WebElement countryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_CODE_OPTION));
        String actualText = countryElement.getText();
        if (actualText.contains(text)) {
            countryElement.click();
        }
        else{
            Assert.fail("Yanlış ülke kodu girilmiştir.");
        }
        return this;
    }

    @Step("Benzersiz telefon numarası girildi")
    public RegisterPage enterPhoneNumber(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PHONE_NUMBER_INPUT)).sendKeys(text);
        return this;
    }

    @Step("Şirket ismi girildi")
    public RegisterPage enterCompany(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(COMPANY_INPUT)).sendKeys(text);
        return this;
    }

    @Step("E-posta adresi girildi")
    public RegisterPage enterEmail(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT)).sendKeys(text);
        return this;
    }

    @Step("Ünvan girildi")
    public RegisterPage enterTitle(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TITLE_INPUT)).sendKeys(text);
        try {
            boolean isTitleCorrect = wait.until(ExpectedConditions.textToBePresentInElementLocated(TITLE_OPTION, text));
            Assert.assertTrue(isTitleCorrect, "İstenilen ünvan seçilemedi");
            WebElement titleOption = wait.until(ExpectedConditions.elementToBeClickable(TITLE_OPTION));
            titleOption.click();
        } catch (Exception e) {
            Assert.fail("İstenilen ünvan seçilemedi");
        }
        return this;
    }

    @Step("Şifre oluşturuldu")
    public RegisterPage enterFirstPassword(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_PASSWORD_INPUT)).sendKeys(text);
        return this;
    }

    @Step("Oluşturulan şifre tekrar girildi")
    public RegisterPage enterSecondPassword(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SECOND_PASSWORD_INPUT)).sendKeys(text);
        return this;
    }

    @Step("Hizmet şartları ve gizlilik politikası okundu")
    public RegisterPage clickCheckBox() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(CHECKBOX)).click();
        Thread.sleep(1500);
        return this;
    }

    @Step("Hizmet şartları ve gizlilik politikası onaylandı")
    public RegisterPage clickAccept() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_BUTTON)).click();
        Thread.sleep(1500);
        return this;
    }

    @Step("Bütün form elemanları dolduruldu. Kayıt ol butonuna basıldı. Verify kodu yollandı")
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
        screenshot();
    }

    @Step("Onay kodu girildi")
    public void enterVerifyCode(String otp) {
        List<WebElement> otpInputs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(OTP_INPUTS));
        for (int i = 0; i < otp.length() && i < otpInputs.size(); i++) {
            otpInputs.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }
        screenshot();
    }

    public void clickLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN_LINK)).click();
    }
}
