package base;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Data {

    public static WebDriver driver;
    public static WebDriver mailDriver;
    public static WebDriverWait wait;
    public static WebDriverWait mailWait;
    public static SoftAssert softAssert;

    public String url = "https://app.forceget.com/system/account/register";
    public String uniquePhone = generateUniquePhone();
    public String uniqueEmail = genarteUniqueEmail();
    public String uniqueEmailPassword = generateRandomEmailPassword();
    public String firstName = "Burak";
    public String lastName = "Salça";
    public String countryCode = "+90";
    public String company = "Company";
    public String title = "Test Engineer";
    public String password = "Siyah.0699*";
    public String registeredAccount ="siyah.0699@hotmail.com";
    public String wrongPassword = "Siyah0699*";
    public String wrongVerifyCode = "000000";
    public String unRegisteredAccount = "unregistered@example.com";
    public String invalidEmail = "unregisteredexample.com";

    private String generateUniqueEmailBody() {
        long timestamp = System.currentTimeMillis();
        String userName = generateRandomEmailPassword();
        return userName + timestamp;
    }

    private String genarteUniqueEmail(){
        String emailBody = generateUniqueEmailBody();
        return emailBody + "@edny.net";
    }

    private String generateRandomEmailPassword(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String password = "";
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password += chars.charAt(index);
        }
        return password;
    }

    private String generateUniquePhone() {
        Random random = new Random();
        int phoneNumber = random.nextInt(900000) + 100000;
        return "505" + phoneNumber;
    }

    public static void createMailTmAccount(String email, String password) {
        try {

            URL url = new URL("https://api.mail.tm/accounts");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject payload = new JSONObject();
            payload.put("address", email);
            payload.put("password", password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 201 && responseCode != 200) {
                System.out.println("Hesap oluşturulamadı. HTTP Response Code: " + responseCode);
                BufferedReader errIn = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errContent = new StringBuilder();
                String line;
                while ((line = errIn.readLine()) != null) {
                    errContent.append(line);
                }
                errIn.close();
                System.out.println("Error: " + errContent.toString());
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseContent = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(responseContent.toString());
            System.out.println("Hesap oluşturuldu: " + jsonResponse);
            System.out.println("Oluşturulan mail: " + email);
            System.out.println("Oluşturulan şifre: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
