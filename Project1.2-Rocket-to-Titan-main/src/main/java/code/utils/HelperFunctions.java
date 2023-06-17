package code.utils;


import code.model.objects.PlanetObject;

/**
 * HelperFunctions class contains utility methods for operations on vectors.
 */
public class HelperFunctions {
    private HelperFunctions() {
    }


    /**
     * Calculate the distance between two PlanetObjects.
     *
     * @param object1 The first PlanetObject.
     * @param object2 The second PlanetObject.
     * @return The distance between the two PlanetObjects.
     */
    public static double getDistanceBetween(PlanetObject object1, PlanetObject object2) {
        return Math.sqrt(
                Math.pow((object2.getCoordinates()[0] - object1.getCoordinates()[0]), 2)
                        + Math.pow((object2.getCoordinates()[1] - object1.getCoordinates()[1]), 2)
                        + Math.pow((object2.getCoordinates()[2] - object1.getCoordinates()[2]), 2)
        );
    }

    /**
     * Calculate the distance between two positional vectors.
     *
     * @param one The first positional vector.
     * @param two The second positional vector.
     * @return The distance between the two vectors.
     */
    public static double getDistanceBetweenWithVectors(double[] one, double[] two) {
        return Math.sqrt(
                Math.pow((two[0] - one[0]), 2)
                        + Math.pow((two[1] - one[1]), 2)
                        + Math.pow((two[2] - one[2]), 2)
        );
    }

    /**
     * Subtract two vectors.
     *
     * @param vectorOne The first vector.
     * @param vectorTwo The second vector.
     * @return The resulting vector after subtraction.
     */
    public static double[] subtract(double[] vectorOne, double[] vectorTwo) {
        double[] returnable = new double[3];
        returnable[0] = vectorOne[0] - vectorTwo[0];
        returnable[1] = vectorOne[1] - vectorTwo[1];
        returnable[2] = vectorOne[2] - vectorTwo[2];
        return returnable;
    }

    /**
     * Add two vectors.
     *
     * @param vectorOne The first vector.
     * @param vectorTwo The second vector.
     * @return The resulting vector after addition.
     */
    public static double[] addition(double[] vectorOne, double[] vectorTwo) {
        double[] returnable = new double[3];
        returnable[0] = vectorOne[0] + vectorTwo[0];
        returnable[1] = vectorOne[1] + vectorTwo[1];
        returnable[2] = vectorOne[2] + vectorTwo[2];
        return returnable;
    }

    /**
     * Calculate the magnitude of a vector.
     *
     * @param vector The input vector.
     * @return The magnitude of the vector.
     */
    public static double getVectorMagnitude(double[] vector) {
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
    }

    /**
     * Convert a string representation of a vector to a double array.
     *
     * @param input The input string.
     * @return The vector as a double array.
     */
    public static double[] stringToVector(String input) {
        input = input.replaceAll("[XVYZ=]", "");
        String[] str = input.trim().split("\\s+");

        double[] returnable = new double[3];
        int counter = 0;
        for (String s : str) {
            if (!s.isEmpty()) {
                returnable[counter] = Double.parseDouble(s);
                counter++;
            }
        }

        return returnable;
    }

//    public boolean isOrbiting(PlanetObject myObj, PlanetObject planet) { //checks if myObj is orbiting planet
//        double[] velVect = new double[3];
//        velVect = myObj.getVelocity(); //could add getters in the planet object class to replace velX, velY and velZ
//        double velX = velVect[0];
//        double velY = velVect[1];
//        double velZ = velVect[2];
//        double velocityMagnitude = getVectorMagnitude(velVect);//speed at which an object is moving in a specific direction
//        double distance = getDistanceBetween(myObj, planet);   //distance between myObj and planet
//        double gravitationalForce = PlanetObject.G * myObj.getMass() * planet.getMass() / Math.pow(distance, 2);//gravitational force between myObj and planet
//        double centripetalForce = myObj.getMass() * Math.pow(velocityMagnitude, 2) / distance;//calculate the centripetal force required to keep myObj in orbit
//        double dotProduct = velX * (myObj.getX() - planet.getX()) + velY * (myObj.getY() - planet.getY()) + velZ * (myObj.getZ() - planet.getZ());
//        if (dotProduct != 0) {       //check if the velocity vector of myObj is perpendicular to the position vector of myObj relative to planet
//            return false;
//        }
//        //check if the centripetal force required to keep myObj in orbit is equal to the gravitational force between myObj and planet
//        double e = 0.001; // A small tolerance to account for rounding errors
//        return Math.abs(centripetalForce - gravitationalForce) < e;
//    }
//    public boolean isInOrbitDistance(Probe myObj, PlanetObject planet){ //checks if myObj is in orbit distance of planet
//        boolean result;
//        double radii = Model.getPlanetObjects().get("Titan").getRadius(); //supposed to be the sum of the radius of the 2 objects, but the probe is small and idk where to take the radius of titan from, will change later
//        double distance = getDistanceBetween(myObj, planet);
//        return distance <= radii; //if the distance be
//    }
}
