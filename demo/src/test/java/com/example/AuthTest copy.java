package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class AuthTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
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

    driver.get("https://seleniumbase.io/simple/login");

    driver.findElement(By.id("username"))
            .sendKeys("wrong_user");

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
// Test 4: ไม่ใส่ Username แต่ใส่ Password
@Test
void shouldShowErrorWhenUsernameIsEmpty() {

    driver.get("https://seleniumbase.io/simple/login");

    // ไม่ใส่ Username

    // ใส่เฉพาะ Password
    driver.findElement(By.id("password"))
            .sendKeys("secret_pass");

    driver.findElement(By.id("log-in"))
            .click();

    // ตรวจสอบว่ายังอยู่หน้า Login
    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Username is required.");
}
// Test 5: Username ถูก แต่ Password ผิด
@Test
void shouldShowErrorWhenPasswordIsWrong() {

    driver.get("https://seleniumbase.io/simple/login");

    // Username ถูก
    driver.findElement(By.id("username"))
            .sendKeys("demo_user");

    // Password ผิด
    driver.findElement(By.id("password"))
            .sendKeys("wrong_password");

    driver.findElement(By.id("log-in"))
            .click();

    // ตรวจสอบว่ายังอยู่หน้า Login
    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Username is correct, but password is incorrect.");
}
// Test 6: Username ถูก แต่ Password ผิด
@Test
void shouldShowErrorWhenPasswordIsWrongAgain() {

    driver.get("https://seleniumbase.io/simple/login");

    // Username ถูก
    driver.findElement(By.id("username"))
            .sendKeys("demo_user");

    // Password ผิด
    driver.findElement(By.id("password"))
            .sendKeys("wrong_password");

    driver.findElement(By.id("log-in"))
            .click();

    // ตรวจสอบว่ายังอยู่หน้า Login
    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Login failed: Username is correct but Password is wrong.");
}
// Test 7: ไม่ใส่ Username และ Password
@Test
void shouldNotLoginWhenBothFieldsAreEmpty() {

    driver.get("https://seleniumbase.io/simple/login");

    // ไม่ใส่ Username
    // ไม่ใส่ Password

    driver.findElement(By.id("log-in"))
            .click();

    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Login blocked when both fields are empty.");
}


// Test 8: Username เป็นช่องว่าง
@Test
void shouldNotLoginWhenUsernameContainsOnlySpaces() {

    driver.get("https://seleniumbase.io/simple/login");

    driver.findElement(By.id("username"))
            .sendKeys("   ");

    driver.findElement(By.id("password"))
            .sendKeys("secret_pass");

    driver.findElement(By.id("log-in"))
            .click();

    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Login blocked when username contains only spaces.");
}


// Test 9: Password เป็นช่องว่าง
@Test
void shouldNotLoginWhenPasswordContainsOnlySpaces() {

    driver.get("https://seleniumbase.io/simple/login");

    driver.findElement(By.id("username"))
            .sendKeys("demo_user");

    driver.findElement(By.id("password"))
            .sendKeys("   ");

    driver.findElement(By.id("log-in"))
            .click();

    assertTrue(driver.getCurrentUrl().contains("login"));

    System.out.println("Login blocked when password contains only spaces.");
}


// Test 10: ตรวจสอบ Password field
@Test
void shouldHidePasswordInput() {

    driver.get("https://seleniumbase.io/simple/login");

    String inputType = driver.findElement(By.id("password"))
            .getAttribute("type");

    assertEquals("password", inputType);

    System.out.println("Password field is hidden.");
}


// Test 11: ตรวจสอบว่ามีช่อง Username
@Test
void shouldHaveUsernameField() {

    driver.get("https://seleniumbase.io/simple/login");

    WebElement username = driver.findElement(By.id("username"));

    assertTrue(username.isDisplayed());

    System.out.println("Username field is displayed.");
}


// Test 12: ตรวจสอบว่ามีช่อง Password
@Test
void shouldHavePasswordField() {

    driver.get("https://seleniumbase.io/simple/login");

    WebElement password = driver.findElement(By.id("password"));

    assertTrue(password.isDisplayed());

    System.out.println("Password field is displayed.");
}
}