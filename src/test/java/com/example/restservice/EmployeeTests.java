package com.example.restservice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 Tests for general Employee functionality, JSON parsing etc
 */
public class EmployeeTests {
    final static String twoEmpsJSON = "[{\"id\":12,\"name\":\"Frodo Baggins12\",\"role\":\"thief\",\"salary\":200.0},{\"id\":11,\"name\":\"Bilbo Baggins11\",\"role\":\"burglar\",\"salary\":100.0}]";

    @Test
    void testJSONParseEmployee() {
        Employee[] emps=  Employee.employeeArrayFromJSON(twoEmpsJSON);
        assertEquals(emps.length, 2);
        assertEquals(emps[0].getName(), "Frodo Baggins12");
        assertEquals(emps[1].getName(), "Bilbo Baggins11");
    }

    /*
    Test entity works as local object and test constructor
     */
    @Test
    public void localEmployeeTest() throws Exception {
        String employeeName = "mr test";
        Employee e = new Employee(employeeName, "job", 20);
        System.out.println(e.toString());
        assertEquals(employeeName, e.getName());
    }
}
