package com.tdd.api.bdd;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {"com.tdd.api.bdd.stepsdefs"}
)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CucumberTest {
/*
 * set up
 * https://medium.com/@bcarunmail/set-up-and-run-cucumber-tests-in-spring-boot-application-d0c149d26220
 */
}
