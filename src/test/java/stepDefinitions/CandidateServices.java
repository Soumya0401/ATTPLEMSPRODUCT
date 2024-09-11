package stepDefinitions;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CandidateServices 
{
	WebDriver driver;
	
	 @Given("Candidate is on the ATTPL EMS landing page")
	    public void candidate_is_on_the_attpl_ems_landing_page() 
	    {
	    	ChromeOptions options = new ChromeOptions();
	    	options.addArguments("--disable-notifications");
	        driver = new ChromeDriver(options);
	    	driver.manage().window().maximize();
	    	driver.get("https://app.attplems.com/");
	    	// driver.get("https://appdev.attplems.com/"); 
	    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	    }
	    
	    @When("the Candidate enters their registered mobile number and password")
	    public void the_candidate_enters_their_registered_mobile_number_and_password() 
	    {
	        try {
	            driver.findElement(By.name("userphone")).sendKeys("7739432284");
	            driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
	            driver.findElement(By.name("password")).sendKeys("Password@765");
	            //driver.findElement(By.name("password")).sendKeys("Apple@123456");
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
	  
	    @Then("the Candidate should be successfully logged in and redirected to the dashboard")
	    public void the_candidate_should_be_successfully_logged_in_and_redirected_to_the_dashboard() 
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

	    
//////////////////////////////////////////////////// EMS /////////////////////////////////////////////////////////
	    
	    @Given("the candidate navigates to the Ward Creation page.")
	    public void the_candidate_navigates_to_the_ward_creation_page() 
	    {
	    	 driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
	         driver.findElement(By.xpath("//span[contains(text(),'WARD MANAGEMENT')]")).click();
	         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	         driver.findElement(By.linkText("New Ward")).click(); 
	        
	    }
	    @When("the candidate enters the following details into the Ward Creation form:")
	    public void the_candidate_enters_the_following_details_into_the_ward_creation_form(DataTable dataTable)
	    {
	    	 List<List<String>> data = dataTable.cells();
	         
	         String electionName = data.get(0).get(1);
	         WebElement electionInput = driver.findElement(By.id("autocomplete-electionId"));
	         selectDropdownOption(electionInput, electionName);
	         
	         driver.findElement(By.name("wardNumber")).sendKeys(data.get(1).get(1));
	         
	         driver.findElement(By.name("wardName")).sendKeys(data.get(2).get(1));
	         
	         driver.findElement(By.name("wardStreetAddress")).sendKeys(data.get(3).get(1));
	         
	         driver.findElement(By.name("wardPostalCode")).sendKeys(data.get(4).get(1));
	         
	         JavascriptExecutor js = (JavascriptExecutor) driver;
	         js.executeScript("window.scrollBy(0,300)", "");
	         
	         String districtName = data.get(5).get(1);
	         WebElement districtInput = driver.findElement(By.id("autocomplete-districtName"));
	         selectDropdownOption(districtInput, districtName);
	         
	         String tehsilName = data.get(6).get(1);
	         WebElement tehsilInput = driver.findElement(By.id("autocomplete-tehsilName"));
	         selectDropdownOption(tehsilInput, tehsilName);
	         
	         String wardCity = data.get(7).get(1);
	         WebElement wardCityInput = driver.findElement(By.id("autocomplete-wardCity"));
	         selectDropdownOption(wardCityInput, wardCity);
	         
	         String wardState = data.get(8).get(1);
	         WebElement wardStateInput = driver.findElement(By.id("autocomplete-wardState"));
	         selectDropdownOption(wardStateInput, wardState);
	         
	         JavascriptExecutor js1 = (JavascriptExecutor) driver;
	         js1.executeScript("window.scrollBy(0,500)", "");
	         
	         String wardCountry = data.get(9).get(1);
	         WebElement wardCountryInput = driver.findElement(By.id("autocomplete-wardCountry"));
	         selectDropdownOption(wardCountryInput, wardCountry);
	         
	         String wardCapacity = data.get(10).get(1);
	         WebElement wardCapacityInput = driver.findElement(By.id("autocomplete-wardCapacity"));
	         selectDropdownOption(wardCapacityInput, wardCapacity);
	        
	         driver.findElement(By.name("emergencyContactNumber")).sendKeys(data.get(11).get(1));
	        
	         driver.findElement(By.name("incidentReporting")).sendKeys(data.get(12).get(1));
	         
	         driver.findElement(By.name("securityMeasures")).sendKeys(data.get(13).get(1));
	         
	         driver.findElement(By.xpath("//button[@type='submit']")).click();
	     }

	     private void selectDropdownOption(WebElement inputElement, String optionText) {
	         inputElement.click();
	         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
	         for (WebElement option : options) {
	             if (option.getText().equals(optionText)) {
	                 option.click();
	                 break;
	             }
	        }
	     }
	     
	    @Then("the ward should be successfully created.")
	    public void the_ward_should_be_successfully_created() 
	    {
	    	 WebElement successMessage = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")); 
	         String message = successMessage.getText();
	         Assert.assertEquals("Profile created successfully", message);
	         System.out.println("Ward Created Sucessfully");
	     }
	    
	    
	    @Given("the candidate navigates to the Booth Creation page")
	    public void the_candidate_navigates_to_the_booth_creation_page() 
	    {
	            driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
	            driver.findElement(By.xpath("//span[contains(text(),'BOOTH MANAGEMENT')]")).click();
	            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	            driver.findElement(By.linkText("New Booth")).click(); 
	    
	    }
	    @When("the candidate fills in the booth creation form with the following details:")
	    public void the_candidate_fills_in_the_booth_creation_form_with_the_following_details(DataTable dataTable) 
	    {
	    	List<List<String>> data = dataTable.cells();
	         
	         driver.findElement(By.name("boothName")).sendKeys(data.get(0).get(1));
	        
	         String boothName = data.get(1).get(1);
	         WebElement boothNameInput = driver.findElement(By.id("autocomplete-boothDimensions"));
	         selectDropdownOption(boothNameInput, boothName);
	         
	         String boothCapacity = data.get(2).get(1);
	         WebElement boothCapacityInput = driver.findElement(By.id("autocomplete-boothCapacity"));
	         selectDropdownOption(boothCapacityInput, boothCapacity);
	         
	         driver.findElement(By.name("boothStreetAddress")).sendKeys(data.get(3).get(1));
	         
	         String boothCity = data.get(4).get(1);
	         WebElement boothCityInput = driver.findElement(By.id("autocomplete-boothCity"));
	         selectDropdownOption(boothCityInput, boothCity);
	         
	         JavascriptExecutor js = (JavascriptExecutor) driver;
	         js.executeScript("window.scrollBy(0,500)", "");
	                  
	         String boothState = data.get(5).get(1);
	         WebElement boothStateInput = driver.findElement(By.id("autocomplete-boothState"));
	         selectDropdownOption(boothStateInput, boothState);
	         
	         driver.findElement(By.name("boothPostalCode")).sendKeys(data.get(6).get(1));
	         
	         String boothCountry = data.get(7).get(1);
	         WebElement boothCountryInput = driver.findElement(By.id("autocomplete-boothCountry"));
	         selectDropdownOption(boothCountryInput, boothCountry);
	         
	         String wardName = data.get(8).get(1);
	         WebElement wardNameInput = driver.findElement(By.id("autocomplete-wardId"));
	         selectDropdownOption(wardNameInput, wardName);
	         
	         driver.findElement(By.xpath("//button[@type='submit']")).click();        
	    }
	    
	    @Then("the booth should be successfully created")
	    public void the_booth_should_be_successfully_created() 
	    {
	    	WebElement successMessage = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")); 
	         String message = successMessage.getText();
	         Assert.assertEquals("Booth created successfully", message);
	         System.out.println("Booth created successfully");
	         
	    }
	    
	    @Given("the candidate navigates to the Polling Station Creation page")
	    public void the_candidate_navigates_to_the_polling_station_creation_page() 
	    {
	    	driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
	         driver.findElement(By.xpath("//span[contains(text(),'POLLING MANAGEMENT')]")).click();
	         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	         driver.findElement(By.linkText("New Poll")).click();
	   
	    }
	    
	    @When("the candidate fills in the polling station creation form with the following details:")
	    public void the_candidate_fills_in_the_polling_station_creation_form_with_the_following_details(DataTable dataTable) 
	    {
	    	 List<List<String>> data = dataTable.cells();
	         
	         String boothName = data.get(0).get(1);
	         WebElement boothNameInput = driver.findElement(By.id("autocomplete-boothId"));
	         selectDropdownOption(boothNameInput, boothName);
	         
	         driver.findElement(By.name("pollingStationName")).sendKeys(data.get(1).get(1));
	         
	         String pollingCapacity = data.get(2).get(1);
	         WebElement pollingCapacityInput = driver.findElement(By.id("autocomplete-pollingCapacity"));
	         selectDropdownOption(pollingCapacityInput, pollingCapacity);
	         
	         driver.findElement(By.name("numberOfBooth")).sendKeys(data.get(3).get(1));
	         
	         driver.findElement(By.xpath("//button[@type='submit']")).click();      
	         
	    }
	    
	    @Then("the polling station should be successfully created")
	    public void the_polling_station_should_be_successfully_created() 
	    {
	    	WebElement successMessage = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")); 
	         String message = successMessage.getText();
	         Assert.assertEquals("Pool created successfully", message);
	         System.out.println("Pool created successfully");
	         
	    }  
	  }


   

