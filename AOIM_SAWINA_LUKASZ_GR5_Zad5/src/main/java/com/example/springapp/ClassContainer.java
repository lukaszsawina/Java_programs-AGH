package com.example.springapp;

import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    private Map<String, EmployeeClass> kontenery = new HashMap();

    public Map<String, EmployeeClass> getKontenery() {return kontenery; }

    public void addClass(String nazwa, int ilosc) {
        if (!kontenery.containsKey(nazwa)) {
            kontenery.put(nazwa, new EmployeeClass(nazwa, ilosc));
        } else {
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
        }
    }

    public void removeClass(String nazwa) {
        if(kontenery.containsKey(nazwa))
            kontenery.remove(nazwa);
        else
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
    }

    public void addEmployeeToClass(String nazwa, Employee nowyPracownik) throws Exception {
        if(kontenery.containsKey(nazwa))
            kontenery.get(nazwa).addEmployee(nowyPracownik);
        else
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
    }

}
