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
    public void addSalary(Employee e, double newSalary)
    {
        int i = pracownicy.indexOf(e);
        Employee pracownik = pracownicy.get(i);

        pracownik.setWynagrodzenie(pracownik.getWynagrodzenie()+newSalary);
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

        prac.setStanPracownika(ec);
    }
    public Employee search(String nazwisko)
    {
        for(Employee pracownik : pracownicy) {
            if( pracownik.getNazwisko().compareTo(nazwisko) == 0 )
                return pracownik;
        }
        System.out.println("nie znaleziono "+nazwisko+"\n");
        return null;
    }
    public List<Employee> searchPartial(String fraza)
    {
        List<Employee> output = new ArrayList<Employee>();
        for (Employee pracownik : pracownicy) {
            if (pracownik.getNazwisko().contains(fraza) || pracownik.getImie().contains(fraza))
                output.add(pracownik);
        }

        return output;
    }
    public int countByCondition(EmployeeCondition ec)
    {
        int n = 0;
        for(Employee praconiwk: pracownicy)
            if(praconiwk.getStanPracownika() == ec)
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
        List<Employee> output = new ArrayList<Employee>(pracownicy);
        Collections.sort(output);

        return output;
    }
    public List<Employee> sortBySalary()
    {
        List<Employee> output = new ArrayList<Employee>(pracownicy);
        Collections.sort(output, Employee.salaryComparator.reversed());

        return output;
    }
    public Employee Max()
    {
        return Collections.max(pracownicy, Employee.salaryComparator);
    }
}
