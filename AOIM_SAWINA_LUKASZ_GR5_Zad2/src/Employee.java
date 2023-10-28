import java.util.Comparator;

public class Employee implements Comparable<Employee>, Comparator<Employee>{
    private String imie;
    private String nazwisko;
    private EmployeeCondition stan_pracownika;
    private int rok_urodzenia;
    private double wynagrodzenie;

    public void setWynagrodzenie(double v)
    {
        wynagrodzenie = v;
    }
    public double getWynagrodzenie()
    {
        return wynagrodzenie;
    }

    public String getNazwisko() {return nazwisko;}
    public String getImie() {return imie;}
    public EmployeeCondition getStan_pracownika()
    {
        return stan_pracownika;
    }
    private double getSalary() {
        return wynagrodzenie;
    }
    public void setStan_pracownika(EmployeeCondition ec)
    {
        stan_pracownika = ec;
    }

    public static Comparator<Employee> salaryComparator = Comparator.comparingDouble(Employee::getSalary);

    public Employee(String newImie,
                    String newNazwisko,
                    EmployeeCondition newStan,
                    int newRok,
                    double newWynagrodzenie)
    {
        imie = newImie;
        nazwisko = newNazwisko;
        stan_pracownika = newStan;
        rok_urodzenia = newRok;
        wynagrodzenie = newWynagrodzenie;
    }

    public void Print()
    {
        System.out.println("Imie: " + imie);
        System.out.println("Nazwisko: " + nazwisko);
        System.out.println("Stan pracownika: " + stan_pracownika);
        System.out.println("Rok urodzenia: " + rok_urodzenia);
        System.out.println("Wynagrodzenie: " + wynagrodzenie);
    }

    public int compareTo(Employee nazwisko)
    {
        return this.nazwisko.compareTo(nazwisko.nazwisko);
    }
    public int compare(Employee nazwisko)
    {
        return this.nazwisko.compareTo(nazwisko.nazwisko);
    }

    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getNazwisko().compareTo(e2.getNazwisko());
    }

}
