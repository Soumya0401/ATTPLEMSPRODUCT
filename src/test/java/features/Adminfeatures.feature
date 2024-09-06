Feature: ATTPL EMS Services
  As a user, I want to ensure that the Admin Page login works correctly.

  #Election Management 
 
  Background:
    Given Admin is on the ATTPL EMS Landing Page
    When Admin logs in with the registered mobile number and password
    Then Admin should see the dashboard

   @WebTest
   Scenario: Admin Creates a New Election

  Given Admin creates a new election
  When Admin fills the create election form with the dataTable:
 
    | Election Title                | General Election                         |
    | Election Date                 | 23-09-2024                               |
    | Election Start Time           | 07:13                                    |
    | End Time                      | 18:00                                    |
    | Position                      | Member of Parliament                     |
    | Voting Method                 | Ballot Paper                             |
    | Eligibility Type              | NRI                                      |
    | Nomination Start Date         | 30-08-2024                               |
    | Nomination End Date           | 02-09-2024                               |
    | Election Conduct Description  | Election Conducted in a Peaceful Manner  |
    | Election Title                | MP Election                              |
    
  And Admin clicks on the Create Election button
  #Then Admin should see the Election List
  
  #Party Alliance Managememt
  
  @SmokeTest
  Scenario: Admin Creates Party Alliance 
  Given Admin creates a new party alliance
  #Then the Party Alliance should be created successfully
  
  #Party Management
  
  @RegressionTest
  Scenario: Admin Creates Party  
  
  Given Admin creates a new party 
  When Admin fills the create party form with the dataTable:
  
    | Choose Election               | General Election                         |
    | Party Alliance Name           | BJP                                      |
    | Party Leader                  | Amit Kumar                               |
    | Party Name                    | Bharatiya Janata Party (BJP)             |
    | Party Foundation Year         | 2000                                     |
    | Party Membership Count        | 200                                      |
    | Party Mainfesto               | To do best for country and people        |
    
    #Then the party should be created successfully
    
    
    #Candidate Management
    
  
   Scenario: Admin Register Candidate for Election

  Given Admin registers a new candidate
  When Admin fills the register candidate form with the dataTable:

    | Choose your Product           | EMS                      |
    | Current your Role             | Candidate                |
    | Mobile Number                 | 7733221198               |
    | First Name                    | Rana                     |
    | Last Name                     | Singh                    |
    | Father                        | Father                   |
    | Ward Number                   | 45                       |
    | Panchayat Number              | Jaipur                   |
    | Tehsil Name                   | Jaipur                   |
    | Current Job Type              | Government Employee      |
    | District Name                 | Jaipur                   |
    | State                         | Karnataka                |
    | Password                      | Apple@123                |
    | Confirm Password              | Apple@123                |
    
    #Then the candidate should be created successfully
    
    
   
    Scenario: Admin Nominate Candidate for Election
    
    Given Admin nominate a new candidate
    When Admin fills the nominate candidate form with the dataTable:
    
    | User Name                     | Rana                            |
    | Choose Election               | General Election                |
    | Chose Party                   |Janata Dal (United) (JDU)        |
    | Legal Case                    | 1                               |
    
    #Then the candidate should be created successfully
    
    #Ward Management
    
   Scenario: Admin Creates Ward

   Given Admin creates a new ward
   When Admin fills the Ward Creation form with the dataTable:
                                                  
    | Choose Election               | General Election                   |
    | Ward Number                   | 11                                 |
    | Ward Name                     | Muzaffarpur                        |
    | Ward Street Address           | Near ICICI Bank                    |
    | Ward Postal Code              | 844127                             |
    | District Name                 | Delhi                              |
    | Tehsil                        | Delhi                              |
    | Ward City                     | Delhi                              |
    | Ward State                    | Delhi                              |
    | Ward Country                  | INDIA                              |
    | Number of Ward Voters         | 500                                |
    | Ward Leader Contact Number    | 9977665544                         |
    | Incident Reporting            | call +91                           |
    | Security Measure              | Provide security feature to voters |

   #Then the ward should be created successfully
   
 
   Scenario: Admin Creates Booth

   Given Admin creates a new booth
   When Admin fills the booth creation form with the dataTable:
                                                  
    | Booth Name                    | Muzaffarpur               |
    | Booth Dimensions              | 30                        |
    | Booth Capacity                | 2000                      |
    | Street Address                | Near ICICI Bank           |
    | City                          | Delhi                     |
    | State                         | Bihar                     |
    | Postal Code                   | 844127                    |
    | Country                       | INDIA                     |
    | Ward Name                     | Muzaffarpur               |
   

   #Then the booth should be created successfully
   
   Scenario: Admin Creates Polling Station

   Given Admin creates a new poll
   When Admin fills the poll creation form with the dataTable:
                                                  
    | Booth Name                    | Muzaffarpur               |
    | Polling Station Name          | Muzaffarpur               |
    | Polling Station Capacity      | 2000                      |
    | Number of Booth               | 10                        |
   
   #Then the polling station should be created successfully
   
   
   Scenario: Admin registers voter

   Given Admin registers a new voter
   When Admin fills the voter registration form with the dataTable:
                                                  
    | Mobile Number                 | 7876221199                     |
    | First Name                    | Ramya                          |
    | Last Name                     | Singh                          |
    | Father                        | Father                         |
    | Ward Number                   | 45                             |
    | Panchayat Number              | Jaipur                         |
    | Tehsil Name                   | Jaipur                         |
    | Party Name                    | Bharatiya Janata Party (BJP)   |
    | Current Job Type              | Government Employee            |
    | District Name                 | Jaipur                         |
    | State                         | Karnataka                      |
    | Password                      | Apple@123                      |
    | Confirm Password              | Apple@123                      |
    
   #Then the voter registration should be created successfully
   #Then Admin Fetch the Voter List
   
  
  Scenario: Admin creates a new Survey

  Given Admin creates a new Survey
  When Admin fills the create survey form with the following data:
  
    | Survey Unique Name  | ATTPL TMS Survey                                                          |
    | Survey Title        | TMS Feedback Survey                                                       |
    | Survey Description  | EMS feature is very helpful for Political Party                           |
    | Select Users        | Dinesh Kumar, Kana Ram                                                    |
    | Set duration        | Survey Start - 08/30/2024, 12:00 AM; Survey End - 08/31/2024, 12:00 PM    |
    | Survey Status       | Open                                                                      |
    
   
    Scenario: Admin see the survey response lists
    Given Admin click the Survey Response Lists
    
   
    Scenario: Admin upload the template
    Given Admin upload the new Template
    
   
    Scenario: Admin Create Expense category 
    Given Admin Create a expense category list
    When Admin fills the create Category form with the following data:
    
    | Category Name      |  Election Expense  |
    
    #Then Admin see the Category List
    
    
    Scenario: Admin Create a Expense Claim
    Given Admin Create Expense claim lists
    When Admin fills the create claim form with the following data:
    
    | CategoryName        | Election Expense       |
    | Amount              | 1000000                |
    | Payment Method      | Cheque                 |
    | Purchase Date       | 25-10-2024             |
    | Description         | Expense in Election    |
    
    #Then Admin see the Cliam List
    
    
    Scenario: Admin creates a new user role
    Given The Admin is on the Create Role page
    When  The Admin fills in the create role form with the following data:
    
      | Choose Your Product | LMS               |
      | Add Role Type       | LawyerAssistance  |
    #Then the Admin should see the updated Role List
    
    
   Scenario: Admin registers a new member
   Given Admin registers a new member
   When Admin fills the member registration form with the dataTable:
   
    | Choose Your Product           | LMS                            |
    | Add Role Type                 | Characterd Accountant Client   |                                              
    | Mobile Number                 | 7876221199                     |
    | First Name                    | Abhay                          |
    | Last Name                     | Singh                          |
    | Father                        | Father                         |
    | Ward Number                   | 56                             |
    | Panchayat Number              | Jaipur                         |
    | Tehsil Name                   | Jaipur                         |
    | Current Job Type              | Self Employee                  |
    | District Name                 | Jaipur                         |
    | State                         | Karnataka                      |
    | Password                      | Apple@123                      |
    | Confirm Password              | Apple@123                      |
    
   #Then the menber registration should be created successfully
   #Then Admin Fetch the Member List
   
   
  
  Scenario: Admin Create a Support Ticket
    Given Admin creates a new Support Ticket
    When Admin fills the support Ticket form with the following details:
      | Service Name  | EMS Services                                              |
      | Assign To     | 4567                                                      |                                              
      | Title         | EMS services feature is not working properly              |
      | Priority      | High                                                      |
      | Status        | Pending                                                   |
      | Description   | EMS services Cab Services feature is not working properly |
      
    #Then the ticket should be created successfully
    #And Admin fetches the Ticket List
 
 
  Scenario: Admin creates a Blog
    Given Admin creates a new Blog
    When Admin fills the blog form with the following details:
      | Post Title       | ATTPL LMS BLOGS                                                                                                        |
      | Description      | Legal Management services that help us select the best Lawyer, Vendor, CA                                              |                                                                                                   
      | Content          | Legal Management services that help us select the best Lawyer, Vendor, CA, and they are highly skilled in their field  |
      | Tags             | Technology, Marketing                                                                                                  |
      | Meta Title       | CHOOSE ATTPL FOR BEST SERVICES                                                                                         |
      | Meta Description | Technology Help to groom in IT and Marketing increase the revenue                                                      |
      | Meta Keywords    | Technology, Marketing                                                                                                  |
      
    #Then the blog should be created successfully
    #And Admin fetches the Blog List
    
   
  Scenario: Admin creates a Product List
    Given Admin navigates a new Product List
    When Admin fills the product details form with the following details:
      | Product Name        | IT                     |
      | Product Description | Information Technology |
                                                                                                         
    #Then the product should be created successfully
    #And Admin fetches the Product List
    
    
    
  Scenario: Admin creates a Product Mapping
    Given Admin navigates a new Product Mapping
    When Admin fills the product mapping form with the following details:
      | Choose Product Name        | TMS                    |
      | Choose User Role Name      | Driver                 |
                                                                                                         
    #Then the product mapping should be created successfully
    #And Admin fetches the Product Mapping List
    
    
    
  Scenario: Admin creates a Product Modal
    Given Admin navigates a new Product Modal List
    When Admin fills the product modal form with the following details:
      | Title             | ATTPL IT TEAMS                                    |
      | Description       | Connect ATTPL For best IT Services                |
                                                                                                         
    Then the product modal should be created successfully
    And Admin fetches the Product Modal List
    
    
   Scenario: Admin gives a Suggestion
    Given Admin gives a new Suggestion
    When Admin fills the Suggestion form with the following details:
      | Suggestion             | ATTPLEMS  is best app for student career RoadMap   |
                     
    Then the Suggestion should be submit successfully
    And Admin fetches the Suggestion List
    
  
  Scenario: Admin adds a  contact detail
    Given Admin navigates to the Add Contact page
    When Admin fills the contact details form with the following details:
    
      | Name          | Sanjay Singh                                  |
      | Mobile Number | 8877665441                                    |
      | Email         | sanjay@gmail.com                              |
      | Subject       | For add the Product and its description       |
      | Message       | ATTPLEMS is the best app for Political Party  |
    Then the contact should be created successfully
    And Admin fetches the Contact List
   
    
  Scenario: Admin adds a new emergency helpline number
    Given Admin navigates to the Add Emergency Contact page
    When Admin fills the emergency contact details form with the following details:
    
      | Select Department      | Education Department |
      | Contact Person Name    | Abhay Singh          |
      | Mobile Number          | 7998764312           |
    Then the emergency contact should be created successfully
    And Admin fetches the emergency contact list
    
     @MobileTest
     Scenario: Admin Books a Ambulance
    Given Admin navigates to the Book Ambulance Feature
    When Admin Select the location and Book Ambulance :
    Then the ambulance booking successfully
    And Admin fetches the ambulance list
    

  
    
    
    
    
    
    
    
    
    
    

      
    
    

   
   
   
   

    
        
    
    
    
    
    
    
    

   
   


  
