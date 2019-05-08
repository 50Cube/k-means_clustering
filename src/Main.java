import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main
{

    public static void readFromFile(Point [] tab)
    {
        try
        {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data.csv"));

            int i = 0;
            while ((line = bufferedReader.readLine()) != null)
            {
                tab[i] = new Point();
                String [] values = line.split(",");

                tab[i].setX(Double.parseDouble(values[0]));
                tab[i].setY(Double.parseDouble(values[1]));
                i++;
            }

            /*for (int j=0; j<tab.length; j++)
            {
                System.out.println(j+1 + "|  " + tab[j].getX() + "   " + tab[j].getY());
            }*/
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        catch (IOException io)
        {
            System.out.println(io.getMessage());
        }
    }

    public static void randomCentroids(Centroid [] centroids, Point [] points)
    {
        for (int i=0; i<centroids.length; i++)
        {
            centroids[i] = points[(int)(Math.random()*1000)];
            centroids[i].setId(i);
        }
    }

    public static void displayPoints(Point [] tab)
    {
        for (int i=0; i<tab.length; i++)
        {
            System.out.println(tab[i].toString());
        }
        System.out.println("");
    }

    public static void displayPointsWithCentroids(Point [] tab)
    {
        for (int i=0; i<tab.length; i++)
        {
            System.out.println(tab[i].displayPoint());
        }
        System.out.println("");
    }

    public static void chooseCentroid(Centroid [] centroids, Point [] points)
    {
        for (int i=0; i<points.length; i++)
        {
            for (int j=0; j<centroids.length; j++)
            {
                centroids[j].setDistanceFromPoint(sqrt((pow(centroids[j].getX() - points[i].getX(),2) + pow(centroids[j].getY() - points[i].getY(),2))));
            }
            Centroid centroids2 [] = new Centroid [centroids.length];
            for (int k=0; k<centroids.length; k++)
            {
                centroids2[k] = centroids[k];
            }
            Arrays.sort(centroids2);
            points[i].setChosenCentroid(centroids2[0]);
            /*for (int m=0; m<centroids.length; m++)
            {
                System.out.println(centroids[m].getDistanceFromPoint());
            }*/
        }
    }

    public static void recalculateCentroids(Centroid [] centroids, Point [] points)
    {
        double count [] = new double[centroids.length];
        double newX [] = new double[centroids.length];
        double newY [] = new double[centroids.length];

        for (int i=0; i<centroids.length; i++)
        {
            newX[i] = 0.0;
            newY[i] = 0.0;
            count[i] = 0.0;
        }

        for (int i=0; i<points.length; i++)
        {
            for (int j=0; j<centroids.length; j++)
            {
                if (points[i].getChosenCentroid().getId() == centroids[j].getId())
                {
                    count[j]++;
                    newX[j] += points[i].getX();
                    newY[j] += points[i].getY();
                }
            }
        }

        for (int i=0; i<centroids.length; i++)
        {
            centroids[i].setX(newX[i] / count[i]);
            centroids[i].setY(newY[i] / count[i]);
        }
        //System.out.println(centroids[0].getX() + "  " + centroids[0].getY());
    }

    // returns false if any of chosen centroids has changed
    public static boolean checkChange(int [] previousCentroidsID, Point [] points)
    {

        boolean tmp = true;

        for (int i=0; i<points.length; i++)
        {
            if (points[i].getChosenCentroid().getId() != previousCentroidsID[i])
                tmp = false;
        }

        return tmp;
    }

    public static double calculateError(Point [] points, Centroid [] centroids)    // obliczanie bledu kwantyzacji
    {
        double tmp = 0;

        for (int i=0; i<centroids.length; i++)
        {
            for (int j=0; j<points.length; j++)
            {
                if (points[j].getChosenCentroid().getId() == centroids[i].getId())
                    tmp += (sqrt((pow(centroids[i].getX() - points[j].getX(),2) + pow(centroids[i].getY() - points[j].getY(),2))));
            }
        }

        return tmp/points.length;
    }

    public static void main(String[] args)
    {
        Point points[] = new Point[1000];
        readFromFile(points);
        int K6 = 6;
        Point centroids[] = new Point[K6];

        randomCentroids(centroids,points);
        displayPoints(centroids);
        System.out.println("\n");

        int previousCentroidsID [] = new int [points.length];
        int iterations = 0;
        chooseCentroid(centroids,points);


        List<Double> errors = new ArrayList<>();      // blad kwantyzacji

        for (int m=0;m<1000;m++)
        {
            iterations++;

            errors.add(calculateError(points,centroids));

            for (int i=0; i<points.length; i++)
            {
                previousCentroidsID[i] = points[i].getChosenCentroid().getId();
            }
            chooseCentroid(centroids,points);
            recalculateCentroids(centroids,points);

            if (checkChange(previousCentroidsID,points) != false && iterations != 1)
            {
                break;
            }
        }

        System.out.println("Centroids after " + iterations + " iterations\n");
        displayPoints(centroids);

        System.out.println("\nQuantization errors in each iteration:\n");
        for (int i=0; i<errors.size(); i++)
            System.out.println(errors.get(i));
    }
}
