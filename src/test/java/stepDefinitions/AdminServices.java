package stepDefinitions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


public class AdminServices {

    WebDriver driver;
    
    @Given("Admin is on the ATTPL EMS Landing Page")
    public void admin_is_on_the_attpl_ems_landing_page() 
    {
    	// Launch Browser
        ChromeOptions options = new ChromeOptions();
        // Disable subscription notifications
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to("https://app.attplems.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);      
    }
    
    @When("Admin logs in with the registered mobile number and password")
    public void admin_logs_in_with_the_registered_mobile_number_and_password() 
    {
    	driver.findElement(By.name("userphone")).sendKeys("9905899259");
    	driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
    	driver.findElement(By.name("password")).sendKeys("Password@004");
    	driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
    } 
    
    @Then("Admin should see the dashboard")
    public void admin_should_see_the_dashboard()
    {
    	String pagetitle= driver.getTitle();
    	System.out.println(pagetitle);
    	if(pagetitle.equals("ATTPL : Login"))
    	{
    		System.out.println("Login Successful");	
    	}
    	else
    	{
    		System.out.println("Login Failed");
    	}
    }

    //Create Election
    
    @Given("Admin creates a new election")
    public void admin_creates_a_new_election() 
    {
    	driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'ELECTION LIST DETAILS')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.linkText("New Election")).click();
       
    }

    @When("Admin fills the create election form with the dataTable:")
    public void admin_fills_the_create_election_form_with_the_dataTable(DataTable dataTable) {
        
        List<List<String>> data = dataTable.cells();

        // Filling out the form using the extracted details
        driver.findElement(By.name("electionTitle")).sendKeys(data.get(0).get(1));  // MP Election
        driver.findElement(By.name("electionDate")).sendKeys(data.get(1).get(1));   // 23-09-2024
        driver.findElement(By.name("electionStartTime")).sendKeys(data.get(2).get(1));  // 07:13
        driver.findElement(By.name("electionEndTime")).sendKeys(data.get(3).get(1));    // 18:00
        
     // Retrieve the value for election type from the data table
        String electionType = data.get(4).get(1); // This should be the text you want to select, e.g., "Member of Parliament"

        // Click to reveal the dropdown options
        WebElement electionTypeInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionType']"));
        electionTypeInput.click();  

        // Use the retrieved value in the XPath expression to select the option
        WebElement electionTypeOption = driver.findElement(By.xpath("//li[contains(text(),'" + electionType + "')]"));
        electionTypeOption.click();  // Select the option

     // Retrieve the value for the method from the data table
        String methodValue = data.get(5).get(1); // This should be the text you want to select

        // Click to reveal the dropdown options
        WebElement methodInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionInstrumentUsed']"));
        methodInput.click();  

        // Use the retrieved value in the XPath expression to find the option and click it
        WebElement methodOption = driver.findElement(By.xpath("//li[contains(text(),'" + methodValue + "')]"));
        methodOption.click();  // Select the option
        
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'" + methodValue + "')]")));


     // Retrieve the value for eligibility type from the data table
        String eligibilityTypeValue = data.get(6).get(1); // Example value: "NRI"

        // Click to reveal the dropdown options
        WebElement eligibilityTypeInput = driver.findElement(By.xpath("//input[@id='autocomplete-eligibilityType']"));
        eligibilityTypeInput.click();  

        // Use the retrieved value in the XPath expression to find the option and click it
        WebElement eligibilityTypeOption = driver.findElement(By.xpath("//li[contains(text(),'" + eligibilityTypeValue + "')]"));
        eligibilityTypeOption.click();  // Select the option
        
//        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'" + eligibilityTypeValue + "')]"))) ;      
//  
        driver.findElement(By.name("nominationStart")).sendKeys(data.get(7).get(1));
        driver.findElement(By.name("nominationEnd")).sendKeys(data.get(8).get(1));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        
       driver.findElement(By.name("securityMeasures")).sendKeys(data.get(9).get(1));
       driver.findElement(By.name("electionDescription")).sendKeys(data.get(10).get(1));
    
    }

    @When("Admin clicks on the Create Election button")
    public void admin_clicks_on_the_create_election_button() {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
   
    
    // Party Alliance 
    
    @Given("Admin creates a new party alliance")
    public void admin_creates_a_new_party_alliance() 
    {
    	driver.findElement(By.xpath("//span[contains(text(),'PARTY ALLIANCE MANAGEMENT')]")).click();
    	driver.findElement(By.linkText("New Party Alliance")).click();
    	driver.findElement(By.name("partyAllianceName")).sendKeys("TVC");
    	driver.findElement(By.xpath("//button[@type='submit']")).click();      
    }
    
    //Party
    
    @Given("Admin creates a new party")
    public void admin_creates_a_new_party() 
    {
    	
    	driver.findElement(By.xpath("//span[contains(text(),'PARTY MANAGEMENT')]")).click();
        driver.findElement(By.linkText("New Party")).click();
        
    }
    
    @When("Admin fills the create party form with the dataTable:")
    public void admin_fills_the_create_party_form_with_the_dataTable(DataTable dataTable) 
    {
        
        List<List<String>> data = dataTable.cells();
        
        // Retrieve the value for election type from the data table
        String electionType = data.get(0).get(1);
        
     // Selecting the election type
        WebElement chooseElectionInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionId']"));
        chooseElectionInput.click();
        
        WebElement chooseElectionOption = driver.findElement(By.xpath("//li[contains(text(),'" + electionType + "')]"));
        chooseElectionOption.click();
        
     // Retrieve the value for party alliance from the data table
        String selectalliance = data.get(1).get(1);  

        // Locate the dropdown input field for selecting the party alliance
        WebElement select_alliance = driver.findElement(By.xpath("//input[@id='autocomplete-partyAllianceId']"));
        select_alliance.click();  // Click to reveal the dropdown options

        // Use the retrieved value to locate the dropdown option and select it
        WebElement select_allianceOptions = driver.findElement(By.xpath("//li[contains(text(),'" + selectalliance + "')]"));
        select_allianceOptions.click();  // Select the option
        
     // Retrieve the value for party leader from the data table
        String partyLeader = data.get(2).get(1);  // Assuming the party leader is in the third row (index 2)

        // Locate the input field for the party leader and enter the value
        WebElement party_leader = driver.findElement(By.name("partyLeader"));
        party_leader.sendKeys(partyLeader);

        // Retrieve the value for party name from the data table
        String partyName = data.get(3).get(1);  // Assuming the party name is in the fourth row (index 3)

        // Locate the dropdown input field for selecting the party name
        WebElement select_party = driver.findElement(By.xpath("//input[@id='autocomplete-partyName']"));
        select_party.click();  // Click to reveal the dropdown options

        // Use the retrieved value to locate the dropdown option and select it
        WebElement select_partyOption = driver.findElement(By.xpath("//li[contains(text(),'" + partyName + "')]"));
        select_partyOption.click();  // Select the option
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        
        
        // Copy file path to clipboard
//        StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\download.jpg");
//        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
//
//        // Create Robot instance for keyboard operations
//        Robot rb = new Robot();
//
//        // Simulate Ctrl+V to paste the file path
//        rb.keyPress(KeyEvent.VK_CONTROL);
//        rb.keyPress(KeyEvent.VK_V);
//        rb.keyRelease(KeyEvent.VK_V);
//        rb.keyRelease(KeyEvent.VK_CONTROL);
//
//        // Simulate pressing Enter
//        rb.keyPress(KeyEvent.VK_ENTER);
//        rb.keyRelease(KeyEvent.VK_ENTER);

    

   }	
        
 }












