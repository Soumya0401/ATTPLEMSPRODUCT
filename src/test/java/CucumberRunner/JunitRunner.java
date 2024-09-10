package CucumberRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = {
		            
    	            "src/test/java/features/Adminfeatures.feature"
    	        },
    	        dryRun = false,
    	        glue = "stepDefinitions",
    	        monochrome = true,
    	        tags = "@MobileTest",
    	        plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"}
    	    )


public class JunitRunner  
{
    // No additional code is needed here unless specific configurations or methods are required
}
