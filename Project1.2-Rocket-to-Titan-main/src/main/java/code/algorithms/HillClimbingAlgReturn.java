package code.algorithms;

import java.util.Arrays;
import java.util.Random;
import code.algorithms.LaunchRocketFromTitan;


public class HillClimbingAlgReturn {

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
    private double[] bestCoordinates;

    public HillClimbingAlgReturn(double[] bestCoordinates) {
        this.bestCoordinates = bestCoordinates;
    }

    public double[] runHillClimbingAlg(double VelocityChange, double accuracySolver, double initialX, double initialY, double initialZ) {

        System.out.println("Starting HC from Titan");

        Random rn = new Random();

        int closestRocket = 0;

        int genCounter = 1;

        double[][] velocitiesOfRockets = new double[7][3];

        velocitiesOfRockets[INITIAL][X] = initialX;
        velocitiesOfRockets[INITIAL][Y] = initialY;
        velocitiesOfRockets[INITIAL][Z] = initialZ;

        while (true) {

            for (int i = 1; i < velocitiesOfRockets.length; i++) {
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

            LaunchRocketFromTitan launchRocketFromTitan = new LaunchRocketFromTitan(bestCoordinates);
            double[] DistancesToEarth = launchRocketFromTitan.launchSevenRocketsReturn(velocitiesOfRockets, accuracySolver);

            closestRocket = findSmallest(DistancesToEarth);
            System.out.println("Current generation number: " + genCounter);
            System.out.println("Closest rocket: " + closestRocket);

            switch (closestRocket) {
                case INITIAL:
                    return new double[]{velocitiesOfRockets[INITIAL][X], velocitiesOfRockets[INITIAL][Y], velocitiesOfRockets[INITIAL][Z]};
                case XPLUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[XPLUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[XPLUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[XPLUS][Z];
                    break;
                case XMINUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[XMINUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[XMINUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[XMINUS][Z];
                    break;
                case YPLUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[YPLUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[YPLUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[YPLUS][Z];
                    break;
                case YMINUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[YMINUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[YMINUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[YMINUS][Z];
                    break;
                case ZPLUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[ZPLUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[ZPLUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[ZPLUS][Z];
                    break;
                case ZMINUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[ZMINUS][X];
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[ZMINUS][Y];
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[ZMINUS][Z];
                    break;
            }
            genCounter++;
        }
    }

    public static int findSmallest(double[] distances) { //actual findSmallest
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
