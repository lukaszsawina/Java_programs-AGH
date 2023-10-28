import java.util.List;

public class Main {
    public static void main(String[] args) {

        Employee pracowniczek = new Employee("Jan", "Kowalski", EmployeeCondition.obecny, 1994, 12);

        //pracowniczek.Print();

        Employee pracowniczek2 = new Employee("Tomasz", "Nowak", EmployeeCondition.chory, 2001, 30);

        //pracowniczek2.Print();

        Employee pracowniczek3 = new Employee("Pan", "Nowaczek", EmployeeCondition.chory, 2001, 30);

//        pracowniczek3.Print();

        ClassEmployee pracownicy = new ClassEmployee();
//
        pracownicy.addEmployee(pracowniczek);
        pracownicy.addEmployee(pracowniczek2);
        pracownicy.addEmployee(pracowniczek3);
//
//        Employee znaleziony = pracownicy.search("Nowak");

//        znaleziony.Print();

//        pracownicy.searchPartial("Nowa");

//        System.out.println(pracownicy.countByCondition(EmployeeCondition.chory));

//        pracownicy.summary();

//
//        List<Employee> posortowani = pracownicy.sortBySalary();
//
//        for(Employee pracownik : posortowani)
//            pracownik.Print();

//        pracownicy.Max();
    }
}