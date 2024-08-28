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
        userNameInput.click();  
        
        List<WebElement> userNameOptions = driver.findElements(By.xpath("//li[contains(text(),'" + userName + "')]")); 

        for (WebElement option : userNameOptions) {
            String optionText = option.getText();
            System.out.println("Dropdown Option: " + optionText);

            if (optionText.equals(userName)) 
            {
                option.click();
                break;  
            }
        }
        
       String choseElection = data.get(1).get(1);
       WebElement choseElectionInput = driver.findElement(By.id("autocomplete-electionId"));
       choseElectionInput.click();  

       List<WebElement> choseElectionOptions = driver.findElements(By.xpath("//li[contains(text(),'" + choseElection + "')]")); 

      for (WebElement option : choseElectionOptions) {
          String optionText = option.getText();
          System.out.println("Dropdown Option: " + optionText);

          if (optionText.equals(choseElection)) {
              option.click();
              break;  
          }
      }
      
      String choseParty = data.get(2).get(1);
      WebElement chosePartyInput = driver.findElement(By.id("autocomplete-partyId"));
      chosePartyInput.click();

      List<WebElement> chosePartyOptions = driver.findElements(By.xpath("//li[contains(text(),'" + choseParty + "')]")); 

      for (WebElement option : chosePartyOptions) 
      {
        String optionText = option.getText();
        System.out.println("Dropdown Option: " + optionText);

        if (optionText.equals(choseParty)) 
        {
            option.click();
            break;  
        }
    }
    
     String legalCase = data.get(3).get(1);
     WebElement legalCaseInput = driver.findElement(By.id("autocomplete-legalCase"));
     legalCaseInput.click();  

     List<WebElement> legalCaseOptions = driver.findElements(By.xpath("//li[contains(text(),'" + legalCase + "')]")); 

     for (WebElement option : legalCaseOptions) 
     {
        String optionText = option.getText();
        System.out.println("Dropdown Option: " + optionText);

        if (optionText.equals(legalCase)) 
         {
             option.click();
             break;  
         }
    } 
     
     WebElement createCandidateBtn = driver.findElement(By.xpath("//button[@type='submit']"));
     createCandidateBtn.click();
           
    }
     
//////////////////////////////////////////// Ward Management //////////////////////////////////////////////////////////     
 
     @Given("Admins Creates a new wards")
     public void admin_Creates_a_new_wards() 
     {
    	 driver.findElement(By.xpath("//span[contains(text(),'ELECTION MANAGEMENT')]")).click();
    	 driver.findElement(By.xpath("//span[contains(text(),'WARD LIST DETAILS')]")).click();
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         driver.findElement(By.linkText("New Ward")).click();   
     }
     
     @When("Admin fills the Ward Creation form with the dataTable:")
     public void admin_fills_the_Ward_Creation_form_with_the_data_table(DataTable dataTable) 
     {
    	 
     }
     
     
}
        
 












