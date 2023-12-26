package com.example.springapp.DTO;

public class GroupDto {
    private String nazwa;
    private int pojemnosc;

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nowaNazwa) {
        nazwa = nowaNazwa;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }
    public void setPojemnosc(int nowaPojemnosc) {
        pojemnosc = nowaPojemnosc;
    }
}
