package com.example.pracownicy;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private long id;

    private String name;
    private String surname;
    private double salary;
    private int yearOfBirth;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private EmployeeClass employeeClass;

    public Employee() {
    }

    public Employee(String name, String surname, double salary, int yearOfBirth, EmployeeStatus employeeStatus) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.yearOfBirth = yearOfBirth;
        this.employeeStatus = employeeStatus;
    }

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

    public void setEmployeeGroup(EmployeeClass employeeClass) {
        this.employeeClass = employeeClass;
    }

    public EmployeeClass getEmployeeGroup() {
        return employeeClass;
    }

}
