package com.example.digital.automation.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.given;

public class ApiStepDefinitions {
    private String endpoint;
    private String jsonResponse;

    @Given("I have the API endpoint {string}")
    public void i_have_the_api_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I send a GET request to the endpoint")
    public void i_send_a_get_request() {
        jsonResponse = given().get(endpoint).then().statusCode(200).extract().asString();
    }

    @Then("the response should contain {string}")
    public void the_response_should_contain(String key) {
        // Assert that response contains the expected key
    }
}
