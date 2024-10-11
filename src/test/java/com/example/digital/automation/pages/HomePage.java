package com.example.digital.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;


public class HomePage extends BasePage<HomePage> {

    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;
    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement loginButton;
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptAllCookies;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getWelcomeMessage() {
        waitForElementToBeVisible(welcomeMessage);
        return welcomeMessage.getText();
    }

    public LoginPage clickLogin() {
        click(loginButton);
        return new LoginPage(driver);
    }

    public void clickAcceptAll() {
        click(acceptAllCookies);
    }
}
