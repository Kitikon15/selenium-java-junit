package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

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

class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Option for headless execution in CI environments:
        // options.addArguments("--headless=new");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        // Explicit wait configured for up to 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginSuccessfully() {
        driver.get("https://seleniumbase.io/simple/login");

        // Wait for inputs to be visible before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
            .sendKeys("demo_user");

        driver.findElement(By.id("password"))
            .sendKeys("secret_pass");

        driver.findElement(By.id("log-in"))
            .click();

        // Explicitly wait for the post-login heading element to appear
        WebElement headingElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        assertEquals("Welcome!", headingElement.getText().trim());
    }
}