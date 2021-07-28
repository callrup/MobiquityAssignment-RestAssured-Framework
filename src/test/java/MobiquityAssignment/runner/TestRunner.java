package MobiquityAssignment.runner;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/feature/",
glue="MobiquityAssignment.stepdefs",
dryRun= false, 
strict=false,
monochrome=true,
tags= {"@GetRestrictList"}
)
public class TestRunner {

}
