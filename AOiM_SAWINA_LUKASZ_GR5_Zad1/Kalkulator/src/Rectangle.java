import java.util.Scanner;

public class Rectangle extends Figure implements Printing{
    private double a;
    private double b;

    public Rectangle(double newA, double newB) throws IllegalArgumentException
    {
        if(newA <= 0 || newB <= 0)
            throw new IllegalArgumentException("Nie można utworzyć kwadratu o takich bokach");

        a = newA;
        b = newB;

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

                if(opcja != 0 && opcja != 1 && opcja != 2 )
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
        return a * b;
    }
    public double calculatePerimeter()
    {
        return 2*a + 2*b;
    }
    public int numOfEdges(){
        return 4;
    }
}
