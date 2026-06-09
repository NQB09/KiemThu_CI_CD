package com.nguyenquocbao;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nguyenquocbao.LoginPage;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        String browser = System.getProperty("browser", "chrome");

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--headless"); 
            // Giả lập User-Agent của người dùng thật để tránh bị Firewall chặn bot
            firefoxOptions.addPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0");
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless"); 
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--window-size=1920,1080");
            // Giả lập User-Agent của người dùng thật để tránh bị Firewall chặn bot
            chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().window().maximize();
        driver.get("https://practice.expandtesting.com/login");
        loginPage = new LoginPage(driver);
    }

    // KỊCH BẢN 1: ĐĂNG NHẬP SAI
    @Test
    public void testInvalidLoginShouldFail() {
        // Nhập tài khoản và mật khẩu sai
        loginPage.enterCredentials("wrongUser", "WrongPassword!");
        loginPage.clickLogin();
        
        // Kỳ vọng: Hệ thống báo lỗi và giữ lại ở trang login
        Assert.assertTrue(loginPage.isLoginFailed(), "Lỗi: Nhập tài khoản sai nhưng không giữ lại ở trang Login!");
    }

    // KỊCH BẢN 2: ĐĂNG NHẬP ĐÚNG
    @Test
    public void testValidLoginShouldSuccess() {
        // Điền tài khoản và mật khẩu đúng của trang expandtesting
        loginPage.enterCredentials("practice", "SuperSecretPassword!");
        loginPage.clickLogin();
        
        // Kỳ vọng: Đăng nhập thành công, URL đã chuyển hướng ra khỏi trang /login (vào /secure)
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Lỗi: Đăng nhập tài khoản đúng nhưng không vào được bên trong!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}