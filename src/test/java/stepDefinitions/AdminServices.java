package stepDefinitions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


public class AdminServices {

    WebDriver driver;
    
//////////////////////////////////////////// Login Details /////////////////////////////////////////////////////////
    
    
    @Given("Admin is on the ATTPL EMS Landing Page")
    public void admin_is_on_the_attpl_ems_landing_page() 
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to("https://app.attplems.com/");
       //driver.navigate().to("https://appdev.attplems.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);      
    }
    
    @When("Admin logs in with the registered mobile number and password")
    public void admin_logs_in_with_the_registered_mobile_number_and_password() 
    {
        try {
            driver.findElement(By.name("userphone")).sendKeys("9905899259");
            driver.findElement(By.xpath("//button[normalize-space()='Next']")).click();
            driver.findElement(By.name("password")).sendKeys("Password@004");
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
  
    @Then("Admin should see the dashboard")
    public void admin_should_see_the_dashboard() 
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

    
//////////////////////////////////////////// Election Management ////////////////////////////////////////////////////////
    
    
    @Given("Admin creates a new election")
    public void admin_creates_a_new_election() 
    {
    	driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'ELECTION LIST DETAILS')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.linkText("New Election")).click();
       
    }

    @When("Admin fills the create election form with the dataTable:")
    public void admin_fills_the_create_election_form_with_the_dataTable(DataTable dataTable) 
    {
        
        List<List<String>> data = dataTable.cells();

        driver.findElement(By.name("electionTitle")).sendKeys(data.get(0).get(1)); 
        driver.findElement(By.name("electionDate")).sendKeys(data.get(1).get(1));   
        driver.findElement(By.name("electionStartTime")).sendKeys(data.get(2).get(1));  
        driver.findElement(By.name("electionEndTime")).sendKeys(data.get(3).get(1));    
        
        String electionType = data.get(4).get(1); 
        WebElement electionTypeInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionType']"));
        electionTypeInput.click();  
        WebElement electionTypeOption = driver.findElement(By.xpath("//li[contains(text(),'" + electionType + "')]"));
        electionTypeOption.click();  

        String methodValue = data.get(5).get(1); 
        WebElement methodInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionInstrumentUsed']"));
        methodInput.click();  
        WebElement methodOption = driver.findElement(By.xpath("//li[contains(text(),'" + methodValue + "')]"));
        methodOption.click();  
        
        String eligibilityTypeValue = data.get(6).get(1); 

        WebElement eligibilityTypeInput = driver.findElement(By.xpath("//input[@id='autocomplete-eligibilityType']"));
        eligibilityTypeInput.click();  

        WebElement eligibilityTypeOption = driver.findElement(By.xpath("//li[contains(text(),'" + eligibilityTypeValue + "')]"));
        eligibilityTypeOption.click();  
        
        driver.findElement(By.name("nominationStart")).sendKeys(data.get(7).get(1));
        driver.findElement(By.name("nominationEnd")).sendKeys(data.get(8).get(1));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        
       driver.findElement(By.name("securityMeasures")).sendKeys(data.get(9).get(1));
       driver.findElement(By.name("electionDescription")).sendKeys(data.get(10).get(1));

    }

    @When("Admin clicks on the Create Election button")
    public void admin_clicks_on_the_create_election_button() 
    {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        String createmsg = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")).getText(); 

        if (createmsg.equals("Election created successfully")) 
        {
            System.out.println("Election created successfully");
        } 
        else 
        {
            System.out.println("Election not Created Successfully");
        }
    }
   
    
////////////////////////////////////////// Party Alliance Management /////////////////////////////////////////////////////////
    
    
    @Given("Admin creates a new party alliance")
     public void admin_creates_a_new_party_alliance() {
        driver.findElement(By.xpath("//span[contains(text(),'PARTY ALLIANCE MANAGEMENT')]")).click();
        driver.findElement(By.linkText("New Party Alliance")).click();
        driver.findElement(By.name("partyAllianceName")).sendKeys("TVC");
        driver.findElement(By.xpath("//button[@type='submit']")).click();      

        String createmsg = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")).getText(); 

        if (createmsg.equals("Party Alliance created successfully")) 
        {
            System.out.println("Party Alliance created successfully");
        } 
        else 
        {
            System.out.println("Party Alliance not created successfully");
        }
    }

    
///////////////////////////////////////////Party Management ///////////////////////////////////////////////////////
    
   
    @Given("Admin creates a new party")
    public void admin_creates_a_new_party() 
    {
    	
    	driver.findElement(By.xpath("//span[contains(text(),'PARTY MANAGEMENT')]")).click();
        driver.findElement(By.linkText("New Party")).click();
        
    }
    
    @When("Admin fills the create party form with the dataTable:")
    public void admin_fills_the_create_party_form_with_the_dataTable(DataTable dataTable) throws AWTException, InterruptedException 
    {
        
        List<List<String>> data = dataTable.cells();
        
        String electionType = data.get(0).get(1);
        WebElement chooseElectionInput = driver.findElement(By.xpath("//input[@id='autocomplete-electionId']"));
        chooseElectionInput.click();
        WebElement chooseElectionOption = driver.findElement(By.xpath("//li[contains(text(),'" + electionType + "')]"));
        chooseElectionOption.click();
        
        String selectalliance = data.get(1).get(1);  
        WebElement select_alliance = driver.findElement(By.xpath("//input[@id='autocomplete-partyAllianceId']"));
        select_alliance.click();  
        WebElement select_allianceOptions = driver.findElement(By.xpath("//li[contains(text(),'" + selectalliance + "')]"));
        select_allianceOptions.click();
        
        
        String partyLeader = data.get(2).get(1);  
        WebElement party_leader = driver.findElement(By.name("partyLeader"));
        party_leader.sendKeys(partyLeader);
        

        String partyName = data.get(3).get(1);  
        WebElement select_party = driver.findElement(By.xpath("//input[@id='autocomplete-partyName']"));
        select_party.click();  
        WebElement select_partyOption = driver.findElement(By.xpath("//li[contains(text(),'" + partyName + "')]"));
        select_partyOption.click(); 
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        
        driver.findElement(By.xpath("//span[contains(text(),'browse')]")).click();

        StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\BJP.jpg");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        Robot rb = new Robot();
     
        Thread.sleep(1000); 

        // Simulate pressing Ctrl+V to paste the file path
           rb.keyPress(KeyEvent.VK_CONTROL);
           rb.keyPress(KeyEvent.VK_V);
           rb.keyRelease(KeyEvent.VK_V);
           rb.keyRelease(KeyEvent.VK_CONTROL);

        // Simulate pressing Enter to confirm the file selection
           rb.keyPress(KeyEvent.VK_ENTER);
           rb.keyRelease(KeyEvent.VK_ENTER);
     
        Thread.sleep(2000);
    
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.scrollBy(0,300)", "");

       String partyFoundationYear = data.get(4).get(1); 
       WebElement dateButton = driver.findElement(By.xpath("//button[@aria-label='Choose date']//*[name()='svg']//*[name()='path']"));
       dateButton.click();
       WebElement yearToSelect = driver.findElement(By.xpath("//button[normalize-space()='" + partyFoundationYear + "']"));
       yearToSelect.click();

        driver.findElement(By.xpath("//input[@name='partyMembershipCount']")).sendKeys(data.get(5).get(1));
        
        driver.findElement(By.name("partyManifesto")).sendKeys(data.get(6).get(1));
        
        driver.findElement(By.xpath("//button[@type='submit']")).click();
   }
    
    
