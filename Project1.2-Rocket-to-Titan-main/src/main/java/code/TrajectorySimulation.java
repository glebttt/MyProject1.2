package code;


import code.algorithms.HillClimbingAlg;
import code.algorithms.HillClimbingAlgReturn;


public class TrajectorySimulation {
    public static void main(String[] args) {
        HillClimbingAlg hcInstance = new HillClimbingAlg();
        double[] bestVelocitiesToTitan = hcInstance.hillClimbing(1,10,67.73988800000001, -44.03988500000006, -4.258907); //These values were also discovered using the HC algorithm,
        System.out.println("Best velocities to Titan: ");                                                                                                               //we added them here as initial velocities
        System.out.println("xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);      //just to shorten the runtime.

        HillClimbingAlgReturn hcReturnInstance = new HillClimbingAlgReturn(hcInstance.getCoordinatesToMain());
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("Switch to Return Trip");

        double[] bestVelocitiesToEarth = hcReturnInstance.runHillClimbingAlg(1, 10, bestVelocitiesToTitan[0], bestVelocitiesToTitan[1] ,bestVelocitiesToTitan[2]);//The velocities used in the simulation were discovered
        System.out.println("Best velocities to Earth: ");                                                                                                                                    //using an earlier version of the HC algorithm, which as of the 30th of may
        System.out.println("xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);                           // started behaving unexpectedly, so we couldn't improve the values more,
                                                                                                                                                                                             // so for the simulation we use the values discovered before we broke the code.
        System.out.println("Results: ");                                                                                                                                                     // We will fix it in phase 3, as we don't have enough time left.
        System.out.println("Best velocities to Titan: " + "xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);
        System.out.println("Best velocities to Earth: " + "xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);
    }
}
