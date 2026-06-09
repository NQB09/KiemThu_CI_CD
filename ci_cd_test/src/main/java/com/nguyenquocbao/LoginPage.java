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

    // 1. Tìm ô nhập Tên đăng nhập / Mã sinh viên (Sử dụng thuộc tính name="username")
    @FindBy(name = "username")
    private WebElement usernameField;

    // 2. Tìm ô nhập Mật khẩu (Sử dụng thuộc tính name="password")
    @FindBy(name = "password")
    private WebElement passwordField;

    // 3. Tìm nút Đăng nhập (Sử dụng selector theo class và type của nút)
    @FindBy(css = "button[type='submit']")
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
            // Trang này khi đăng nhập sai thường hiện một thông báo lỗi (Toast/Alert)
            // Hoặc đơn giản là URL vẫn giữ nguyên ở trang /#/login
            return driver.getCurrentUrl().contains("/login");
        } catch (Exception e) {
            return false;
        }
    }
}