///////////////////////////////////////Candidate Management ////////////////////////////////////////////////////
    
    
    //Register Candidate For Eletcion
       
    @Given("Admin registers a new candidate")
    public void admin_registers_a_new_candidate() 
    {
        
        driver.findElement(By.xpath("//span[contains(text(),'USER MANAGEMENT')]")).click();
        driver.findElement(By.linkText("USER LIST DETAILS")).click();
        WebElement addNewMember = driver.findElement(By.xpath("//a[contains(text(),'Add New Member')]"));
        addNewMember.click();    
    }
    
    @When("Admin fills the register candidate form with the dataTable:")
    public void admin_fills_the_register_candidate_form_with_the_data_table(DataTable dataTable) 
    {
        List<List<String>> data = dataTable.cells();

        String chooseProduct = data.get(0).get(1);
        WebElement productNameInput = driver.findElement(By.xpath("//input[@placeholder='Choose your Product']"));
        productNameInput.click();  
        WebElement productNameOption = driver.findElement(By.xpath("//li[contains(text(),'" + chooseProduct + "')]"));
        productNameOption.click(); 
        
         String currentRole = data.get(1).get(1);        
         WebElement currentRoleInput = driver.findElement(By.xpath("//input[@placeholder='Choose your Role']"));
         currentRoleInput.click();

          List<WebElement> currentRoleOptions = driver.findElements(By.xpath("//li[contains(text(),'" + currentRole + "')]")); 
          for (WebElement option : currentRoleOptions) 
          {
            String optionText = option.getText();
            System.out.println("Dropdown Option: " + optionText);

            if (optionText.equals(currentRole)) 
            {
                option.click();
                break;  
            }
        }
              
        
        WebElement nextBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        nextBtn.click();

        WebElement mobileNumberInput = driver.findElement(By.name("phone"));
        mobileNumberInput.sendKeys(data.get(2).get(1));

        WebElement firstNameInput = driver.findElement(By.name("firstName"));
        firstNameInput.sendKeys(data.get(3).get(1));

        WebElement lastNameInput = driver.findElement(By.name("lastName"));
        lastNameInput.sendKeys(data.get(4).get(1));

        WebElement fatherNameInput = driver.findElement(By.name("fatherName"));
        fatherNameInput.sendKeys(data.get(5).get(1));
       
        WebElement nextBtn1 = driver.findElement(By.xpath("//button[@type='submit']"));
        nextBtn1.click();

        WebElement wardNumberInput = driver.findElement(By.name("wardNo"));
        wardNumberInput.sendKeys(data.get(6).get(1));

        WebElement panchayatNameInput = driver.findElement(By.name("panchayatName"));
        panchayatNameInput.sendKeys(data.get(7).get(1));

        WebElement tehsilNameInput = driver.findElement(By.name("tehsilName"));
        tehsilNameInput.sendKeys(data.get(8).get(1));

        WebElement nextBtn2 = driver.findElement(By.xpath("//button[@type='submit']"));
        nextBtn2.click();

        String currentJobType = data.get(9).get(1);
        WebElement currentJobInput = driver.findElement(By.id("autocomplete-currentJobTitle"));
        currentJobInput.click();  
        WebElement currentJobOption = driver.findElement(By.xpath("//li[contains(text(),'" + currentJobType + "')]"));
        currentJobOption.click();  

        WebElement districtNameInput = driver.findElement(By.name("districtName"));
        districtNameInput.sendKeys(data.get(10).get(1));

        String state = data.get(11).get(1);
        WebElement stateInput = driver.findElement(By.id("autocomplete-userState"));
        stateInput.click();  

        List<WebElement> userNameOptions = driver.findElements(By.xpath("//li[contains(text(),'" + state + "')]")); // Adjusted XPath based on common patterns

        for (WebElement option : userNameOptions) {
            String optionText = option.getText();
            System.out.println("Dropdown Option: " + optionText);

            if (optionText.equals(state)) 
            {
                option.click();
                break;  
            }
        }
       
        WebElement nextBtn3 = driver.findElement(By.xpath("//button[@type='submit']"));
        nextBtn3.click();

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(data.get(12).get(1));

        WebElement confirmPasswordInput = driver.findElement(By.name("confirmPassword"));
        confirmPasswordInput.sendKeys(data.get(13).get(1));

        WebElement createVoterBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        createVoterBtn.click();
    }
        
        //Nominate the Election Candidate
        
        @Given("Admin nominate a new candidate")
        public void admin_nominate_a_new_candidate() 
        {
        
        driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'CANDIDATE LIST DETAILS')]")).click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.findElement(By.linkText("New Candidate")).click(); 
        
    }
        
     @When("Admin fills the nominate candidate form with the dataTable:")
        public void admin_fills_the_nominate_candidate_form_with_the_data_table(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
         
         String userName = data.get(0).get(1);
         WebElement userNameInput = driver.findElement(By.id("autocomplete-userId"));
         selectDropdownOptions(userNameInput, userName);
         
         
         String choseElection = data.get(1).get(1);
         WebElement choseElectionInput = driver.findElement(By.id("autocomplete-electionId"));
         selectDropdownOptions(choseElectionInput, choseElection);
         
         String choseParty = data.get(2).get(1);
         WebElement chosePartyInput = driver.findElement(By.id("autocomplete-partyId"));
         selectDropdownOptions(chosePartyInput, choseParty);
         
         String legalCase = data.get(3).get(1);
         WebElement legalCaseInput = driver.findElement(By.id("autocomplete-legalCase"));
         selectDropdownOptions(legalCaseInput, legalCase);
           
        WebElement createCandidateBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        createCandidateBtn.click();           
    }
     
     private void selectDropdownOptions(WebElement inputElement, String optionText) {
         inputElement.click();
         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
         for (WebElement option : options) {
             if (option.getText().equals(optionText)) {
                 option.click();
                 break;
             }
         }
     }
     
