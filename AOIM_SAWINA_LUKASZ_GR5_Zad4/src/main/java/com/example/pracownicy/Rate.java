package com.example.pracownicy;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Rate {
    @Id
    @GeneratedValue
    private long id;
    private int starNumber;
    private Date reviewDate;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private EmployeeClass employeeClass;

    public Rate() {
    }

    public Rate(int starNumber, Date reviewDate, String comment) {
        this.starNumber = starNumber;
        this.reviewDate = reviewDate;
        this.comment = comment;
    }

    public int getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(int starNumber) {
        this.starNumber = starNumber;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public EmployeeClass getEmployeeGroup() {
        return employeeClass;
    }

    public void setEmployeeGroup(EmployeeClass employeeClass) {
        this.employeeClass = employeeClass;
    }

    public long getId() {
        return id;
    }
}
