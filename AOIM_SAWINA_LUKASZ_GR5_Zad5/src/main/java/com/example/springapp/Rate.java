package com.example.springapp;


import java.util.Date;

public class Rate {

    private long id;
    private int starNumber;
    private Date reviewDate;
    private String comment;


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

    public long getId() {
        return id;
    }
}
