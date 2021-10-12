package homework.midtermPrepration;

class Circle extends Shape implements Shape2
{
    private final double radius;

    public double getRadius()
    {
        return this.radius;
    }

    public Circle(double radius)
    {
        this.radius = radius;
    }

    @Override
    public double getArea()
    {
        return Math.PI * radius * radius;
    }

    @Override
    public String getShapeName()
    {
        return "Circle";
    }
}
