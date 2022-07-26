package com.team.rcb_api_test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

public class PlayerTest {

	public static Response response;

	@BeforeClass
	public void setupURL() {
		// here we setup the default URL and API base path to use throughout the tests
		RestAssured.baseURI = "https://run.mocky.io";
		RestAssured.basePath = "/v3/e83143cc-34e1-4441-9000-17ba526949dc";
	}
	
	/**
	 * TestCase Summary: Test that validates that the team has only 4 foreign players 
	 */

	@Test
	public void verify_team_4Foreign_Players() {
		int expResults = 4;
		response = given().when().get("/").then().contentType(ContentType.JSON).extract().response();

		List<String> countries = response.path("player.country");
		int actResults = 0;
		for (String country : countries) {
			if (!country.equalsIgnoreCase("India")) {
				actResults = actResults + 1;
			}
		}
		assertEquals(actResults, expResults);

	}
	
	/**
	 * TestCase Summary: Test that validates that there is atleast one wicket keeper
	 */

	@Test
	public void verify_team_atleast_one_wicketKeeper() {
		int expResults = 1;
		Response res = given().when().get("/").then().contentType(ContentType.JSON).extract().response();

		List<String> roles = res.path("player.role");
		int actResults = 0;
		for (String role : roles) {
			if (role.equalsIgnoreCase("Wicket-keeper") || role.equalsIgnoreCase("All-Rounder")) {
				actResults = actResults + 1;
			}
		}
		assertTrue(actResults >= expResults);
	}
}
