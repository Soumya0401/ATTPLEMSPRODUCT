@tag
Feature: Title of your feature
  I want to use this template for my feature file

  
  Scenario: Driver Login with Registered Credentials
    Given Driver is on the ATTPL EMS Landing Page
    When Driver logs in with the registered mobile number and password
    Then Driver should see the dashboard
    And I allow location access
