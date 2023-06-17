package code.algorithms;

import code.algorithms.Solvers.*;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.ArrayList;
import java.util.List;

public class ModelRunner {
    /**
     * Runs the model forward with step updates the position of the Objects in the Model using Euler Solver
     * <p>
     * The runner is made to work with multiple probes also it is optimized to work fast with Euler solver
     * Boosts are checked inside as well, because the max F depends on the step size
     * </p>
     *
     * @param numberOfDays how many days is the model going to get run for
     * @param accuracy     or step size for the Solver
     * @param planetss     list of planets that are going to be run
     * @param probes       list of probes that are going to be run
     */
    public static void runnerForMultipleProbes(int numberOfDays, double accuracy, List<PlanetObject> planetss, List<Probe> probes) {
        ArrayList<PlanetObject> allObjects = new ArrayList<>(planetss);
        allObjects.addAll(probes);
        PlanetObject[] planets = allObjects.toArray(new PlanetObject[allObjects.size()]);
        boolean stopper = false;
        // check if the boosts
        for (Probe probe : probes) {
            boolean checker = probe.areBoostsValid(accuracy);
//            System.out.println(checker);
            if (!checker) {
                stopper = true;
                System.out.println("The probe " + probe.getProbeNumber() + " with wrong boost");
            }
        }

        if (!stopper) {
            for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {

                if (i % ((1 / accuracy) * 60 * 60 * 24) == 0) {
                    double day = i / ((1 / accuracy) * 60 * 60 * 24);
//                    System.out.println("Day " + day);
                    for (Probe probe : probes) {
                        probe.BoosterMECH(day);
                    }
                }
                for (int j = 1; j < planets.length; j++) {

                    double[] acc = new double[3];
                    for (int k = 0; k < planets.length - probes.size(); k++) {

                        if (k != j) {
                            acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                        }
                    }
                    Solvers.implicitEuler(planets[j], acc, accuracy);
                }
            }
        }
    }

    /**
     * Runs the model forward with step updates the position of the Objects in the Model using Euler Solver
     * This runner is used for GUI and is called in a loop, so it runs the model not for days but seconds usually
     * small values. It keeps track of how long the model has been run.
     * <p>
     * The runner is made to work with multiple probes also it is optimized to work fast with Euler solver
     * Boosts are checked inside as well, because the max F depends on the step size
     * </p>
     *
     * @param time       keep track how long the model has been run in total
     * @param smoothness how smooth the planets are going to move in GUI
     * @param accuracy   or step size for the Solver
     * @param planetsList   list of planets that are going to be run
     * @param probes     list of probes that are going to be run
     * @return the amount of times runs and will update the amount of time the model was run for
     */
    public static double runnerForGUI(double time, int smoothness, double accuracy, List<PlanetObject> planetsList, List<Probe> probes) {
        AccelerationFunction accelerationFunction;
        VelocityFunction velocityFunction;
        Solvers solvers = new Solvers();
        ArrayList<PlanetObject> allObjects = new ArrayList<>(planetsList);
        allObjects.addAll(probes);
        PlanetObject[] planets = allObjects.toArray(new PlanetObject[allObjects.size()]);
        boolean stopper = false;
        // check if the boosts
        for (Probe probe : probes) {
            boolean checker = probe.areBoostsValid(accuracy);
            if (!checker) {
                stopper = true;
                System.out.println("The probe " + probe.getProbeNumber() + " with wrong boost");
            }
        }

        if (!stopper) {
            for (int i = 0; i < smoothness; i += 1) {

                for (int j = 1; j < planets.length; j++) {
                    if (i % ((1 / accuracy) * 60 * 60 * 24) == 0) {

                        double day = time / ((1 / accuracy) * 60 * 60 * 24);
                        for (Probe probe : probes) {
                            probe.BoosterMECH(day);
                        }
                    }
                    double[] acc = new double[3];
                    for (int k = 0; k < planets.length; k++) {
                        double[] newPosition = new double[3];
                        double[] newVelocity = new double[3];
                        for (int l = 0; l<3; l++){
                        if (k != j) {
                            //acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                            double[] position = planets[j].getCoordinates();
                            double[] otherPosition = planets[k].getCoordinates();
                            double otherPosition1D = planets[k].getCoordinates()[l];
                            accelerationFunction = new AccelerationFunction(planets[j], planets[k], otherPosition1D, l);
                            velocityFunction = new VelocityFunction(planets[j].getVelocity()[l]);
                            Vector y = new Vector();
                            y.addFunction(accelerationFunction);
                            //y.addFunction(velocityFunction);
                            double initVelocity = planets[j].getVelocity()[l];
                            double initPosition = planets[j].getCoordinates()[l];
                            //System.out.println("initPosition: "+initPosition+" initVelocity: "+initVelocity);
                            double[] y0 = {initPosition, initVelocity};
                            solvers.eulerStep(y, y0, 0.5, 0);
                            newPosition[l] = planets[j].getVelocity()[l];
                            //newPosition[l] = solvers.getW(1);
                            newVelocity[l] = solvers.getW(0);
                        }
                        planets[j].setVelocity(newVelocity);
                        planets[j].setCoordinates(newPosition);
                        }
                    }
                    //Solvers.implicitEuler(planets[j], acc, accuracy);
                }
                time++;
            }
        }
        return time;
    }
}
