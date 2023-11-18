package com.example.menagerpracownikow;

public interface IDataCommunicator {
    void przekazDaneGrupy(String nazwa, int pojemnosc);
    void przekazDanePracownika(Employee nowyPracownik);
    void zaktualizujPracownika(EmployeeCondition nowyStatus, double noweZarobki);
    boolean czyGrupaIstnieje(String nazwa);
    Employee wybranyPracownik();
}
