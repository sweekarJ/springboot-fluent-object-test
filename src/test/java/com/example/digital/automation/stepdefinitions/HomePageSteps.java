package com.example.digital.automation.stepdefinitions;

import com.aventstack.extentreports.Status;
import com.example.digital.automation.config.Config;
import com.example.digital.automation.pages.HomePage;
import com.example.digital.automation.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
@SpringBootTest
public class HomePageSteps extends BaseTest {
    @Autowired
    Config config;
    @Autowired
    CucumberHooks cucumberHooks;
    @Given("I am using the {string} browser with resolution {string}")
    public void i_am_using_the_browser_with_resolution(String browser, String resolution) {
        try {
            setUp(browser, resolution); // Call to BaseTest's setup method
            homePage = new HomePage(driver);
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @Given("I am on the welcome page")
    public void i_am_on_the_welcome_page() {
        try {
            homePage.goTo(config.getPortalData().get("welcome-url"));
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @And("I accept-all cookies")
    public void i_accept_all_cookies() {
        try {
            homePage.clickAcceptAll();
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @And("I click the homepage login button")
    public void i_click_the_homepage_login_button() {
        try {
            loginPage = homePage.clickLogin();
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() throws InterruptedException {
        try {
            loginPage.waitForUsernameField();
            assertThat(driver.getCurrentUrl(), containsString(config.getPortalData().get("login-url")));
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @And("I should see a login welcome message {string}")
    public void i_should_see_a_login_welcome_message(String expectedMessage) {
        try {
            String actualMessage = homePage.getWelcomeMessage();
            assertEquals(expectedMessage, actualMessage);
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }
}
