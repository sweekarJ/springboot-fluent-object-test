package com.example.digital.automation.stepdefinitions;

import com.example.digital.automation.pages.DashboardPage;
import com.example.digital.automation.pages.HomePage;
import com.example.digital.automation.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DashboardSteps extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;
    DashboardPage dashboardPage;

    @And("I should see a dashboard welcome message {string}")
    public void i_should_see_a_dashboard_welcome_message(String expectedMessage) {
//        String actualMessage = dashboardPage.getWelcomeMessage();
//        assertEquals(expectedMessage, actualMessage);
    }
}
