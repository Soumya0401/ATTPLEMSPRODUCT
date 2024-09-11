
Feature: ATTPL EMS Candidate Services
  As a Candidate, I want to ensure that the Candidate Features works correctly.

 Background:
    Given Candidate is on the ATTPL EMS landing page
    When the Candidate enters their registered mobile number and password
    Then the Candidate should be successfully logged in and redirected to the dashboard


Scenario: Candidate creates a new ward
Given the candidate navigates to the Ward Creation page.
When the candidate enters the following details into the Ward Creation form:
    | Choose Election               | General Election                                    |
    | Ward Number                   | 65                                                  |
    | Ward Name                     | Kolkata                                             |
    | Ward Street Address           | Near ICICI Bank                                     |
    | Ward Postal Code              | 110543                                              |
    | District Name                 | Kolkata                                             |
    | Tehsil                        | Kolkata                                             |
    | Ward City                     | Kolkata                                             |
    | Ward State                    | West Bengal                                         |
    | Ward Country                  | INDIA                                               |
    | Number of Ward Voters         | 500                                                 |
    | Ward Leader Contact Number    | 9857643211                                          |
    | Incident Reporting            | call +91                                            |
    | Security Measure              | Provide security feature to Ward Members and Voters |
Then the ward should be successfully created.
#And the candidate should be able to view the newly created ward in the ward list

 
  Scenario: Candidate creates a new booth
   Given the candidate navigates to the Booth Creation page
   When the candidate fills in the booth creation form with the following details:
       | Booth Name       | Muzaffarpur            |
       | Booth Dimensions | 30                     |
       | Booth Capacity   | 1000                   |
       | Street Address   | Near ICICI Bank        |
       | City             | Delhi                  |
       | State            | Bihar                  |
       | Postal Code      | 844127                 |
       | Country          | INDIA                  |
       | Ward Name        | Muzaffarpur            |
   Then the booth should be successfully created
  # And the candidate should be able to view the newly created booth in the booth list
  

  Scenario: Candidate creates a new polling station
   Given the candidate navigates to the Polling Station Creation page
   When the candidate fills in the polling station creation form with the following details:
       | Booth Name               | Muzaffarpur        |
       | Polling Station Name      | Muzaffarpur       |
       | Polling Station Capacity  | 2000              |
       | Number of Booths          | 10                |
   Then the polling station should be successfully created
   #And the candidate should be able to view the newly created polling station in the polling station list


   

