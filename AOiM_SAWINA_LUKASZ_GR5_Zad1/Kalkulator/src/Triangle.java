import java.util.Scanner;

public class Triangle extends Figure implements Printing {
    private double a;
    private double b;
    private double c;

    public Triangle(double newA, double newB, double newC) throws IllegalArgumentException
    {
        if((newA + newB <= newC || newA + newC <= newB || b + newC <= newA) || (newA <= 0 || newB <= 0 || newC <= 0))
            throw new IllegalArgumentException("Nie można utworzyć trójkątu o takich bokach");

        a = newA;
        b = newB;
        c = newC;
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
        double p = 0.5*(a+b+c);
        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
    public double calculatePerimeter()
    {
        return a+b+c;
    }

    public int numOfEdges(){
        return 3;
    }

}