//////////////////////////////////////////// Ward Management //////////////////////////////////////////////////////////     
 
     @Given("Admin creates a new ward")
     public void admin_creates_a_new_ward() 
     {
         driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'WARD LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Ward")).click();   
     }
     
     @When("Admin fills the Ward Creation form with the dataTable:")
     public void admin_fills_the_Ward_Creation_form_with_the_data_table(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
         
         String electionName = data.get(0).get(1);
         WebElement electionInput = driver.findElement(By.id("autocomplete-electionId"));
         selectDropdownOption1(electionInput, electionName);
         
         driver.findElement(By.name("wardNumber")).sendKeys(data.get(1).get(1));
         
         driver.findElement(By.name("wardName")).sendKeys(data.get(2).get(1));
         
         driver.findElement(By.name("wardStreetAddress")).sendKeys(data.get(3).get(1));
         
         driver.findElement(By.name("wardPostalCode")).sendKeys(data.get(4).get(1));
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,300)", "");
         
         String districtName = data.get(5).get(1);
         WebElement districtInput = driver.findElement(By.id("autocomplete-districtName"));
         selectDropdownOption1(districtInput, districtName);
         
         String tehsilName = data.get(6).get(1);
         WebElement tehsilInput = driver.findElement(By.id("autocomplete-tehsilName"));
         selectDropdownOption1(tehsilInput, tehsilName);
         
         String wardCity = data.get(7).get(1);
         WebElement wardCityInput = driver.findElement(By.id("autocomplete-wardCity"));
         selectDropdownOption1(wardCityInput, wardCity);
         
         String wardState = data.get(8).get(1);
         WebElement wardStateInput = driver.findElement(By.id("autocomplete-wardState"));
         selectDropdownOption1(wardStateInput, wardState);
         
         JavascriptExecutor js1 = (JavascriptExecutor) driver;
         js1.executeScript("window.scrollBy(0,500)", "");
         
         String wardCountry = data.get(9).get(1);
         WebElement wardCountryInput = driver.findElement(By.id("autocomplete-wardCountry"));
         selectDropdownOption1(wardCountryInput, wardCountry);
         
         String wardCapacity = data.get(10).get(1);
         WebElement wardCapacityInput = driver.findElement(By.id("autocomplete-wardCapacity"));
         selectDropdownOption1(wardCapacityInput, wardCapacity);
        
         driver.findElement(By.name("emergencyContactNumber")).sendKeys(data.get(11).get(1));
        
         driver.findElement(By.name("incidentReporting")).sendKeys(data.get(12).get(1));
         
         driver.findElement(By.name("securityMeasures")).sendKeys(data.get(13).get(1));
         
         driver.findElement(By.xpath("//button[@type='submit']")).click();
     }

     private void selectDropdownOption1(WebElement inputElement, String optionText) {
         inputElement.click();
         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
         for (WebElement option : options) {
             if (option.getText().equals(optionText)) {
                 option.click();
                 break;
             }
         }
     }
     
     
/////////////////////////////////////// Booth Management //////////////////////////////////////////////////

     @Given("Admin creates a new booth")
     public void admin_creates_a_new_booth() {
         driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'BOOTH LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Booth")).click();  
     }

     @When("Admin fills the booth creation form with the dataTable:")
     public void admin_fills_the_booth_creation_form_with_the_data_table(DataTable dataTable) 
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

///////////////////////////////////////// Polling Management ////////////////////////////////////////////////////
     
     @Given("Admin creates a new poll")
     public void admin_creates_a_new_poll() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'POLLING LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Poll")).click();  
    	      
     }
     
     @When("Admin fills the poll creation form with the dataTable:")
     public void admin_fills_the_poll_creation_form_with_the_data_table(DataTable dataTable) 
     {
    	 
         List<List<String>> data = dataTable.cells();
         
         String boothName = data.get(0).get(1);
         WebElement boothNameInput = driver.findElement(By.id("autocomplete-boothId"));
         selectDropdownOption2(boothNameInput, boothName);
         
         driver.findElement(By.name("pollingStationName")).sendKeys(data.get(1).get(1));
         
         String pollingCapacity = data.get(2).get(1);
         WebElement pollingCapacityInput = driver.findElement(By.id("autocomplete-pollingCapacity"));
         selectDropdownOption2(pollingCapacityInput, pollingCapacity);
         
         driver.findElement(By.name("numberOfBooth")).sendKeys(data.get(3).get(1));
         
         driver.findElement(By.xpath("//button[@type='submit']")).click();      
         
     } 
     
     private void selectDropdownOption2(WebElement inputElement, String optionText) {
         inputElement.click();
         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
         for (WebElement option : options) {
             if (option.getText().equals(optionText)) {
                 option.click();
                 break;
             }
      }
   }
     
