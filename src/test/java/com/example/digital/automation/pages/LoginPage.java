package com.example.digital.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

public class LoginPage extends BasePage<LoginPage> {

    @FindBy(id = "input28")
    private WebElement usernameField;

    @FindBy(id = "input36")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@value='Sign in']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Method to wait for usernameField to be visible
    public void waitForUsernameField() {
        waitForElementToBeVisible(usernameField);
    }
    public LoginPage enterUsername(String username) {
        enterText(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    public DashboardPage clickLogin() {
        click(loginButton);
        return new DashboardPage(driver);
    }
}
