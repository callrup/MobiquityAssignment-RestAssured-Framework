package MobiquityAssignment.stepdefs;

import io.cucumber.java.Before;

import io.cucumber.datatable.*;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.*;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.util.Map.Entry;

public class CommonSteps {
	static RequestSpecification requestSpecs;
	static ResponseBody body;
	static Response res;
	static String token;
	public static Properties prop = new Properties();
	public static FileInputStream fis;
	public static Properties propResource = new Properties ();
	public static FileInputStream fisResource;
	long currentTimeStamp = System.currentTimeMillis();
	static String environmentName;
	public static String resourceURL;
	 private static final Logger Log = LoggerFactory.getLogger(CommonSteps.class.getName());
	//public static Logger Log = LogManager.getLogger(CustomSearchSerenitysteps.class.getName());
	public static StringWriter requestWriter;
	public static PrintStream requestCapture;
	
	public static StringWriter responseWriter;
	public static PrintStream responseCapture;
	
	public static StringWriter errorWriter;
	public static PrintStream errorCapture;
	
	URL envPropertyPath = getClass().getClassLoader().getResource("env.properties");
	URL resourcePropertyPath = getClass().getClassLoader().getResource("resource.properties");
	
	
	@Before
	
	public void beforeEveryScenario() throws IOException
	{
		System.out.println("------------sceanrio start----------");
		fis = new FileInputStream(envPropertyPath.getFile());
		prop.load(fis);
		
		fisResource =new FileInputStream(resourcePropertyPath.getFile());
		propResource.load(fisResource);
		
		environmentName =prop.getProperty("env");
	}
	
	// Get Base url
	
	@Given ("^Testing environment$")
	
	public void getBaseURI() throws Throwable
	{
		RestAssured.baseURI = prop.getProperty(environmentName);
	RestAssured.useRelaxedHTTPSValidation();
	}
	
	 //Set Headers
	
	@When("^I pass headers$")
	public void setHeaders(Map<String,String> headers) throws Throwable
	{
		Iterator<Entry<String,String>> it = headers.entrySet().iterator();
		requestSpecs = given().filter(new RequestLoggingFilter(responseCapture)).filter(new ResponseLoggingFilter(responseCapture)).contentType(ContentType.JSON);
	while (it.hasNext())
	{
		Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
		requestSpecs=requestSpecs.header(pair.getKey(),pair.getValue());
	}
	
	}
	
	//Set queryParameters
	@And("^I pass queryParametres$")
	
	public void setQueryParam(Map<String, String>queryParam) throws Throwable
	{
	
		Iterator<Entry<String,String>> it = queryParam.entrySet().iterator();
		
		while (it.hasNext())
		{
			Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
			requestSpecs=requestSpecs.header(pair.getKey(),pair.getValue());
		}
	}
	
	
	//Trigger GET Endpoint
	
	@And("^I perform GET operation \"([^\"]*)\"$")
	public void invokeGETOperation(String resourceName) throws Throwable
	{
		resourceURL = propResource.getProperty(resourceName);
		res = requestSpecs.when().get(resourceURL);
	}
	
	// Validate Response type is in JSON format
	
	@And ("^response content Type is json$")
	public void verifyResponseContentType() throws Throwable
	{
		res.then().assertThat().contentType(ContentType.JSON);
		
	}
	
	//Validates response bidy contains the given values
	
	@And("^response Body contains$") 
	public void verifyResponseBodyValues(DataTable table) throws Throwable
	{
		List <String> list = table.asList(String.class);
		Iterator<String> it = list.iterator();
		body = res.getBody();
		String bodyStringValue = body.asString();
		while (it.hasNext())
		{
			String i = it.next();
			if (!bodyStringValue.contains(i))
			{
				Log.info("Scenario failed :" +i+ "not found");
			}
			Assert.assertTrue(bodyStringValue.contains(i));
		}
		
	}
	
	//Validate the HTTP status code
	
	@Then("^I should get \"([^\"]*\"response$")
	public void verifyHTTPStatusCode(String arg1) throws Throwable
	{
	
		if (res.getStatusCode()!=Integer.parseInt(arg1))
{
	Log.info("Scenario failed as expected HTTP status is :" +arg1);
}
		res = res.then().assertThat().statusCode(Integer.parseInt(arg1)).extract().response();
	}
}
