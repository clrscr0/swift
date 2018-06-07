#Author: clarissa.ortiaga@gmail.com
Feature: Login Scenario

  Scenario Outline: Login on the Mercury Tours website
    Given that the user opens the website in "<browser>"
    When the user enter the username "<username>" and password "<password>"
    And the user clicks the login button
    Then the home page should be displayed

    Examples: 
      | browser  | username | password |
      | firefox  | mercury  | mercury  |
      | chrome   | mercury  | mercury  |
      | iexplore | mercury  | mercury  |
