package code.algorithms;

import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;

import java.util.ArrayList;
import java.util.Arrays;

import static code.algorithms.ModelRunner.runnerForMultipleProbes;
import static code.model.Model.addProbe;

public class LaunchRocketHC {

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
    private static double[] closestCoordinates;
    public LaunchRocketHC() {
        //this one is called in HillClimbingAlg
    }

    public double[] launchSevenRockets(double[][] velocitiesOfRockets, double accuracySolvers) {

        System.out.println("Running...");

        Model.getInstance().loadData(new FileDataLoader());

        Probe initialRocket = new Probe();
        Boost boost1 = new Boost(0, velocitiesOfRockets[INITIAL]);
        initialRocket.addBoost(boost1);

        Probe xPlusRocket = new Probe();
        Boost boost2 = new Boost(0, velocitiesOfRockets[XPLUS]);
        xPlusRocket.addBoost(boost2);

        Probe xMinusRocket = new Probe();
        Boost boost3 = new Boost(0, velocitiesOfRockets[XMINUS]);
        xMinusRocket.addBoost(boost3);

        Probe yPlusRocket = new Probe();
        Boost boost4= new Boost(0, velocitiesOfRockets[YPLUS]);
        yPlusRocket.addBoost(boost4);

        Probe yMinusRocket = new Probe();
        Boost boost5= new Boost(0, velocitiesOfRockets[YMINUS]);
        yMinusRocket.addBoost(boost5);

        Probe zPlusRocket = new Probe();
        Boost boost6= new Boost(0, velocitiesOfRockets[ZPLUS]);
        zPlusRocket.addBoost(boost6);

        Probe zMinusRocket = new Probe();
        Boost boost7= new Boost(0, velocitiesOfRockets[ZMINUS]);
        zMinusRocket.addBoost(boost7);

        Model.addProbe(initialRocket);
        Model.addProbe(xPlusRocket);
        Model.addProbe(xMinusRocket);
        Model.addProbe(yPlusRocket);
        Model.addProbe(yMinusRocket);
        Model.addProbe(zPlusRocket);
        Model.addProbe(zMinusRocket);

        runnerForMultipleProbes(365, accuracySolvers, Model.getPlanetObjectsArrayList(), Model.getProbes());
        if(initialRocket.getDistanceToTitan() > initialRocket.getShortestDistanceToTitan()){
            closestCoordinates = initialRocket.getCoordinatesOfShortestDistanceToTitan();
        }
        System.out.println();
        System.out.println("Distances to Titan: "+ initialRocket.getShortestDistanceToTitan()+"   " +
                xPlusRocket.getShortestDistanceToTitan()+ "   " +
                xMinusRocket.getShortestDistanceToTitan()+"   " +
                yPlusRocket.getShortestDistanceToTitan()+"   " +
                yMinusRocket.getShortestDistanceToTitan()+"   " +
                zPlusRocket.getShortestDistanceToTitan()+"   " +
                zMinusRocket.getShortestDistanceToTitan());

        System.out.println("-------------------------------------------------------------------------------------------------------------");

        return new double[]{initialRocket.getShortestDistanceToTitan(),
                xPlusRocket.getShortestDistanceToTitan(),
                xMinusRocket.getShortestDistanceToTitan(),
                yPlusRocket.getShortestDistanceToTitan(),
                yMinusRocket.getShortestDistanceToTitan(),
                zPlusRocket.getShortestDistanceToTitan(),
                zMinusRocket.getShortestDistanceToTitan()};
    }

    public  double[] getClosestCoordinates() {
        return closestCoordinates;
    }
}
