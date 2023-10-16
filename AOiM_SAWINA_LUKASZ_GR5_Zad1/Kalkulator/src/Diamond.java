public class Diamond extends Figure implements Printing{
    private double d1;
    private double d2;
    private boolean correctDiamond = true;

    public Diamond(double newd1, double newd2)
    {
        if(newd1 <= 0 || newd2 <= 0)
        {
            correctDiamond = false;
            System.out.println("ERROR!! Wartości ujemne lub 0!!!");
        }
        else
        {
            d1 = newd1;
            d2 = newd2;
        }
    }
    public void print()
    {
        if(!correctDiamond)
            return;
        System.out.println("Romb o przekątnych d1: " + d1+ " d2: " + d2);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
    public double calculateArea()
    {
        if(!correctDiamond)
            return 0;
        return d1*d2/2;

    }
    public double calculatePerimeter()
    {
        if(!correctDiamond)
            return 0;
        return 2*Math.sqrt(Math.pow(d1/2,2)+Math.pow(d2/2,2));
    }

    private int numOfEdges()
    {
        return 4;
    }
}
