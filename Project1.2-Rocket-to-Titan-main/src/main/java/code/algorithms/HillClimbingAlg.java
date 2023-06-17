package code.algorithms;

import java.util.Arrays;
import java.util.Random;
import code.algorithms.LaunchRocketHC;
import code.model.Model;


public class HillClimbingAlg {
    private final int INITIAL = 0;
    private final int XPLUS = 1;
    private final int XMINUS = 2;
    private final int YPLUS = 3;
    private final int YMINUS = 4;
    private final int ZPLUS = 5;
    private final int ZMINUS = 6;
    private final int X = 0;
    private final int Y = 1;
    private final int Z = 2;

    private double[] coordinatesToMain;

    public HillClimbingAlg(){
        //his one is called in main
    }

    public double[] getCoordinatesToMain() {
        return coordinatesToMain;
    }

    public double[] hillClimbing(double VelocityChange, double accuracySolver, double initialX, double initialY, double initialZ) {

        System.out.println("Starting HC from Earth");

        Random rn = new Random();

        int closestRocket = 0;

        int genCounter = 1;

        double[][] velocitiesOfRockets = new double[7][3];

        velocitiesOfRockets[INITIAL][X] = initialX;
        velocitiesOfRockets[INITIAL][Y] = initialY;
        velocitiesOfRockets[INITIAL][Z] = initialZ;

        //System.out.println(velocitiesOfRockets[INITIAL][X]);

//        velocitiesOfRockets[INITIAL][X] = rn.nextDouble() * 200 - 100;
//        velocitiesOfRockets[INITIAL][Y] = rn.nextDouble() * 200 - 100;
//        velocitiesOfRockets[INITIAL][Z] = rn.nextDouble() * 200 - 100;

        while (true) {

            for(int i=1; i < velocitiesOfRockets.length; i++) {                 //Loads the velocitiesOfRockets 2D array with respecting velocities.
                velocitiesOfRockets[i][X] = velocitiesOfRockets[INITIAL][X];
                velocitiesOfRockets[i][Y] = velocitiesOfRockets[INITIAL][Y];
                velocitiesOfRockets[i][Z] = velocitiesOfRockets[INITIAL][Z];
            }
            velocitiesOfRockets[XPLUS][X] = velocitiesOfRockets[INITIAL][X] + VelocityChange;
            velocitiesOfRockets[XMINUS][X] = velocitiesOfRockets[INITIAL][X] - VelocityChange;
            velocitiesOfRockets[YPLUS][Y] = velocitiesOfRockets[INITIAL][Y] + VelocityChange;
            velocitiesOfRockets[YMINUS][Y] = velocitiesOfRockets[INITIAL][Y] - VelocityChange;
            velocitiesOfRockets[ZPLUS][Z] = velocitiesOfRockets[INITIAL][Z] + VelocityChange;
            velocitiesOfRockets[ZMINUS][Z] = velocitiesOfRockets[INITIAL][Z] - VelocityChange;


            System.out.println("Current best velocities: " + Arrays.deepToString(velocitiesOfRockets));
            LaunchRocketHC launcherHC = new LaunchRocketHC();
            double[] DistancesToTitan = launcherHC.launchSevenRockets(velocitiesOfRockets, accuracySolver);
            coordinatesToMain = launcherHC.getClosestCoordinates(); //passes coordinates


            closestRocket = findSmallest(DistancesToTitan);
            System.out.println("Current generation number: " + genCounter);
            System.out.println("Closest rocket: " + closestRocket);

            switch (closestRocket) {
                case INITIAL:
                    return new double[] { velocitiesOfRockets[INITIAL][X], velocitiesOfRockets[INITIAL][Y], velocitiesOfRockets[INITIAL][Z],  };
                case XPLUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[XPLUS][X];
                    break;
                case XMINUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[XMINUS][X];
                    break;
                case YPLUS:
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[YPLUS][Y];
                    break;
                case YMINUS:
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[YMINUS][Y];
                    break;
                case ZPLUS:
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[ZPLUS][Z];
                    break;
                case ZMINUS:
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[ZMINUS][Z];
                    break;
            }
            genCounter++;
        }
    }

    public static int findSmallest(double[] distances) {
        int smallestIndex = 0;
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] < distances[smallestIndex])
                smallestIndex = i;
        }
        return smallestIndex;
    }

    public static void main(String[] args) {

    }
}
