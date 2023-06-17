package code.model.objects;

import code.model.Model;
import code.utils.HelperFunctions;

import java.util.*;

/**
 * Class representing a spacecraft in the model. Extends the PlanetObject class.
 */
public class Probe extends PlanetObject {
    private double shortestDistanceToTitan;
    private final int PROBE_NUMBER;
    private static int probeCounter = 0;
    private final Queue<Boost> listOfBoosts;
    private double fuelUsed;
    private double[] coordinatesOfShortestDistanceToTitan;


    public Probe() {
        super(new double[3], new double[]{0, 0, 0});
        PROBE_NUMBER = probeCounter;
        probeCounter++;
        listOfBoosts = new PriorityQueue<>();
        setCoordinates(initialPosition());
        setMass(50_000);
        shortestDistanceToTitan = getDistanceToTitan();
        coordinatesOfShortestDistanceToTitan = new double[3];
    }


    /**
     * Determines where the Probe should be launched from.
     *
     * @return the double array representing initial coordinates.
     */
    private double[] initialPosition() {
        double[] coordinates = HelperFunctions.subtract(Model.getPlanetObjects().get("Earth").getCoordinates(),
                Model.getPlanetObjects().get("Titan").getCoordinates());

        double magnitude = HelperFunctions.getVectorMagnitude(coordinates);
        for (int i = 0; i <= 2; i++)
            coordinates[i] = coordinates[i] * 6370 / magnitude;

        return HelperFunctions.addition(coordinates, Model.getPlanetObjects().get("Earth").getCoordinates());
    }


    public boolean areBoostsValid(double step) {
        double maxImpulse = 3 * (Math.pow(10, 7)) * step;
        ArrayList<Boost> list = new ArrayList<>(listOfBoosts);
        for (Boost boost : list) {
            if (boost.getFuel() > maxImpulse) {
                System.out.println("Max is" + maxImpulse);
                System.out.println("Boots is" + boost.getFuel());
                return false;
            }
        }
        return true;

    }

    public void addBoost(Boost boost) {
        listOfBoosts.add(boost);
        fuelUsed += boost.getFuel();
    }

    public void BoosterMECH(double time) {
        if (listOfBoosts.peek() != null) {
            if (time == listOfBoosts.peek().getTimeOfBoost()) {
                System.out.println("boosted");
                double[] probeVelocity = getVelocity();
                double[] boostVelocity = listOfBoosts.poll().getVelocityOfBoost();
                setVelocity(HelperFunctions.addition(probeVelocity, boostVelocity));
            }
        }
    }

    @Override
    public String toString() {
        return "Probe{" +
                "shortestDistanceToTitan=" + shortestDistanceToTitan +
                ", listOfBoosts=" + listOfBoosts +
                ", fuelUsed=" + fuelUsed +
                '}';
    }

    // GETTERS AND SETTERS

    public double getDistanceToTitan() {
        return HelperFunctions.getDistanceBetween(this, Model.getPlanetObjects().get("Titan")) - Model.getPlanetObjects().get("Titan").getRadius();
    }

    public double getDistanceToEarth() {
        return HelperFunctions.getDistanceBetween(this, Model.getPlanetObjects().get("Earth")) - Model.getPlanetObjects().get("Earth").getRadius();
    }

    public double getShortestDistanceToTitan() {
        return shortestDistanceToTitan;
    }

    public int getProbeNumber() {
        return PROBE_NUMBER;
    }

    public double getFuelUsed() {
        return fuelUsed;
    }

    /**
     * Overrides the PlanetObject class's method. Apart from assigning the new coordinates
     * also checks the Probe's distance to Titan. If this distance is the lowest noted distance,
     * assigns it to the shortestDistanceToTitan field.
     *
     * @param coordinates an array of doubles representing the new coordinates for the modeled object.
     */
    @Override
    public void setCoordinates(double[] coordinates) {
        super.setCoordinates(coordinates);
        double distanceToTitan = getDistanceToTitan();
        if (distanceToTitan < shortestDistanceToTitan) {
            shortestDistanceToTitan = distanceToTitan;
            coordinatesOfShortestDistanceToTitan = coordinates;
        }
    }

    public double[] getCoordinatesOfShortestDistanceToTitan() {
        return coordinatesOfShortestDistanceToTitan;
    }
}
