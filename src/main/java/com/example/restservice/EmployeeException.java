package com.example.restservice;

public class EmployeeException  extends RuntimeException {
        EmployeeException(String error) {
            super("Problem with Employee - full error: " + error);
        }
}
