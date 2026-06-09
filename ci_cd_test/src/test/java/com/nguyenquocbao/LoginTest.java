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
            chromeOptions.addArguments("--headless"); // Giữ nguyên để chạy được trên GitHub Actions
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().window().maximize();
        
        // ======= ĐỔI ĐƯỜNG LINK SANG TRANG CỦA TLU =======
        driver.get("https://sinhvien1.tlu.edu.vn/#/login");
        
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testInvalidLoginShouldFail() {
        // Thử nghiệm với một tài khoản không tồn tại ngẫu nhiên
        loginPage.enterCredentials("225112xxxx", "matkhausaichat");
        loginPage.clickLogin();
        
        // Chờ 2 giây để trang xử lý phản hồi từ server
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        
        // Kỳ vọng: Đăng nhập thất bại, hệ thống vẫn giữ người dùng ở lại trang login
        Assert.assertTrue(loginPage.isLoginFailed(), "Lỗi: Nhập tài khoản sai nhưng hệ thống không giữ lại trang Login!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}