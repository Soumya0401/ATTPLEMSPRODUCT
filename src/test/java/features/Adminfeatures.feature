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
  #Then  Admin should see the Party Alliance List
  
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
    
    
    @MobileTest
    Scenario: Admin Nominate Candidate for Election
    
    Given Admin nominate a new candidate
    When Admin fills the nominate candidate form with the dataTable:
    
    | User Name                     | Rana                            |
    | Choose Election               | General Election                |
    | Chose Party                   |Janata Dal (United) (JDU)        |
    | Legal Case                    | 1                               |
    
    
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
    | Number of Ward Voters         | 500                               |
    | Ward Leader Contact Number    | 9977665544                         |
    | Incident Reporting            | call +91                           |
    | Security Measure              | Provide security feature to voters |

   #Then the ward should be created successfully


