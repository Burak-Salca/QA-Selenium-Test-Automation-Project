package base;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;


public class BaseLibrary extends Data{

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshotMail() {
        return ((TakesScreenshot) mailDriver).getScreenshotAs(OutputType.BYTES);
    }

    public void attachScreenshotToStep(String stepName) {
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(stepName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
    }

    void cleanAllureResults() {
        File allureResults = new File("allure-results");
        if (allureResults.exists() && allureResults.isDirectory()) {
            for (File file : allureResults.listFiles()) {
                file.delete();
            }
        }
    }
}
