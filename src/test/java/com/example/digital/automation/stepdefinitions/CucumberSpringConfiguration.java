package com.example.digital.automation.stepdefinitions;

import com.example.digital.automation.Application;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
//@ContextConfiguration(classes = Application.class)
public class CucumberSpringConfiguration {
    // This class can remain empty; its purpose is to hold the annotations
}
