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
