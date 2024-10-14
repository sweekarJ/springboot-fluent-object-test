package com.example.digital.automation.stepdefinitions;

import com.aventstack.extentreports.Status;
import com.example.digital.automation.config.Config;
import com.example.digital.automation.pages.DashboardPage;
import com.example.digital.automation.pages.HomePage;
import com.example.digital.automation.pages.LoginPage;
import io.cucumber.java.en.*;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class LoginSteps extends BaseTest {

    @Autowired
    Config config;
    @Autowired
    CucumberHooks cucumberHooks;

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        try {
            loginPage.enterUsername(username)
                    .enterPassword(password);
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @And("I click the login button")
    public void i_click_the_login_button() {
        try {
            dashboardPage = loginPage.clickLogin();
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

    @Then("I should be redirected to the dashboard page")
    public void i_should_be_redirected_to_the_dashboard_page() {
        try {
            assertThat(driver.getCurrentUrl(),
                    anyOf(is(config.getPortalData().get("funds-url")), is("accounts-url")));
            cucumberHooks.scenarioTest.log(Status.PASS, "Step passed successfully");
        } catch (Exception e) {
            cucumberHooks.scenarioTest.log(Status.FAIL, "Step failed with error: " + e.getMessage());
            throw e;
        }
    }

}
