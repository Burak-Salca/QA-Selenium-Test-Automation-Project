import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MailPage;
import pages.MainPage;
import pages.RegisterPage;

public class PositiveCase extends BaseTest {

    RegisterPage registerPage;
    MailPage mailPage;
    LoginPage loginPage;
    MainPage mainPage;

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();

        registerPage = new RegisterPage();
        mailPage = new MailPage();
        loginPage = new LoginPage();
        mainPage = new MainPage();
    }

    @Test(description = "Başarılı kayıt işleminden sonra başarılı giriş kontrolü")
    public void RegisterAndLoginTest() throws InterruptedException {

        registerPage.enterFirstName(firstName)
                    .enterLastName(lastName)
                    .enterCountryCode(countryCode)
                    .clickCountryCode()
                    .enterPhoneNumber(uniquePhone)
                    .enterCompany(company)
                    .enterEmail(uniqueEmail)
                    .enterTitle(title)
                    .clickTitle()
                    .enterFirstPassword(password)
                    .enterSecondPassword(password)
                    .clickCheckBox()
                    .clickAccept()
                    .clickSubmit();

        String otp = null;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-3 > nz-modal-container > div > div > div")));
            System.out.println("Verify kodu yollandı");

            mailPage.openMail()
                    .openLoginMail()
                    .enterMail(uniqueEmail)
                    .enterPassword(uniqueEmailPassword)
                    .clickLogin();
            try{
                mailWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#__nuxt > div.h-screen.flex.overflow-hidden.bg-gray-100.antialiased.dark\\:bg-gray-900 > div.w-0.flex.flex-1.flex-col.overflow-hidden > main > div.mx-auto.max-w-7xl.px-4.lg\\:max-w-full.md\\:px-8.sm\\:px-6 > div.mt-6.overflow-hidden.bg-white.shadow.sm\\:rounded-md.dark\\:bg-gray-800\\/75 > ul > li > a > div")));
                System.out.println("Mail doğru e-posta adresine yollandı");
                mailPage.clickMessage();
                otp = mailPage.getVerifyCode();
                mailDriver.quit();

            } catch (Exception e) {
                mailDriver.quit();
                Assert.fail("Maile giriş yapılamadı");
            }
        } catch (Exception e) {
            Assert.fail("Doğrulama kodu yolanmadı");
        }

        registerPage.enterVerifyCode(otp);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-4 > nz-modal-container > div > div > div")));
            System.out.println("Doğrulama kodu doğru. Başarılı bir şekilde kayıt olundu.");
            loginPage.enterEmail(uniqueEmail)
                    .enterPassword(password)
                    .clickLogin();
            Thread.sleep(1000);
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Welcome')]")));
                System.out.println("Başarılı bir şekilde giriş yapıldı.");
                mainPage.isLoginSuccessful();

            } catch (Exception e){
                Assert.fail("Giriş yapılamadı. Email yada şifre yanlış.");
            }

        } catch (Exception e){
            Assert.fail("Doğrulama kodu doğru değil.");
        }
    }
}
