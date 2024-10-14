package com.example.digital.automation.stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.Base64;

public class CucumberHooks {

    @Autowired
    private WebDriver driver;
    //    @Autowired
//    private VideoRecorder videoRecorder;
    private static ExtentReports extent;
    ExtentTest scenarioTest;

    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
        } else {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Step Screenshot");
        }
    }

    @Before
    public void beforeScenario(Scenario scenario) throws IOException, AWTException {
//        String scenarioName = scenario.getName().replaceAll(" ", "_");
//        videoRecorder.startRecording(scenarioName);
        if (extent == null) {
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/extent-report.html");

            htmlReporter.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        scenarioTest = extent.createTest(scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) throws IOException {
//        videoRecorder.stopRecording();
        // Attach video to Cucumber report if needed
        if (scenario.isFailed()) {
            // Take a screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] imageData = screenshot.getScreenshotAs(OutputType.BYTES);
            String encodedImage = Base64.getEncoder().encodeToString(imageData);
            scenarioTest.fail("Test Failed")
                    .addScreenCaptureFromBase64String(encodedImage);
        } else {
            scenarioTest.pass("Test Passed");
        }
        extent.flush();
    }

}
