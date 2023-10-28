import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Utworzenie pracowników
        Employee pracownik1 = new Employee("Jan", "Kowalski", EmployeeCondition.obecny, 1984, 12);
        Employee pracownik2 = new Employee("Tomasz", "Nowak", EmployeeCondition.delegacja, 1997, 10);
        Employee pracownik3 = new Employee("Anna", "Nowaczek", EmployeeCondition.chory, 2000, 8);
        Employee pracownik4 = new Employee("Nowanow", "Kowalik", EmployeeCondition.nieobecny, 1994, 19);

        //Utworzenie Klasy pracowniczej
        ClassEmployee pracownicy = new ClassEmployee();
        pracownicy.addEmployee(pracownik1);
        pracownicy.addEmployee(pracownik2);
        pracownicy.addEmployee(pracownik3);
        pracownicy.addEmployee(pracownik4);

        //Wszyscy pracownicy
        System.out.println("Wszyscy pracownicy");
        pracownicy.summary();
        System.out.println();

        //Szukanie po nazwisku
        Employee pracownikNowak = pracownicy.search("Nowak");
        if(pracownikNowak != null)
        {
            System.out.println("Pracownik o naziwsku nowak");
            pracownikNowak.Print();
            System.out.println();
        }

        //Szukanie nazwiska z frazą
        System.out.println("Pracownicy z 'Nowa' w imieniu lub nazwisku");

        List<Employee> pracownicyNowa = pracownicy.searchPartial("Nowa");
        for(Employee pracownik: pracownicyNowa)
        {
            pracownik.Print();
            System.out.println();
        }

        //Sortowanie po nazwiskach
        System.out.println("Sortowanie po nazwiskach");

        List<Employee> posortowaniPoNazwisku = pracownicy.sortByName();
        for(Employee pracownik: posortowaniPoNazwisku)
        {
            pracownik.Print();
            System.out.println();
        }

        //Sortowanie po zarobkach malejąco
        System.out.println("Sortowanie po zarobkach malejąco");

        List<Employee> posortowaniPoZarobkach = pracownicy.sortBySalary();
        for(Employee pracownik: posortowaniPoZarobkach)
        {
            pracownik.Print();
            System.out.println();
        }

        //Pracownik z max zarobkami
        System.out.println("Pracownik z max zarobkami");

        Employee pracownikZMax = pracownicy.Max();
        pracownikZMax.Print();
        System.out.println();

        //Utworzenie kontenerów klas pracowników
        ClassContainer kontener = new ClassContainer();

        kontener.addClass("Klasa 1", 2);
        kontener.addClass("Klasa 2", 3);
        kontener.addClass("Klasa 3", 4);
        kontener.addClass("Klasa 0", 0);

        //Wyświetlenie kontenerów klas
        System.out.println("Wyświetlenie kontenerów klas");
        kontener.summary();

        //Szukanie pustej klasy
        System.out.println("Szukanie pustej klasy");
        kontener.findEmpty();
        System.out.println();

        //Usunięcie klasy
        System.out.println("Usunięcie klasy");
        kontener.removeClass("Klasa 3");
        kontener.summary();

    }
}