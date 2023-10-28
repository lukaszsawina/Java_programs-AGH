import java.util.Comparator;

public class Employee implements Comparable<Employee>, Comparator<Employee>{
    private String imie;
    private String nazwisko;
    private EmployeeCondition stanPracownika;
    private int rok_urodzenia;
    private double wynagrodzenie;

    public String getImie() {return imie;}
    public void setImie(String newImie) {imie = newImie;}
    public String getNazwisko() {return nazwisko;}
    public void setNazwisko(String newNazwisko) {nazwisko = newNazwisko;}
    public EmployeeCondition getStanPracownika() { return stanPracownika;}
    public void setStanPracownika(EmployeeCondition ec) {stanPracownika = ec;}
    public double getWynagrodzenie() {return wynagrodzenie;}
    public void setWynagrodzenie(double v) {wynagrodzenie = v;}


    public Employee(String newImie,
                    String newNazwisko,
                    EmployeeCondition newStan,
                    int newRok,
                    double newWynagrodzenie)
    {
        imie = newImie;
        nazwisko = newNazwisko;
        stanPracownika = newStan;
        rok_urodzenia = newRok;
        wynagrodzenie = newWynagrodzenie;
    }

    public void Print()
    {
        System.out.println("Imie: " + imie);
        System.out.println("Nazwisko: " + nazwisko);
        System.out.println("Stan pracownika: " + stanPracownika);
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
    public int compare(Employee e1, Employee e2)
    {
        return e1.getNazwisko().compareTo(e2.getNazwisko());
    }
    public static Comparator<Employee> salaryComparator = Comparator.comparingDouble(Employee::getWynagrodzenie);
}
