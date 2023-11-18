package com.example.menagerpracownikow;

import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    private Map<String, ClassEmployee> kontenery = new HashMap();

    public Map<String, ClassEmployee> getKontenery() {return kontenery; }

    public void addClass(String nazwa, int ilosc) {
        if (!kontenery.containsKey(nazwa)) {
            kontenery.put(nazwa, new ClassEmployee(nazwa, ilosc));
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

    public void addEmployeeToClass(String nazwa, Employee nowyPracownik)
    {
        if(kontenery.containsKey(nazwa))
            kontenery.get(nazwa).addEmployee(nowyPracownik);
        else
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
    }

    public void findEmpty()
    {
        kontenery.forEach((groupName, group) -> {
            if (group.getSize() == 0)
                System.out.println(groupName);
        });
    }

    public boolean czyIstnieje(String nazwa)
    {
        return kontenery.containsKey(nazwa);
    }

    public void summary()
    {
        kontenery.forEach((groupName, group) -> {
            System.out.println(groupName + ":");
            group.summary();
            System.out.println();
        });
    }
}
