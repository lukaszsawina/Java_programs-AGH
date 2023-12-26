package com.example.springapp;


import java.util.ArrayList;
import java.util.List;

public class EmployeeClass {
    private String groupName;
    private int size;

    List<Employee> employeeList = new ArrayList<>();
    List<Rate> reviewList = new ArrayList<>();

    private int numOfPerson = employeeList.size();

    public EmployeeClass() {
    }

    public EmployeeClass(String groupName, int size) {
        this.groupName = groupName;
        this.size = size;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Rate> getReviewList() {
        return reviewList;
    }

    public void addEmployee(Employee pracownik) throws Exception {
        if(size <= employeeList.size())
            throw new Exception("Za dużo osób w grupie");
        employeeList.add(pracownik);
    }
}
