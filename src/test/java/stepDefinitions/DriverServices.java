package stepDefinitions;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DriverServices {
    WebDriver driver;

    @Given("Driver is on the ATTPL EMS Landing Page")
    public void driver_is_on_the_attpl_ems_landing_page() 
    {
        // Set ChromeOptions to handle notifications and geolocation
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("incognito");

        // Set geolocation to allow permissions automatically
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("profile.default_content_setting_values.geolocation", 1); // 1 = Allow
        }});

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to("https://app.attplems.com/");
        // driver.navigate().to("https://appdev.attplems.com/");
        
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
	    
	    @When("Driver logs in with the registered mobile number and password")
	    public void driver_logs_in_with_the_registered_mobile_number_and_password() 
	    {
	        try {
	            driver.findElement(By.name("userphone")).sendKeys("8239552369");
	            driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
	            driver.findElement(By.name("password")).sendKeys("Apple@12345");
	            driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	            
	            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiAlert-message css-1xsto0d']")));

	            if (errorMessage.isDisplayed()) 
	            {
	                throw new Exception("Login failed due to incorrect details.");
	            }
	        } catch (TimeoutException e) 
	        {
	            System.out.println("Login successful, no error message found.");
	        } catch (Exception e) 
	        {
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    //After Successful Login
	  
	    @Then("Driver should see the dashboard")
	    public void driver_should_see_the_dashboard() 
	    {
	        String pagetitle = driver.getTitle();
	        System.out.println(pagetitle);

	        if(pagetitle.equals("Dashboard: App")) 
	        {
	            System.out.println("Login Successful");
	        } 
	        else 
	        {
	            System.out.println("Login Failed");
	        }
	    }
	    
	    @When("I allow location access")
	    public void i_allow_location_access() 
	    {
	    	WebElement permissionDropdown = driver.findElement(By.id("permission"));
	    	Select select = new Select(permissionDropdown);
	    	select.selectByValue("allow");


	    }


}
