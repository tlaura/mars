package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstitutionSeleniumIT {

    private WebDriver driver;

    @BeforeEach
    public void startBrowser() {
        ClassLoader loadTest = InstitutionSeleniumIT.class.getClassLoader();

        Boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
        if (isWindows) {
            System.setProperty("webdriver.chrome.driver", loadTest.getResource("windows/chromedriver.exe").getFile());
        }

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");

        driver = new ChromeDriver(options);
    }

    @Test
    public void testFillAddInstitutionForm() throws InterruptedException {
        //Add institution via register form
        driver.get("http://localhost:4200/institution-form");
        driver.findElement(By.cssSelector("#name")).sendKeys("Test Institution");
        driver.findElement(By.cssSelector("#email")).sendKeys("test.insti@gmail.com");
        driver.findElement(By.cssSelector("#phone")).sendKeys("+363333333");
        driver.findElement(By.cssSelector("#zipcode")).sendKeys("3700");
        driver.findElement(By.cssSelector("#city")).sendKeys("Kazincbarcika");
        driver.findElement(By.cssSelector("#address")).sendKeys("Május 1 út 12.");
        driver.findElement(By.cssSelector("body > app-root > div > app-institution-form > div > div > div > form > app-institution > form > div > div.col-sm-12.col-md-6.px-0 > div:nth-child(3) > textarea"))
                .sendKeys("1TWKCvdZw4Mt03nLmcWyJfsm11t9eGlHyubGFsqm");

        driver.findElement(By.cssSelector("body > app-root > div > app-institution-form > div > div > div > form > button"))
                .click();

        //Log in with admin for accept the request
        driver.findElement(By.cssSelector("#myNavbar > ul.navbar-nav.ml-auto.mt-2.mt-lg-0 > li > a"))
                .click();
        driver.findElement(By.cssSelector("#email")).sendKeys("emahideaki@gmail.com");
        driver.findElement(By.cssSelector("#password")).sendKeys("elza123");
        driver.findElement(By.cssSelector("body > app-root > div > app-login-form > div > div > div > form > button"))
                .click();

        //Go to the list
        driver.get("http://localhost:4200/evaluate-list");

        //Find institution
        String expectedName = "Test Institution";
        assertThrows(Throwable.class, () -> driver.findElement(By.xpath("//*[text()[contains(.,'" + expectedName + "')]]")));

    }


    @AfterEach
    public void closeDriver() {
        driver.quit();
    }

}
