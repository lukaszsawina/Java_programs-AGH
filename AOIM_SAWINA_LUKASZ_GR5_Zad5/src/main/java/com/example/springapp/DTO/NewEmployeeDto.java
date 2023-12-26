package com.example.springapp.DTO;

public class NewEmployeeDto {
    private String groupName;
    private EmployeeDto employee;

    public String getGroupName() {
        return groupName;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setGroupName(String name) {groupName = name;}
    public void setEmployee(EmployeeDto pracownik) {employee = pracownik;}

}
