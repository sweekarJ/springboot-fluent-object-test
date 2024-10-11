package com.example.digital.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardPage extends BasePage<DashboardPage> {

    @Autowired
    public DashboardPage(WebDriver driver) {
        super(driver);  // Pass WebDriver to BasePage
    }

    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;

    public String getWelcomeMessage() {
        waitForElementToBeVisible(welcomeMessage);
        return welcomeMessage.getText();
    }

}
