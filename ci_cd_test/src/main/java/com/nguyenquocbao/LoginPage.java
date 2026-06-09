package com.nguyenquocbao;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Tìm ô nhập Tên đăng nhập (SauceDemo dùng name="user-name")
    @FindBy(name = "user-name")
    private WebElement usernameField;

    // 2. Tìm ô nhập Mật khẩu (SauceDemo dùng name="password")
    @FindBy(name = "password")
    private WebElement passwordField;

    // 3. Tìm nút Đăng nhập (SauceDemo dùng id="login-button")
    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Khởi tạo bộ đợi thông minh 10 giây để chờ giao diện load xong
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterCredentials(String username, String password) {
        // Chờ cho ô nhập liệu hiển thị trên màn hình rồi mới gõ chữ
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        // Chờ nút đăng nhập sẵn sàng để click
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public boolean isLoginFailed() {
        try {
            // SauceDemo hiển thị lỗi với thuộc tính data-test="error"
            return wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.cssSelector("[data-test='error']"))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isLoginSuccessful() {
        try {
            // Sau khi đăng nhập thành công, SauceDemo chuyển hướng sang trang /inventory.html
            return wait.until(ExpectedConditions.urlContains("inventory.html"));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}