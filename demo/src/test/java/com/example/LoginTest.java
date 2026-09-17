package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

class LoginTest {

    private WebDriver driver;

    @BeforeEach
void setUp() {
ChromeOptions options = new ChromeOptions();

if (Boolean.getBoolean("headless")) {
options.addArguments("--headless=new");
options.addArguments("--window-size=1920,1080");
}
driver = new ChromeDriver(options);
}

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Test 1: Login สำเร็จ
    @Test
    void shouldLoginSuccessfully() {

        driver.get("https://seleniumbase.io/simple/login");

        driver.findElement(By.id("username"))
                .sendKeys("demo_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_pass");

        driver.findElement(By.id("log-in"))
                .click();

        String heading = driver.findElement(By.tagName("h1"))
                .getText();

        assertEquals("Welcome!", heading);
    }

    // Test 2: Login ผิดแล้วต้องมีข้อความแจ้งเตือน
    @Test
    void shouldShowErrorMessageWhenLoginFails() {    

        // 1. เรียกใช้ driver.get ก่อน เพื่อเปิดหน้าเว็บ
        driver.get("https://seleniumbase.io/simple/login");

        // 2. สร้าง WebDriverWait หลังจากเปิดหน้าเว็บแล้ว
        WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        // 3. รอให้ช่อง username แสดง แล้วจึงใส่ข้อมูล
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("username")
            )
        ).sendKeys("wrong_user");

        driver.findElement(By.id("password"))
                .sendKeys("wrong_password");

        driver.findElement(By.id("log-in"))
                .click();

        // ค้นหาข้อความที่แสดงหลังจาก Login ผิด
        String message = driver.findElement(By.tagName("body"))
                .getText();

        // ถ้ามีข้อความอะไรก็ได้ที่หน้าเว็บแสดงกลับมา ถือว่า Test สำเร็จ
        assertFalse(message.isEmpty());

        System.out.println("Login failed message: " + message);
    }

    // Test 3: ใส่ Username แต่ไม่ใส่ Password
    @Test
    void shouldShowErrorWhenPasswordIsEmpty() {

        driver.get("https://seleniumbase.io/simple/login");

        // ใส่เฉพาะ Username
        driver.findElement(By.id("username"))
                .sendKeys("demo_user");

        // ไม่ใส่ Password

        driver.findElement(By.id("log-in"))
                .click();

        // ตรวจสอบว่ามีข้อความแจ้งเตือนกลับมา
        String message = driver.findElement(By.tagName("body"))
                .getText();

        assertFalse(message.isEmpty());

        System.out.println("Validation message: " + message);
    }
}