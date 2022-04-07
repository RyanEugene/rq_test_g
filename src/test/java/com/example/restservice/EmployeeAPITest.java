package com.example.restservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Repository
public class EmployeeAPITest {
    private static final Logger log = LoggerFactory.getLogger(EmployeeAPITest.class);

    private Employee employeeToTest = new Employee("Frodo Baggins12", "thief",200);
    private Employee employeeOneToTest = new Employee("Bilbo Baggins1", "burglar",1);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins1", "burglar", 1)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins2", "thief", 2)));
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins3", "burglar", 3)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins4", "thief", 4)));
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins5", "burglar", 5)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins6", "thief", 6)));
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins7", "burglar", 7)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins8", "thief", 8)));
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins9", "burglar", 9)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins10", "thief", 10)));
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins11", "burglar", 11)));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins12", "thief", 200)));
    }

    @Test
    public void getEmployee() throws Exception {
         //as the IDs can change from test to test, get the employee by name first
        String url = "/employeeByName/" + employeeToTest.getName();
        assertEquals(url, "/employeeByName/Frodo Baggins12");
        ResponseEntity<Employee> response = template.getForEntity(url, Employee.class);
        Employee oneEmp = response.getBody();

        String employeeById = "/employee/" + oneEmp.getId();
        log.info("Calling " + employeeById);
        ResponseEntity<String> response1 = template.getForEntity(employeeById, String.class);
        String json = response1.getBody();
        Employee emp= Employee.employeeFromJSON(json);
        assertEquals(emp.getName(), employeeToTest.getName());
        assertEquals(emp.getRole(), employeeToTest.getRole());
    }

    @Test
    public void testMaxEmployeeSalary() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/maxEmployeeSalary", String.class);

        // note: should really be JSON {"salary":200} but spec/question asked for an integer - breaking JSON return type here!
        assertThat(response.getBody()).isEqualTo("200");
    }

    @Test
    public void testGetEmployeeByName() throws Exception {
        String url = "/employeeByName/" + employeeToTest.getName();
        assertEquals(url, "/employeeByName/Frodo Baggins12");

        ResponseEntity<String> response = template.getForEntity(url, String.class);
        String json = response.getBody();
        Employee emp= Employee.employeeFromJSON(json);

        assertEquals(emp.getName(), employeeToTest.getName());
        assertEquals(emp.getRole(), employeeToTest.getRole());
        assertEquals(emp.getSalary(), employeeToTest.getSalary());
    }

    @Test
    public void testMaxSalary() throws Exception {
        String url = "/maxEmployeeSalary";
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        assertThat(response.getBody()).isEqualTo("200");
    }

    @Test
    public void testTop10Employees() throws Exception {
        String url = "/top10EmployeesBySalary";
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        String json = response.getBody();
        Employee emps[] = Employee.employeeArrayFromJSON(json);
        assertEquals(emps.length, 10);

        // we know that Frodo Baggins12 is THE top earner and Bilbo Baggins3 is lowest if ordered by salary DESC, then top 10
        assertEquals(emps[0].getName(), "Frodo Baggins12");
        assertEquals(emps[9].getName(), "Bilbo Baggins3");
    }

    @Test
    public void testAddEmployees() throws Exception {
        // get all employees
        String url = "/employee";
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        String json = response.getBody();
        Employee emps[] = Employee.employeeArrayFromJSON(json);
        assertEquals(12, emps.length);
        assertEquals(emps[0].getName(), "Bilbo Baggins1");
        assertEquals(emps[11].getName(), "Frodo Baggins12");


        // create employee
        String newName = "new one";
        Employee e = new Employee(newName, "new job", 22);
        String j = e.toString();
        ResponseEntity<Employee> er = template.postForEntity(url, e, Employee.class);
        Employee eOut = er.getBody();
        assertNotNull(eOut);
        assertEquals(eOut.getName(), newName);

        // get all employees again
        response = template.getForEntity(url, String.class);
        json = response.getBody();
        emps = Employee.employeeArrayFromJSON(json);
        assertEquals(emps.length, 13);

        assertEquals(emps[0].getName(), "Bilbo Baggins1");
        assertEquals(emps[12].getName(), eOut.getName());
    }


    @Test
    public void testDeleteEmployees() throws Exception {
        // get all employees
        String allURL = "/employee";
        ResponseEntity<String> response = template.getForEntity(allURL, String.class);
        String json = response.getBody();
        Employee emps[] = Employee.employeeArrayFromJSON(json);
        assertEquals(12, emps.length);
        assertEquals("Bilbo Baggins1", emps[0].getName());
        assertEquals("Frodo Baggins12", emps[11].getName());

        // delete employee
        String deleteURL = "/employee/" + emps[0].getId();
        log.info("Calling " + deleteURL);
        //System.out.println(deleteURL);
        template.delete(deleteURL);

        // get all employees again
        response = template.getForEntity(allURL, String.class);
        json = response.getBody();
        emps = Employee.employeeArrayFromJSON(json);
        assertEquals(11, emps.length);
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("{\"id\":1,\"content\":\"Hello, World!\"}");
    }
}

