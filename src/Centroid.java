public class Centroid implements Comparable<Centroid>
{
    private double x;
    private double y;
    private double distanceFromPoint;
    private int id;

    public Centroid () {}

    public Centroid(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getDistanceFromPoint()
    {
        return distanceFromPoint;
    }

    public void setDistanceFromPoint(double distanceFromPoint)
    {
        this.distanceFromPoint = distanceFromPoint;
    }

    @Override
    public String toString()
    {
        return "X: " + this.getX() + ";  Y: " + this.getY();
    }

    @Override
    public int compareTo(Centroid o)
    {
        int tmp = 0;
        if (this.distanceFromPoint > o.distanceFromPoint)
            tmp = 1;
        if (this.distanceFromPoint < o.distanceFromPoint)
            tmp = -1;
        return tmp;
    }
}
