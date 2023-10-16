public class Rectangle extends Figure implements Printing{
    private double a;
    private double b;
    private boolean correctRectangle = true;

    public Rectangle(double newA, double newB)
    {
        if(newA <= 0 || newB <= 0)
        {
            correctRectangle = false;
            System.out.println("ERROR!! Wartości ujemne lub 0!!!");
        }
        else
        {
            a = newA;
            b = newB;
        }
    }
    public void print()
    {
        if(!correctRectangle)
            return;
        System.out.println("Kwadrat o bokach a: " + a + " b: " + b);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
    public double calculateArea()
    {
        if(!correctRectangle)
            return 0;
        return a * b;

    }
    public double calculatePerimeter()
    {
        if(!correctRectangle)
            return 0;
        return 2*a + 2*b;
    }

    public int numOfEdges()
    {
        return 4;
    }

}
