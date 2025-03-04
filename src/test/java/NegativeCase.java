import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MailPage;
import pages.MainPage;
import pages.RegisterPage;


public class NegativeCase extends BaseTest {

    RegisterPage registerPage = new RegisterPage();
    LoginPage loginPage = new LoginPage();


    @Test(description = "Kayıtlı bir hesap ile tekrar kayyıt kontrolü")
    public void RegisterWithExistingAccountTest() throws InterruptedException {

        registerPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCountryCode(countryCode)
                .enterPhoneNumber(uniquePhone)
                .enterCompany(company)
                .enterEmail(registeredAccount)
                .enterTitle(title)
                .enterFirstPassword(password)
                .enterSecondPassword(password)
                .clickCheckBox()
                .clickAccept()
                .clickSubmit();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'This email is already associated')]")));
        screenshot();
        String expectedMessage = "This email is already associated with an account. Please use a different email or log in";
        String actualMessage = errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Beklenen hata mesajı bulunamadı!");
    }

    @Test(description = "Şifrelerin uyuşmaması hata mesajı kontorlü")
    public void RegisterWithMismatchedPasswordsTest() throws InterruptedException {

        registerPage.enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCountryCode(countryCode)
                .enterPhoneNumber(uniquePhone)
                .enterCompany(company)
                .enterEmail(uniqueEmail)
                .enterTitle(title)
                .enterFirstPassword(password)
                .enterSecondPassword(wrongPassword)
                .clickCheckBox()
                .clickAccept()
                .clickSubmit();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Passwords does not match')]")));
        screenshot();
        String expectedMessage = "Passwords does not match!!";
        String actualMessage = errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Beklenen şifre uyuşmazlık hata mesajı bulunamadı!");
    }

    @Test(description = "Yanlış doğrulama kodu girildiğinde hata mesajı kontrolü")
    public void VerifyIncorrectVerificationCodeTest() throws InterruptedException {

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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-3 > nz-modal-container > div > div > div")));
        System.out.println("Verify kodu yollandı");
        registerPage.enterVerifyCode(wrongVerifyCode);
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Code not valid')]")));
        screenshot();
        String expectedMessage = "Code not valid";
        String actualMessage = errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage, "Beklenen doğrulama kodu hata mesajı bulunamadı!");
    }

    @Test(description = "Kayıtlı olmayan bir e-posta ile giriş kontrolü")
    public void LoginWithUnregisteredEmailTest()  {

        try {
            registerPage.clickLoginPage();
            wait.until(ExpectedConditions.urlContains("/login"));
            loginPage.enterEmail(unRegisteredAccount)
                    .enterPassword(password)
                    .clickLogin(unRegisteredAccount,password);
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Incorrect email or password')]")));
            screenshot();
            String expectedMessage = "Incorrect email or password. Please check your information and try again";
            String actualMessage = errorMessage.getText();
            Assert.assertEquals(actualMessage, expectedMessage, "Beklenen giriş hata mesajı bulunamadı!");

        } catch (Exception e) {
            Assert.fail("Login sayfasına yönlendirilemedi");
        }
    }

    @Test(description = "Yanlış formatta e-posta ile giriş kontrolü")
    public void LoginWithInvalidEmailFormatTest()  {

        try {
            registerPage.clickLoginPage();
            wait.until(ExpectedConditions.urlContains("/login"));
            loginPage.enterEmail(invalidEmail)
                    .enterPassword(password)
                    .clickLogin(invalidEmail,password);
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Please fill the required fields!')]")));
            screenshot();
            String expectedMessage = "Please fill the required fields!";
            String actualMessage = errorMessage.getText();
            Assert.assertEquals(actualMessage, expectedMessage, "Beklenen giriş hata mesajı bulunamadı!");

        } catch (Exception e) {
            Assert.fail("Login sayfasına yönlendirilemedi");
        }

    }


}
