package com.progmasters.mars;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser() {
        ClassLoader loadTest = SeleniumTest.class.getClassLoader();

        System.setProperty("webdriver.chrome.driver", loadTest.getResource("windows/chromedriver.exe").getFile());

        driver = new ChromeDriver();
    }

    @Test
    public void fillForm() {
        driver.get("http://localhost:4200/register");

        driver.findElement(By.id("name")).sendKeys("PecskeSelenTest");
        driver.findElement(By.id("email")).sendKeys("pecske92@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("+36205851886");
        driver.findElement(By.cssSelector("body > app-root > div > app-register > div > div > div.register-div.text-left.p-sm-3.p-md-3 > form > div.row.mx-0 > div:nth-child(1) > app-provider-attributes > div.row.mx-0 > div:nth-child(1) > app-password > input")).sendKeys("Test1234");
        driver.findElement(By.cssSelector("body > app-root > div > app-register > div > div > div.register-div.text-left.p-sm-3.p-md-3 > form > div.row.mx-0 > div:nth-child(1) > app-provider-attributes > div.row.mx-0 > div:nth-child(2) > app-password > input")).sendKeys("Test1234");

        driver.findElement(By.cssSelector("body > app-root > div > app-register > div > div > div.register-div.text-left.p-sm-3.p-md-3 > form > div.form-group.col-sm-12.col-md-12 > button")).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}
