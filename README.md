# OrangeHRM Automation

This project automates basic scenarios on the OrangeHRM demo website.

## What is covered
- Login to the system
- Navigate to PIM module
- Add a new employee
- Verify employee is added
- Logout

## Tools used
- Java
- Selenium WebDriver
- TestNG
- Maven

## Project structure
- src → contains test classes and page objects
- .github → contains CI/CD workflow
- pom.xml → project dependencies

## How to run
1. Clone the repo
2. Open the project
3. Run:
   mvn clean test

## Report
After running the tests, check the report here:
test-output/ExtentReport.html
