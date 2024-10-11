package com.example.digital.automation.stepdefinitions;

import com.example.digital.automation.pages.DashboardPage;
import com.example.digital.automation.pages.HomePage;
import com.example.digital.automation.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {

    @Autowired
    protected WebDriver driver;
    @Autowired
    protected LoginPage loginPage;
    @Autowired
    protected HomePage homePage;
    @Autowired
    protected DashboardPage dashboardPage;
//    static ScreenshotHook screenshotHook;

    @Before
    public void setUp(String browser, String resolution) {
        // Split the resolution string (e.g., "1920x1080") to set browser window size
        String[] dimensions = resolution.split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
