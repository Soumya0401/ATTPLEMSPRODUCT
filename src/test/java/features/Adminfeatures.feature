Feature: ATTPL EMS Services
  As a user, I want to ensure that the Admin Page login works correctly.

  Background:
    Given Admin is on the ATTPL EMS Landing Page
    When Admin logs in with the registered mobile number and password
    Then Admin should see the dashboard

   @SmokeTest
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
  
  #Party Alliance
  
  Scenario: Admin Creates Party Alliance 
  Given Admin creates a new party alliance
  #Then  Admin should see the Party Alliance List
  
  #Party
  
  @RegressionTest
  Scenario: Admin Creates Party  
  
  Given Admin creates a new party 
  When Admin fills the create party form with the dataTable:
  
    | Choose Election               | State Election                           |
    | Party Alliance Name           | BJP                                      |
    | Party Leader                  | Chirag Paswan                            |
    | Party Name                    | Bharatiya Janata Party (BJP)             |
    | Party Foundation Year         | 2000                                     |
    | Party Membership Count        | 200                                      |
    | Party Mainfesto               | To do best for country and people        |
    
   
  

