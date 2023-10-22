import java.util.Scanner;

public class UI {
    private Figure figura2D;
    private ThreeDim figura3D;

    public void Menu()
    {
        Scanner scan = new Scanner(System.in);
        int wybor;
        while(true)
        {
            int opcja;
            System.out.println("Kalkulator geometryczny");
            System.out.println("Wybierz kategorię:");
            System.out.println("1: 2D");
            System.out.println("2: 3D");
            System.out.println("0: Wyjście z programu");

            try
            {
                opcja = scan.nextInt();
                if(opcja != 0 && opcja != 1 && opcja != 2)
                {
                    System.out.println("Wprowadzono złą opcję!");
                    continue;
                }

                wybor = opcja;
                break;
            }
            catch (Exception ex)
            {
                System.out.println("Źle wprowadzono wartość liczby!!");
                scan.nextLine();
            }
        }

        switch (wybor)
        {
            case 0:
            {
                System.out.println("Gud baj :)");
                return;
            }
            case 1:
            {
                Menu2D();
                break;
            }
            case 2:
            {
                Menu3D();
                break;
            }
        }
    }

    private void Menu2D()
    {
        Scanner scan = new Scanner(System.in);
        int opcja;
        while(true)
        {
            System.out.println("Kalkulator 2D");
            System.out.println("Wybierz figurę:");
            System.out.println("1: trójkąt");
            System.out.println("2: prostokąt");
            System.out.println("3: romb");
            System.out.println("0: Powrót");

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
            }
        }

        switch (opcja)
        {
            case 0:
            {
                Menu();
                break;
            }
            case 1:
            {
                MenuTrójkat2D();
                break;
            }
            case 2:
            {
                MenuProstokat2D();
                break;
            }
            case 3:
            {
                MenuRomb2D();
                break;
            }
        }
    }

    private void Menu3D()
    {
        Scanner scan = new Scanner(System.in);
        int opcja;
        while(true)
        {
            System.out.println("Kalkulator 3D");
            System.out.println("Wybierz figurę w podstawie:");
            System.out.println("1: trójkąt");
            System.out.println("2: prostokąt");
            System.out.println("3: romb");
            System.out.println("0: Wyjście z programu");

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
            }
        }

        switch (opcja)
        {
            case 0:
            {
                Menu();
                break;
            }
            case 1:
            {
                MenuTrójkat3D();
                break;
            }
            case 2:
            {
                MenuProstokat3D();
                break;
            }
            case 3:
            {
                MenuRomb3D();
                break;
            }
        }
    }
    private void MenuTrójkat2D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB, wartoscC;

        System.out.println("Podaj wartości boków trójkąta:");

        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();
            wartoscC = scan.nextDouble();

            figura2D = new Triangle(wartoscA, wartoscB, wartoscC);
            figura2D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuTrójkat2D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuTrójkat2D();
        }
    }
    private void MenuTrójkat3D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB, wartoscC;

        System.out.println("Podaj wartości boków trójkąta:");
        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();
            wartoscC = scan.nextDouble();

            figura2D = new Triangle(wartoscA, wartoscB, wartoscC);

            System.out.println("Podaj wartości wysokości:");
            double wysokosc = scan.nextDouble();

            figura3D = new ThreeDim(figura2D, wysokosc);

            figura3D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuTrójkat3D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuTrójkat3D();
        }
    }
    private void MenuProstokat2D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB;

        System.out.println("Podaj wartości boków prostokąta:");
        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();

            figura2D = new Rectangle(wartoscA, wartoscB);

            figura2D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuProstokat2D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuProstokat2D();
        }
    }
    private void MenuProstokat3D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB;

        System.out.println("Podaj wartości boków prostokąta:");

        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();

            figura2D = new Rectangle(wartoscA, wartoscB);

            System.out.println("Podaj wartości wysokości:");
            double wysokosc = scan.nextDouble();

            figura3D = new ThreeDim(figura2D, wysokosc);

            figura3D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuProstokat3D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuProstokat3D();
        }
    }
    private void MenuRomb2D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB;

        System.out.println("Podaj wartości przekątnych rombu:");

        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();

            figura2D = new Diamond(wartoscA, wartoscB);

            figura2D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuRomb2D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuRomb2D();
        }
    }
    private void MenuRomb3D()
    {
        Scanner scan = new Scanner(System.in);
        double wartoscA, wartoscB;

        System.out.println("Podaj wartości przekątnych rombu:");

        try
        {
            wartoscA = scan.nextDouble();
            wartoscB = scan.nextDouble();

            figura2D = new Diamond(wartoscA, wartoscB);

            System.out.println("Podaj wartości wysokości:");
            double wysokosc = scan.nextDouble();

            figura3D = new ThreeDim(figura2D, wysokosc);

            figura3D.print();
            Menu();
        }
        catch (IllegalArgumentException ex)
        {
            System.out.println(ex.getMessage());
            MenuRomb3D();
        }
        catch (Exception ex)
        {
            System.out.println("Źle wprowadzono wartość liczby!!");
            scan.nextLine();
            MenuRomb3D();
        }
    }
}