////////////////////////////////////////// Voter Management //////////////////////////////////////////////////////     
     
     @Given("Admin registers a new voter")
     public void admin_registers_a_new_voter() 
     { 	 
         driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'VOTERS LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Voter")).click();  	 
     }
          
     @When("Admin fills the voter registration form with the dataTable:")
     public void admin_fills_the_voter_registration_form_with_the_data_table(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
          
         WebElement mobileNumberInput = driver.findElement(By.name("phone"));
         mobileNumberInput.sendKeys(data.get(0).get(1));

         WebElement firstNameInput = driver.findElement(By.name("firstName"));
         firstNameInput.sendKeys(data.get(1).get(1));

         WebElement lastNameInput = driver.findElement(By.name("lastName"));
         lastNameInput.sendKeys(data.get(2).get(1));

         WebElement fatherNameInput = driver.findElement(By.name("fatherName"));
         fatherNameInput.sendKeys(data.get(3).get(1));
         
         WebElement nextBtn1 = driver.findElement(By.xpath("//button[@type='submit']"));
         nextBtn1.click();

         WebElement wardNumberInput = driver.findElement(By.name("wardNo"));
         wardNumberInput.sendKeys(data.get(4).get(1));

         WebElement panchayatNameInput = driver.findElement(By.name("panchayatName"));
         panchayatNameInput.sendKeys(data.get(5).get(1));

         WebElement tehsilNameInput = driver.findElement(By.name("tehsilName"));
         tehsilNameInput.sendKeys(data.get(6).get(1));
         
         String partyName = data.get(7).get(1);
         WebElement partyNameInput = driver.findElement(By.id("autocomplete-partyId"));
         selectDropdownOption3(partyNameInput, partyName);
         
         WebElement nextBtn2 = driver.findElement(By.xpath("//button[@type='submit']"));
         nextBtn2.click();
         
         String currentJob = data.get(8).get(1);
         WebElement currentJobInput = driver.findElement(By.id("autocomplete-currentJobTitle"));
         selectDropdownOption3(currentJobInput, currentJob);
                   
         WebElement districtNameInput = driver.findElement(By.name("districtName"));
         districtNameInput.sendKeys(data.get(9).get(1));

         String state = data.get(10).get(1);
         WebElement stateInput = driver.findElement(By.id("autocomplete-userState"));
         selectDropdownOption3(stateInput, state); 

         WebElement nextBtn3 = driver.findElement(By.xpath("//button[@type='submit']"));
         nextBtn3.click();

         WebElement passwordInput = driver.findElement(By.name("password"));
         passwordInput.sendKeys(data.get(11).get(1));

         WebElement confirmPasswordInput = driver.findElement(By.name("confirmPassword"));
         confirmPasswordInput.sendKeys(data.get(12).get(1));

         driver.findElement(By.xpath("(//button[normalize-space()='Create New Voter'])[1]")).click();       
             
     }
         
     private void selectDropdownOption3(WebElement inputElement, String optionText) {
         inputElement.click();
         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
         for (WebElement option : options) {
             if (option.getText().equals(optionText)) {
                 option.click();
                 break;
             }
         }      
     }
     
///////////////////////////////////////// Survey Management /////////////////////////////////////////////////////
    
     @Given("Admin creates a new Survey")
     public void admin_creates_a_new_survey() 
     {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    	 WebElement electionManagement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")));
    	 electionManagement.click();

    	 WebElement surveyManagement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'SURVEY MANAGEMENT')]")));
    	 surveyManagement.click();

    	 WebElement surveyListDetails = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'SURVEY LIST DETAILS')]")));
    	 surveyListDetails.click();
    	 
    	 WebElement newSurveyButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Survey")));
    	newSurveyButton.click();
	 
     }
     
     @When("Admin fills the create survey form with the following data:")
     public void admin_fills_the_create_survey_form_with_the_following_data(DataTable dataTable) {

         List<List<String>> data = dataTable.cells();

         driver.findElement(By.name("surveyName")).sendKeys(data.get(0).get(1));

         driver.findElement(By.name("surveyTitle")).sendKeys(data.get(1).get(1));

         driver.findElement(By.name("surveyDescription")).sendKeys(data.get(2).get(1));

         WebElement selectUsersButton = driver.findElement(By.xpath("//button[contains(text(),'Select All Users')]"));
         selectUsersButton.click();
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,500)", "");

         driver.findElement(By.xpath("(//button[normalize-space()='Add duration'])[1]")).click();
         
         WebElement startDateElement = driver.findElement(By.xpath("(//button[contains(@aria-label,'Choose date')])[1]"));
         startDateElement.sendKeys(data.get(4).get(1).split(";")[0]);

         WebElement endDateElement = driver.findElement(By.xpath("(//button[contains(@aria-label,'Choose date')])[2]"));
         endDateElement.sendKeys(data.get(4).get(1).split(";")[1]);
         
         driver.findElement(By.xpath("(//button[normalize-space()='Done'])[1]")).click();

         WebElement surveyStatusElement = driver.findElement(By.xpath("//input[@value='Open']"));
         surveyStatusElement.click();
         
         driver.findElement(By.xpath("//span[@class='MuiButton-icon MuiButton-startIcon MuiButton-iconSizeMedium css-1l6c7y9']//*[name()='svg']")).click();
         
         driver.findElement(By.name("questionDescription")).sendKeys("Is EMS Software User-Friendly or Not?");
         
         driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiAutocomplete-input MuiAutocomplete-inputFocused css-epd5gc']")).sendKeys("Yes || No");
         
         driver.findElement(By.xpath("(//button[normalize-space()='Done'])[1]")).click();
         
         driver.findElement(By.xpath("(//button[normalize-space()='Create Survey'])[1]")).click();
         
         
     }
     
     @Given("Admin click the Survey Response Lists")
     public void admin_click_the_survey_response_lists() throws InterruptedException 
     {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    	 WebElement electionManagement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")));
    	 electionManagement.click();

    	 WebElement surveyManagement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'SURVEY MANAGEMENT')]")));
    	 surveyManagement.click();
    	 
    	 WebElement surveyResponseList = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'SURVEY RESPONSE LIST DETAILS')]")));
    	 surveyResponseList.click();
    	 
    	 Thread.sleep(1000);
    	 
    	 JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,700)", "");
         
         Thread.sleep(3000);
         
         WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@title,'Go to next page')]//*[name()='svg']")));
         nextPageButton.click();   	        
     }  
     
