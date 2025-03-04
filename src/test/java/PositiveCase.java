import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MailPage;
import pages.MainPage;
import pages.RegisterPage;


public class PositiveCase extends BaseTest {

    RegisterPage registerPage = new RegisterPage();
    MailPage mailPage = new MailPage();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();
    }


    @Test(description = "Başarılı kayıt işleminden sonra başarılı giriş kontrolü")
    public void RegisterAndLoginTest() throws InterruptedException {
        registerPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCountryCode(countryCode)
                .enterPhoneNumber(uniquePhone)
                .enterCompany(company)
                .enterEmail(uniqueEmail)
                .enterTitle(title)
                .enterFirstPassword(password)
                .enterSecondPassword(password)
                .clickCheckBox()
                .clickAccept()
                .clickSubmit();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-3 > nz-modal-container > div > div > div")));
            System.out.println("Verify kodu yollandı");
            String verifyCode = null;
            mailPage.openMail()
                    .openLoginMail()
                    .enterMail(uniqueEmail)
                    .enterPassword(uniqueEmailPassword)
                    .clickLogin(uniqueEmailPassword)
                    .clickMessage();
            verifyCode =mailPage.getVerifyCode();
            registerPage.enterVerifyCode(verifyCode);
            try {
                attachScreenshotToStep("Verify kodu doğru. Başarılı bir şekilde kayıt olundu.");
                WebElement successPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-4 > nz-modal-container > div > div > div")));
                wait.until(ExpectedConditions.invisibilityOf(successPopup));
                loginPage.enterEmail(uniqueEmail)
                        .enterPassword(password)
                        .clickLogin(uniqueEmail, password);
                mainPage.isLoginSuccessful();

            } catch (Exception e) {
                Assert.fail("Doğrulama kodu doğru değil.");
            }
        }catch(Exception e){
            Assert.fail("Form eksik ya da yanlış dolduruldu");
        }

    }

}
