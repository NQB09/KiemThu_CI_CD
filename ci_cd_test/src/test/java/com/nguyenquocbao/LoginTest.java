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
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless"); 
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().window().maximize();
        driver.get("https://sinhvien1.tlu.edu.vn/#/login");
        loginPage = new LoginPage(driver);
    }

    // KỊCH BẢN 1: ĐĂNG NHẬP SAI
    @Test
    public void testInvalidLoginShouldFail() {
        // Nhập tài khoản và mật khẩu sai bừa
        loginPage.enterCredentials("2351067085", "bao0309");
        loginPage.clickLogin();
        
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        // Kỳ vọng: Hệ thống báo lỗi và giữ lại ở trang login
        Assert.assertTrue(loginPage.isLoginFailed(), "Lỗi: Nhập tài khoản sai nhưng không giữ lại ở trang Login!");
    }

    // KỊCH BẢN 2: ĐĂNG NHẬP ĐÚNG
    @Test
    public void testValidLoginShouldSuccess() {
        // Điền tài khoản và mật khẩu ĐÚNG thật của bạn vào đây để test
        loginPage.enterCredentials("2351067085", "bao090325");
        loginPage.clickLogin();
        
        // Chờ 3-4 giây vì trang đăng nhập đúng sẽ mất thời gian load vào dashboard bên trong
        try { Thread.sleep(4000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        // Kỳ vọng: Đăng nhập thành công, URL đã chuyển hướng ra khỏi trang /login
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Lỗi: Đăng nhập tài khoản đúng nhưng không vào được bên trong!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}