Feature: User Login

  As a registered user
  I want to log into the application
  So that I can access my account

  @UI @login
  Scenario Outline: Successful login with browser and resolution selection
    Given I am using the "<browser>" browser with resolution "<resolution>"
    And I am on the welcome page
    And I accept-all cookies
    And I click the homepage login button
    Then I should be redirected to the login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should be redirected to the dashboard page

    Examples:
      | browser  | resolution  | username  | password  | welcomeMessage       |
      | chrome   | 1920x1080   | testuser  | password  | Welcome, testuser!   |
#      | firefox  | 1366x768    | admin     | admin123  | Welcome, admin!      |
#      | edge     | 1280x720    | testuser  | password  | Welcome, testuser!   |
