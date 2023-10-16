public class Triangle extends Figure implements Printing {
    private double a;
    private double b;
    private double c;
    private boolean correctTriangle = true;

    public Triangle(double newA, double newB, double newC)
    {
        if((newA + newB <= newC || newA + newC <= newB || b + newC <= newA) || (newA <= 0 || newB <= 0 || newC <= 0))
        {
            correctTriangle = false;
            System.out.println("ERROR!! nie można zrobić trójkąta!!!");
        }
        else
        {
            a = newA;
            b = newB;
            c = newC;
        }
    }
    public void print()
    {
        if(!correctTriangle)
            return;
        System.out.println("Trójkąt o bokach a: " + a + " b: " + b + " c: " + c);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
    public double calculateArea()
    {
        if(!correctTriangle)
            return 0;
        double p = 0.5*(a+b+c);

        return Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }
    public double calculatePerimeter()
    {
        if(!correctTriangle)
            return 0;
        return a+b+c;
    }

    private int numOfEdges()
    {
        return 3;
    }
}
