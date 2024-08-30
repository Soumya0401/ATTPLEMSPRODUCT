package stepDefinitions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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



     
  }  
    


        
 