////////////////////////////////////////////// Template Management //////////////////////////////////////////////     
     
     @Given("Admin upload the new Template")
     public void admin_upload_the_new_template() throws AWTException, InterruptedException 
     {
    	 
    	 driver.findElement(By.xpath("//span[contains(text(),'SOCIAL MEDIA MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'TEMPLATE LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.xpath("(//*[name()='svg'][@class='component-iconify MuiBox-root css-1t9pz9x iconify iconify--mingcute'])[1]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'browse')]")).click();

         StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\BJP.jpg");
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

         Robot rb = new Robot();
      
         Thread.sleep(1000); 

         // Simulate pressing Ctrl+V to paste the file path
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);

         // Simulate pressing Enter to confirm the file selection
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
            
            driver.findElement(By.xpath("//button[normalize-space()='Upload']")).click();  	 
     }
     
/////////////////////////////////////////// Expense Management System ////////////////////////////////////////////////////////////////
         
     @Given("Admin Create a expense category list")
     public void admin_create_a_expense_category_list() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'EXPENSE MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'CATEGORY LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Category")).click();  	 
    	 
         
     }
     @When("Admin fills the create Category form with the following data:")
     public void admin_fills_the_create_category_form_with_the_following_data(DataTable dataTable) 
     {
    	 List<List<String>> data = dataTable.cells();
    	 WebElement categoryDeatils = driver.findElement(By.name("expenseCategoryName"));
    	 categoryDeatils.sendKeys(data.get(0).get(1));
    	 
    	 driver.findElement(By.xpath("(//button[normalize-space()='Create Category'])[1]")).click();
    	       
     }
//     @Then("Admin see the Category List")
//     public void admin_see_the_category_list() {

//     }
     
     @Given("Admin Create Expense claim lists")
     public void admin_create_expense_claim_lists() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'EXPENSE MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'CLAIM LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Claim")).click();  	 
    	          
     }
     @When("Admin fills the create claim form with the following data:")
     public void admin_fills_the_create_claim_form_with_the_following_data(DataTable dataTable) 
     {
    	 List<List<String>> data = dataTable.cells();
    	 
    	 String categoryName = data.get(0).get(1);
         WebElement categoryNameInput = driver.findElement(By.id("autocomplete-expenseCategoryId"));
         selectDropdownOption4(categoryNameInput, categoryName);
    	 
    	 WebElement amount = driver.findElement(By.name("amount"));
    	 amount.sendKeys(data.get(1).get(1));
    	 
    	 String paymentMethod = data.get(2).get(1);
         WebElement paymentMethodInput = driver.findElement(By.id("autocomplete-paymentMethod"));
         selectDropdownOption4(paymentMethodInput, paymentMethod);
         
         String purchaseDate = data.get(3).get(1);
         WebElement purchaseDateInput = driver.findElement(By.id("purchaseDate"));
         selectDropdownOption4(purchaseDateInput, purchaseDate);
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,300)", "");
         
         WebElement description = driver.findElement(By.name("description"));
         description.sendKeys(data.get(4).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Create Claim'])[1]")).click();    
     }
     
     private void selectDropdownOption4(WebElement inputElement, String optionText) {
         inputElement.click();
         List<WebElement> options = driver.findElements(By.xpath("//li[contains(text(),'" + optionText + "')]"));
         for (WebElement option : options) {
             if (option.getText().equals(optionText)) {
                 option.click();
                 break;
             }
         }      
     }
//     @Then("Admin see the Cliam List")
//     public void admin_see_the_cliam_list() {
//         // Write code here that turns the phrase above into concrete actions
//         throw new io.cucumber.java.PendingException();
//     }
     
