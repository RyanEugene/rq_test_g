package com.example.restservice;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @SequenceGenerator(name= "CLIENT_SEQUENCE", sequenceName = "CLIENT_SEQUENCE_ID", initialValue=1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="CLIENT_SEQUENCE")
    private Long id;
    //private @Id @GeneratedValue Long id;
    private String name;
    private String role;
    private int salary;

    Employee() {}

    Employee(String name, String role, int salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    Employee(Long id, String name, String role, int salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    Employee(Object[] o) {
        this.id = ((BigInteger)o[0]).longValue();
        this.name = (String)o[1];
        this.role = (String)o[2];
        this.salary = (int)o[3];
    }

    /**
     * Creates a list of Employee objects from a JSON
     * @param json
     * @return a List of Employee objects
     */
    public static Employee[] employeeArrayFromJSON(String json) {
        Gson g = new Gson();
        Employee emps[] = g.fromJson(json, Employee[].class);
        return emps;
    }

    /**
     * Creates an Employee object from a JSON
     * @param json
     * @return an Employee object
     */
    public static Employee employeeFromJSON(String json) {
        Gson g = new Gson();
        Employee emp = g.fromJson(json, Employee.class);
        return emp;
    }



    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role) && Objects.equals(this.salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role, this.salary);
    }

    @Override
    public String toString() {
        return "{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role
                + "', salary = " + this.salary + "}";
    }
}
