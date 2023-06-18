package code.algorithms.Solvers;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;
import static code.model.objects.PlanetObject.G;

public class AccelerationFunction implements Function {

    PlanetObject planetObject;
    PlanetObject otherObject;

    private double acceleration;

    double distance;

    double otherPosition1D;

    public AccelerationFunction(PlanetObject planetObject, PlanetObject otherObject, double otherPosition1D){
        this.planetObject = planetObject;
        this.otherObject = otherObject;
        this.distance = 1/HelperFunctions.getDistanceBetweenWithVectors(planetObject.getCoordinates(), otherObject.getCoordinates());
        this.otherPosition1D = otherPosition1D;
    }

    public double evaluation(double y0, double t){
       double positionalDifference;
        double M2 = otherObject.getMass();

        positionalDifference = y0 - otherPosition1D;

        acceleration = -G * M2 * (Math.pow(distance, 3d)*positionalDifference);

        return acceleration;
    }

    //this one simply updates the acceleration on the ModelRunner by adding it to the old acceleration
    public double accelerationForEuler(int i){
        double newAcceleration = planetObject.getAcceleration()[i] + acceleration;
        return  newAcceleration;
    }


}