/////////////////////////////////////////////User Management System //////////////////////////////////////////     
     
     @Given("The Admin is on the Create Role page")
     public void the_admin_is_on_the_create_role_page() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'USER MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'USER ROLE LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Role")).click();  	 
     }
     
     @When("The Admin fills in the create role form with the following data:")
     public void the_admin_fills_in_the_create_role_form_with_the_following_data(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
    	 
    	 String createRole = data.get(0).get(1);
         WebElement createRoleInput = driver.findElement(By.id("autocomplete-productId"));
         selectDropdownOption(createRoleInput, createRole);
         
         WebElement addRole = driver.findElement(By.name("userRoleType"));
         addRole.sendKeys(data.get(1).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Add New Role Type'])[1]")).click();  
                 
     }
     
//     @Then("the Admin should see the updated Role List")
//     public void the_admin_should_see_the_updated_role_list() 
//     {
//    	 
//         
//     }
     
     
     @Given("Admin registers a new member")
     public void admin_registers_a_new_member() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'USER MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'USER LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("Add New Member")).click();  
         
     }
     @When("Admin fills the member registration form with the dataTable:")
     public void admin_fills_the_member_registration_form_with_the_data_table(DataTable dataTable) 
     {
    	 
        List<List<String>> data = dataTable.cells();
    	 
    	 String chooseProduct = data.get(0).get(1);
         WebElement chooseProductInput = driver.findElement(By.id("autocomplete-productId"));
         selectDropdownOption(chooseProductInput, chooseProduct);
         
         String currentRole = data.get(1).get(1);
         WebElement currentRoleInput = driver.findElement(By.id("autocomplete-RoleId"));
         selectDropdownOption(currentRoleInput, currentRole);
         
         driver.findElement(By.xpath("(//button[normalize-space()='Next'])[1]")).click();
         
         WebElement mobileNumberInput = driver.findElement(By.name("phone"));
         mobileNumberInput.sendKeys(data.get(2).get(1));

         WebElement firstNameInput = driver.findElement(By.name("firstName"));
         firstNameInput.sendKeys(data.get(3).get(1));

         WebElement lastNameInput = driver.findElement(By.name("lastName"));
         lastNameInput.sendKeys(data.get(4).get(1));

         WebElement fatherNameInput = driver.findElement(By.name("fatherName"));
         fatherNameInput.sendKeys(data.get(5).get(1));
         
         WebElement nextBtn = driver.findElement(By.xpath("(//button[normalize-space()='Next'])[1])"));
         nextBtn.click();

         WebElement wardNumberInput = driver.findElement(By.name("wardNo"));
         wardNumberInput.sendKeys(data.get(6).get(1));

         WebElement panchayatNameInput = driver.findElement(By.name("panchayatName"));
         panchayatNameInput.sendKeys(data.get(7).get(1));

         WebElement tehsilNameInput = driver.findElement(By.name("tehsilName"));
         tehsilNameInput.sendKeys(data.get(8).get(1));
         
         WebElement nextBtn1 = driver.findElement(By.xpath("(//button[normalize-space()='Next'])[1])"));
         nextBtn1.click();
         
         String currentJob = data.get(9).get(1);
         WebElement currentJobInput = driver.findElement(By.id("autocomplete-currentJobTitle"));
         selectDropdownOption3(currentJobInput, currentJob);
                   
         WebElement districtNameInput = driver.findElement(By.name("districtName"));
         districtNameInput.sendKeys(data.get(10).get(1));

         String state = data.get(11).get(1);
         WebElement stateInput = driver.findElement(By.id("autocomplete-userState"));
         selectDropdownOption3(stateInput, state); 

         WebElement nextBtn2 = driver.findElement(By.xpath("(//button[normalize-space()='Next'])[1])"));
         nextBtn2.click();
         
         WebElement passwordInput = driver.findElement(By.name("password"));
         passwordInput.sendKeys(data.get(12).get(1));

         WebElement confirmPasswordInput = driver.findElement(By.name("confirmPassword"));
         confirmPasswordInput.sendKeys(data.get(13).get(1));

         driver.findElement(By.xpath("(//button[normalize-space()='Create New Voter'])[1]")).click();       
                
     }
     
//     @Then("the menber registration should be created successfully")
//     public void the_menber_registration_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin Fetch the Member List")
//     public void admin_fetch_the_member_list() 
//     {
//         
//     }


////////////////////////////////////////////// Support Ticket Management ///////////////////////////////////////

     @Given("Admin creates a new Support Ticket")
     public void admin_creates_a_new_support_ticket() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'SUPPORT TICKET LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Support Ticket")).click();  
   	        
     }
     @When("Admin fills the support Ticket form with the following details:")
     public void admin_fills_the_support_ticket_form_with_the_following_details(DataTable dataTable) throws AWTException, InterruptedException 
     {
        List<List<String>> data = dataTable.cells();
    	 
         WebElement serviceName = driver.findElement(By.name("service_name"));
         serviceName.sendKeys(data.get(0).get(1));
         
         WebElement assignTo = driver.findElement(By.id("assignee_id"));
         assignTo.sendKeys(data.get(1).get(1));
          
         WebElement issueTitle = driver.findElement(By.name("issue_title"));
         issueTitle.sendKeys(data.get(2).get(1));
             
         String choosePriority = data.get(3).get(1);
         WebElement choosePriorityInput = driver.findElement(By.id("autocomplete-priority"));
         selectDropdownOption(choosePriorityInput, choosePriority);
         
         String statusChange = data.get(4).get(1);
         WebElement statusChangeInput = driver.findElement(By.id("autocomplete-priority"));
         selectDropdownOption(statusChangeInput, statusChange);
         
         WebElement description = driver.findElement(By.name("issue_description"));
         description.sendKeys(data.get(5).get(1));
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,300)", "");
         
         driver.findElement(By.xpath("//span[contains(text(),'browse')]")).click();

         StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\BJP.jpg");
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

         Robot rb = new Robot();
      
         Thread.sleep(1000); 

         // Simulate pressing Ctrl+V to paste the file path
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);

         // Simulate pressing Enter to confirm the file selection
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER); 
            
           driver.findElement(By.xpath("(//button[normalize-space()='Create Support Ticket'])[1]")).click();           
     }
     
    @Then("the ticket should be created successfully")
     public void the_ticket_should_be_created_successfully() {
    	    // Assuming there's a success message or a specific element that confirms ticket creation
    	    WebElement successMessage = driver.findElement(By.xpath("//div[contains(@class,'alert-success')]"));
    	    Assert.assertTrue("Ticket creation success message is not displayed", successMessage.isDisplayed());

    	    String expectedMessage = "Support Ticket created successfully";
    	    String actualMessage = successMessage.getText();
    	    Assert.assertEquals("Ticket creation message mismatch", expectedMessage, actualMessage);
    	}

    	@Then("Admin fetches the Ticket List")
    	public void admin_fetches_the_ticket_list() {
    	    // Assuming there's a button or a link to navigate to the Ticket List
    	    WebElement ticketListButton = driver.findElement(By.xpath("//a[normalize-space()='Ticket List']"));
    	    ticketListButton.click();

    	    // Verifying the ticket is listed
    	    List<WebElement> ticketRows = driver.findElements(By.xpath("//table[@id='ticket-table']//tr"));
    	    Assert.assertTrue("Ticket list is empty or not displayed", ticketRows.size() > 0);

    	    // Optionally, verify specific details of the created ticket
    	    boolean ticketFound = false;
    	    for (WebElement row : ticketRows) {
    	        String ticketName = row.findElement(By.xpath("//td[contains(text(),'EMS services feature is not working properly')]")).getText();
    	        if (ticketName.contains("EMS services feature is not working properly")) {
    	            ticketFound = true;
    	            break;
    	        }
    	    }
    	    Assert.assertTrue("Created ticket is not found in the list", ticketFound);
    	}
     
