public class Point extends Centroid
{
    private Centroid chosenCentroid = new Centroid(0,0);

    public Centroid getChosenCentroid()
    {
        return chosenCentroid;
    }

    public void setChosenCentroid(Centroid chosenCentroid)
    {
        this.chosenCentroid = chosenCentroid;
    }

    public String displayPoint()
    {
        return super.toString() + "  chosen centroid: " + getChosenCentroid().toString();
    }
}
