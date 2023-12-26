package com.example.springapp.DTO;

import com.example.springapp.Rate;

public class NewRateDto {

    private String groupName;
    private RateDto rate;

    public String getGroupName() {
        return groupName;
    }

    public RateDto getRate() {
        return rate;
    }

    public void setGroupName(String name) {groupName = name;}
    public void setRate(RateDto ocena) {rate = ocena;}
}
