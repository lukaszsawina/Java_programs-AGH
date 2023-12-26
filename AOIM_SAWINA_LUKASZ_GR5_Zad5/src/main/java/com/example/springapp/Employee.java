package com.example.springapp;


public class Employee {
    private int id;
    private String name;
    private String surname;
    private double salary;
    private int yearOfBirth;
    private EmployeeStatus employeeStatus;

    public Employee() {
    }

    public Employee(int id, String name, String surname, double salary, int yearOfBirth, EmployeeStatus employeeStatus) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.yearOfBirth = yearOfBirth;
        this.employeeStatus = employeeStatus;
    }

    public int getId() {return id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

}