////////////////////////////////////////////////////Blog Management System //////////////////////////////////////////     

     @Given("Admin creates a new Blog")
     public void admin_creates_a_new_blog() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'BLOG LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Post")).click();     
     }
     @When("Admin fills the blog form with the following details:")
     public void admin_fills_the_blog_form_with_the_following_details(DataTable dataTable) 
     {
    	 
         List<List<String>> data = dataTable.cells();
    	 
         WebElement postTitle = driver.findElement(By.name("postTitle"));
         postTitle.sendKeys(data.get(0).get(1));
         
         WebElement description = driver.findElement(By.name("description"));
         description.sendKeys(data.get(1).get(1));
          
         WebElement contentDesc = driver.findElement(By.xpath("//div[@class='ql-editor ql-blank']"));
         contentDesc.sendKeys(data.get(2).get(1));
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,500)", "");
              
         String tags = data.get(3).get(1);
         WebElement tagsInput = driver.findElement(By.id("autocomplete-tag"));
         selectDropdownOption(tagsInput, tags);
         
         WebElement metaTitle = driver.findElement(By.name("metaTitle"));
         metaTitle.sendKeys(data.get(4).get(1));
         
         JavascriptExecutor js1 = (JavascriptExecutor) driver;
         js1.executeScript("window.scrollBy(0,300)", "");
         
         WebElement metaDescription = driver.findElement(By.name("metaDescription"));
         metaDescription.sendKeys(data.get(5).get(1));
         
         String metaKeyword = data.get(6).get(1);
         WebElement metaKeywordInput = driver.findElement(By.xpath("autocomplete-metaKeyword"));
         selectDropdownOption(metaKeywordInput, metaKeyword);
         
         driver.findElement(By.xpath("(//button[normalize-space()='Create Post'])[1]")).click();               
     }
//     @Then("the blog should be created successfully")
//     public void the_blog_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Blog List")
//     public void admin_fetches_the_blog_list() 
//     {
//         
//     }


//////////////////////////////////////// Product Management ////////////////////////////////////////////////////
  
     @Given("Admin navigatess a new Product List")
     public void admin_navigates_a_new_product_list() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'PRODUCT LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Product")).click();  
    	      
     }
     
     @When("Admin fills the product details form with the following details:")
     public void admin_fills_the_product_details_form_with_the_following_details(DataTable dataTable) 
     {
    	 List<List<String>> data = dataTable.cells();
    	 
    	 WebElement chooseProductName = driver.findElement(By.name("productName"));
    	 chooseProductName.sendKeys(data.get(0).get(1));
         
         WebElement productDescription = driver.findElement(By.name("productDescription"));
         productDescription.sendKeys(data.get(1).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Add New Product'])[1]")).click();   
           
     }
//     @Then("the product should be created successfully")
//     public void the_product_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Product List")
//     public void admin_fetches_the_product_list() 
//     {
//         
//     }
     
     
     
     @Given("Admin navigates a new Product Mapping")
     public void admin_navigates_a_new_product_mapping() 
     { 
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'PRODUCT ROLE MAPPING LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("Mapping Roles")).click();  
         
     }
     @When("Admin fills the product mapping form with the following details:")
     public void admin_fills_the_product_mapping_form_with_the_following_details(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
    	 
         String chooseProductName = data.get(0).get(1);
         WebElement chooseProductNameInput = driver.findElement(By.id("autocomplete-productId"));
         selectDropdownOption(chooseProductNameInput, chooseProductName);
         
         String usernameRole = data.get(1).get(1);
         WebElement usernameRoleInput = driver.findElement(By.id("autocomplete-userRoleId"));
         selectDropdownOption(usernameRoleInput, usernameRole);
         
         driver.findElement(By.xpath("(//button[normalize-space()='Modify Roles'])[1]")).click();   
     }
    	 
//     @Then("the product mapping should be created successfully")
//     public void the_product_mapping_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Product Mapping")
//     public void admin_fetches_the_product_mapping() 
//     {
//    	 
//
//
//     }
     
     @Given("Admin navigates a new Product Modal List")
     public void admin_navigates_a_new_product_modal_list() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MODAL LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Model")).click();  	 
     }
     @When("Admin fills the product modal form with the following details:")
     public void admin_fills_the_product_modal_form_with_the_following_details(DataTable dataTable) throws AWTException, InterruptedException 
     {

         List<List<String>> data = dataTable.cells();
    	 
         WebElement modalTitle = driver.findElement(By.name("title"));
         modalTitle.sendKeys(data.get(0).get(1));
         
         WebElement modalDescription = driver.findElement(By.name("description"));
         modalDescription.sendKeys(data.get(1).get(1));
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,300)", "");
         
         driver.findElement(By.xpath("//span[contains(text(),'browse')]")).click();

         StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\BJP.jpg");
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

         Robot rb = new Robot();
      
         Thread.sleep(1000); 

         // Simulate pressing Ctrl+V to paste the file path
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);

         // Simulate pressing Enter to confirm the file selection
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER); 
            
            
            driver.findElement(By.xpath("(//button[normalize-space()='Create Model'])[1]")).click();
               
     
//    	WebElement navBar = driver.findElement(By.xpath("//nav[@id='nav-section-vertical']"));
//    	JavascriptExecutor js = (JavascriptExecutor) driver;
//    	js.executeScript("window.scrollBy(0,250)", "");
    	
    	
     }
     
