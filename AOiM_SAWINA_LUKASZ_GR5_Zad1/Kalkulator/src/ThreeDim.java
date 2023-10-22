import java.util.Scanner;

public class ThreeDim implements Printing {
    private Figure base;
    private double h;

    public ThreeDim(Figure newBase, double newH)
    {
        base = newBase;
        h = newH;
    }
    public void print()
    {
        Scanner scan = new Scanner(System.in);
        int opcja;
        while(true)
        {
            System.out.println("Co chcesz policzyć?");
            System.out.println("1: Pole całkowite");
            System.out.println("2: Obwód");
            System.out.println("3: Objętość");
            System.out.println("0: Nic, chcę już przestać");

            try
            {
                opcja = scan.nextInt();

                if(opcja != 0 && opcja != 1 && opcja != 2  && opcja != 3)
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
            case 3:
            {
                System.out.println("Objętość: " + calculateVolume());
                break;
            }
        }
    }
    public double calculateVolume()
    {
        return base.calculateArea()*h;
    }
    public double calculateArea()
    {
        return base.calculatePerimeter()*h+2*base.calculateArea();
    }
    public double calculatePerimeter()
    {
        return 2*base.calculatePerimeter()+base.numOfEdges()*h;
    }

}
