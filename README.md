# Coding Challenge 

## In this assessment you will be tasked with filling out the functionality of different methods that will be listed further down. 
These methods will require some level of api interactions with the following base url: https://dummy.restapiexample.com.
Please keep the following in mind when doing this assessment: clean coding practices, test driven development, logging, and scalability.
If you are unable to successfully receive responses from the endpoints, mocking the response calls may prove to be helpful.

# note 1: 
In order to use REST conventions, REST endpoints have been created to look like "/employee" etc rather than "getEmployeeX".
In order the requirement for the method names, the EmployeeController has the asked-for methed names on the <i>server-side</i> controller-  as java methods

# note 2: 
Base url  https://dummy.restapiexample.com not set as project is meant to be run on localhost with "gradle bootRun"
Likewise, we are running on http - we don't want certificates in Git/GitLab - to enable https, it will be necessary to follow these steps:https://www.thomasvitale.com/https-spring-boot-ssl-certificate/

# note 3
getHighestSalaryOfEmployees returns an Int of max salary - note, should be JSON, but question/spec said return integer

## to run the application
```
To run the application, use gradle bootRun --scan --stacktrace

To run unit tests, gradle :test --tests "com.example.restservice.EmployeeAPITest"
```

## methods
Endpoints to implement

getAllEmployees()
output - list of employees
description - this should return all employees

getEmployeesByNameSearch()
output - list of employees
description - this should return all employees whose name contains or matches the string input provided

getEmployeeById(string id)
output - employee
description - this should return a single employee

getHighestSalaryOfEmployees()
output - integer of the highest salary
description -  this should return a single integer indicating the highest salary of all employees

getTop10HighestEarningEmployeeNames()
output - list of employees
description -  this should return a list of the top 10 employees based off of their salaries

createEmployee(string name, string salary, string age)
output - string of the status (i.e. success)
description -  this should return a status of success or failed based on if an employee was created



deleteEmployee(String id)
output - the name of the employee that was deleted
description - this should delete the employee with specified id given
