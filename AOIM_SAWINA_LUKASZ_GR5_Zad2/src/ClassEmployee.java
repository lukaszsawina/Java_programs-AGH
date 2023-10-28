import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassEmployee{

    private String nazwa;
    private List<Employee> pracownicy = new ArrayList<Employee>();
    private static final int max_employee = 10;

    public int getSize() {return pracownicy.size();}

    public boolean addEmployee(Employee newEmployee) {
        if (pracownicy.size() == max_employee)
            return false;
        pracownicy.add(newEmployee);

        return true;
    }
    public void addSalacy(Employee e, double newSalary)
    {
        int i = pracownicy.indexOf(e);

        Employee prac = pracownicy.get(i);

        prac.setWynagrodzenie(prac.getWynagrodzenie()+newSalary);
    }
    public boolean removeEmployee(Employee e)
    {
        if(!pracownicy.contains(e))
            return false;
        pracownicy.remove(e);
        return true;
    }
    public void changeCondition(Employee e, EmployeeCondition ec)
    {
        int i = pracownicy.indexOf(e);

        Employee prac = pracownicy.get(i);

        prac.setStan_pracownika(ec);
    }

    public Employee search(String nazwisko)
    {
        for(Employee pracownik : pracownicy) {
            if( pracownik.getNazwisko().compareTo(nazwisko) == 0 ) {
                return pracownik;
            }
        }
        System.out.println("nie znaleziono "+nazwisko+"\n");
        return null;
    }

    public void searchPartial(String nazwisko)
    {
        for (Employee pracownik : pracownicy) {

            if (pracownik.getNazwisko().contains(nazwisko) || pracownik.getImie().contains(nazwisko)) {
                System.out.println("Znaleziona osoba:");
                pracownik.Print();
                System.out.println();
            }
        }
    }

    public int countByCondition(EmployeeCondition ec)
    {
        int n = 0;
        for(Employee praconiwk: pracownicy)
            if(praconiwk.getStan_pracownika() == ec)
                n++;
        return n;
    }

    public void summary()
    {
        for(Employee praconiwk: pracownicy)
            praconiwk.Print();
    }

    public List<Employee> sortByName()
    {
        Collections.sort(pracownicy);

        return pracownicy;
    }

    public List<Employee> sortBySalary()
    {
        Collections.sort(pracownicy, Employee.salaryComparator.reversed());
        return pracownicy;
    }

    public void Max()
    {
        System.out.println(Collections.max(pracownicy, Employee.salaryComparator).getNazwisko());
    }


}
