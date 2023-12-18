package com.example.pracownicy;

import com.opencsv.bean.CsvIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EmployeeClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private long id;
    private String groupName;
    private int size;


    @CsvIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="employeeClass",fetch = FetchType.EAGER)
    List<Employee> employeeList = new ArrayList<>();
    @CsvIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="employeeClass",fetch = FetchType.EAGER)
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

    public long getId() {
        return id;
    }

    public List<Rate> getReviewList() {
        return reviewList;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
