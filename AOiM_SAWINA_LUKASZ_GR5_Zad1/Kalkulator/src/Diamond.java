import java.util.Scanner;

public class Diamond extends Figure implements Printing{
    private double d1;
    private double d2;

    public Diamond(double newd1, double newd2) throws IllegalArgumentException
    {
        if(newd1 <= 0 || newd2 <= 0)
            throw new IllegalArgumentException("Nie można utworzyć rombu o takich przekątnych");

        d1 = newd1;
        d2 = newd2;
    }
    public void print()
    {
        Scanner scan = new Scanner(System.in);
        int opcja;
        while(true)
        {
            System.out.println("Co chcesz policzyć?");
            System.out.println("1: Pole");
            System.out.println("2: Obwód");
            System.out.println("0: Nic, chcę już przestać");

            try
            {
                opcja = scan.nextInt();

                if(opcja != 0 && opcja != 1 && opcja != 2)
                {
                    System.out.println("Wprowadzono złą opcję!");
                    continue;
                }

                break;
            }
            catch (Exception ex)
            {
                System.out.println("Źle wprowadzono wartość liczby!!");
                scan.nextLine();
                print();
            }
        }

        switch (opcja)
        {
            case 0:
            {
                return;
            }
            case 1:
            {
                System.out.println("Pole: " + calculateArea());
                break;
            }
            case 2:
            {
                System.out.println("Obwód: " + calculatePerimeter());
                break;
            }
        }
    }
    public double calculateArea()
    {
        return d1*d2/2;

    }
    public double calculatePerimeter()
    {
        return 2*Math.sqrt(Math.pow(d1/2,2)+Math.pow(d2/2,2));
    }
    public int numOfEdges(){
        return 4;
    }
}
