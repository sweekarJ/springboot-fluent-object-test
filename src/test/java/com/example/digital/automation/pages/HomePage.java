package com.example.digital.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends BasePage<HomePage> {
    @Autowired
    public HomePage(WebDriver driver) {
        super(driver);  // Pass WebDriver to BasePage
    }

    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;
    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement loginButton;
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptAllCookies;

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