//     @Then("the product modal should be created successfully")
//     public void the_product_modal_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Product Modal List")
//     public void admin_fetches_the_product_modal_list() 
//     {
//    	 
//     }
//    
     @Given("Admin navigates a new Product Feature List")
     public void admin_navigates_a_new_product_feature_list() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'PRODUCT FEATURE LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Work")).click();  	
         
     }
     @When("Admin fills the product Feature form with the following details:")
     public void admin_fills_the_product_feature_form_with_the_following_details(DataTable dataTable) throws AWTException, InterruptedException 
     {
         List<List<String>> data = dataTable.cells();
    	 
         WebElement featureTitle = driver.findElement(By.name("title"));
         featureTitle.sendKeys(data.get(0).get(1));
         
         WebElement featureDescription = driver.findElement(By.name("description"));
         featureDescription.sendKeys(data.get(1).get(1));
         
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,300)", "");
         
         driver.findElement(By.xpath("//span[contains(text(),'browse')]")).click();

         StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\BJP.jpg");
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

         Robot rb = new Robot();
      
         Thread.sleep(1000); 

         // Simulate pressing Ctrl+V to paste the file path
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);

         // Simulate pressing Enter to confirm the file selection
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER); 
            
            
            driver.findElement(By.xpath("(//button[normalize-space()='Create Work'])[1]")).click();
               
         
     }
//     @Then("the product features should be created successfully")
//     public void the_product_features_should_be_created_successfully() 
//     {
//       
//     }
//     @Then("Admin fetches the Product Features List")
//     public void admin_fetches_the_product_features_list() 
//     {
//    	 
//         
//     }

     @Given("Admin gives a new Suggestion")
     public void admin_gives_a_new_suggestion() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'PRODUCT SUGGESTION BOX LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Suggestion")).click();  	
         
     }
     @When("Admin fills the Suggestion form with the following details:")
     public void admin_fills_the_suggestion_form_with_the_following_details(DataTable dataTable) 
     {
    	 
         List<List<String>> data = dataTable.cells();
    	 
         WebElement featureTitle = driver.findElement(By.name("feedbackMessage"));
         featureTitle.sendKeys(data.get(0).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Create Work'])[1]")).click();   
     }
     
//     @Then("the Suggestion should be submit successfully")
//     public void the_suggestion_should_be_submit_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Suggestion List")
//     public void admin_fetches_the_suggestion_list() 
//     {
//         
//     }
     
     @Given("Admin navigates to the Add Contact page")
     public void admin_navigates_to_the_add_contact_page() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'PRODUCT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'CONTACT DETAILS LIST')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Contact")).click();  	
             
     }
     @When("Admin fills the contact details form with the following details:")
     public void admin_fills_the_contact_details_form_with_the_following_details(DataTable dataTable) 
     {
         List<List<String>> data = dataTable.cells();
    	 
         WebElement contactName = driver.findElement(By.name("name"));
         contactName.sendKeys(data.get(0).get(1));
         
         WebElement contactMobile = driver.findElement(By.name("mobile"));
         contactMobile.sendKeys(data.get(1).get(1));
         
         WebElement contactEmail = driver.findElement(By.name("email"));
         contactEmail.sendKeys(data.get(2).get(1));
         
         WebElement contactSubject = driver.findElement(By.name("subject"));
         contactSubject.sendKeys(data.get(3).get(1));
         
         WebElement contactMesssage = driver.findElement(By.name("message"));
         contactMesssage.sendKeys(data.get(4).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Submit Now'])[1]")).click(); 
    	 
         
     }
//     @Then("the contact should be created successfully")
//     public void the_contact_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the Contact List")
//     public void admin_fetches_the_contact_list() 
//     {
//         
//     }
//     @Then("the contact list should contain the new contact {string}")
//     public void the_contact_list_should_contain_the_new_contact(String string) 
//     {
//         
//     }

/////////////////////////////////// Emergency Services ////////////////////////////////////////////////////////     

     @Given("Admin navigates to the Add Emergency Contact page")
     public void admin_navigates_to_the_add_emergency_contact_page() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),' EMERGENCY SERVICES')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'EMERGENCY HELPLINE NUMBER')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("Add Emergency Contact")).click();
         
     }
     @When("Admin fills the emergency contact details form with the following details:")
     public void admin_fills_the_emergency_contact_details_form_with_the_following_details(DataTable dataTable) 
     {
    	 
         List<List<String>> data = dataTable.cells();
    	 
         String selectDepartment = data.get(0).get(1);
         WebElement selectDepartmentInput = driver.findElement(By.id("autocomplete-departmentName"));
         selectDropdownOption(selectDepartmentInput, selectDepartment);
         
         WebElement contactName = driver.findElement(By.name("contactName"));
         contactName.sendKeys(data.get(1).get(1));
         
         WebElement contactNumber = driver.findElement(By.name("phoneNumber"));
         contactNumber.sendKeys(data.get(2).get(1));
         
         driver.findElement(By.xpath("(//button[normalize-space()='Create'])[1]")).click(); 
         
     }
//     @Then("the emergency contact should be created successfully")
//     public void the_emergency_contact_should_be_created_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the emergency contact list")
//     public void admin_fetches_the_emergency_contact_list() 
//     {
//         
//         
//     }

////////////////////////////////////Travel Management System ///////////////////////////////////////////////////
    
     @Given("Admin navigates to the Book Ambulance Feature")
     public void admin_navigates_to_the_book_ambulance_feature() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'TRANSPORT MANAGEMENT')]")).click();
         driver.findElement(By.xpath("//span[contains(text(),'BOOK AMBULANCE')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         
     }
     
     @When("Admin Select the location and Book Ambulance :")
     public void admin_select_the_location_and_book_ambulance() throws InterruptedException 
     {
    	 Map<String, Object> cordinateMap = new HashMap<String, Object>();
    	 cordinateMap.put("latitude",  26.12975);
    	 cordinateMap.put("longitude", 85.37753);
    	 cordinateMap.put("accuracy", 1);
    	 
    	 ((ChromeDriver)driver).executeCdpCommand("Emulation.setGeolocationOverride", cordinateMap);
    	 
    	 
         JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0,500)", "");
          
         Thread.sleep(2000);


     }
//     @Then("the ambulance booking successfully")
//     public void the_ambulance_booking_successfully() 
//     {
//         
//     }
//     @Then("Admin fetches the ambulance list")
//     public void admin_fetches_the_ambulance_list() 
//     {
//         
//     }   
         
     
  }  
    


        
 












