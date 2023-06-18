package code.algorithms.Solvers;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

import static code.model.objects.PlanetObject.G;

/**
 * Class containing methods with various solver implementations with methods
 */
public class Solvers {

    private double[] w;
    public Solvers() {
    }

    //Since the eulerStep() method doesn't return anything, I made an  instance field w which is the result of the euler calculation and
    //then I can use it to call the new values of position and velocity calculated by euler from the ModelRunner
    public double getW(int i){
        return w[i];
    }

    /**
     * Approximates the acceleration between two PlanetObject objects using the formula for acceleration between two celestial bodies
     *
     * @param position1D   individual vector component of position
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param otherObject  the PlanetObject to which the current planet is being compared to
     * @param i            int representing which component of the coordinates is being evaluated
     * @return acceleration
     */

    private static double accelerationFunction(double position1D, PlanetObject planetObject, PlanetObject otherObject, int i) {
        double force, acceleration, positionalDifference;
        double M1 = planetObject.getMass();
        double M2 = otherObject.getMass();

        double[] coordinates = planetObject.getCoordinates();
        double[] otherCoordinates = otherObject.getCoordinates();

        positionalDifference = position1D - otherCoordinates[i];

        double distance = 1 / HelperFunctions.getDistanceBetweenWithVectors(coordinates, otherCoordinates);

        force = -G * M1 * M2 * (Math.pow(distance, 3)) * positionalDifference;
        acceleration = force / M1;

        return acceleration;
    }
    /**
     * The first order explicit Euler solver that approximates the new position and new velocity vectors
     * with the help of the accelerationFunction() method
     *
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param otherObject  the PlanetObject with respect to which the changes are being calculated
     * @param timeStep     double representing the time step
     */
    public static void explicitEuler(PlanetObject planetObject, PlanetObject otherObject, double timeStep) {
        //Initialize values
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();

        for (int i = 0; i < 3; i++) {
            //Approximate the new values of acceleration, position and velocity
            acceleration[i] += accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            positionalVector[i] += velocityVector[i] * timeStep;
            velocityVector[i] += acceleration[i] * timeStep;
        }

        //Update the new values
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(acceleration);
    }


    /**
     * Euler's solver that updates the planet position with step in time
     *
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param acceleration all the accelerations that affect the body combined to one vector
     * @param timeStep     double representing the time step (1 second is 1)
     */
    public static void implicitEuler(PlanetObject planetObject, double[] acceleration, double timeStep) {
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        for (int i = 0; i < 3; i++) {
            velocityVector[i] += acceleration[i] * timeStep;
            positionalVector[i] += velocityVector[i] * timeStep;
        }
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
    }

    //This is the Euler solver. I pass in two Functions into it using Vector y. y basically calls the two functions that i've added to it
    //y0 are the initial values for each equation, the one in the 0th index corresponds to the first equation, the in the 1st index to the second equation
    //h is just the step size and t is the time but we don't have to worry about it in this situation
    public void eulerStep(Vector y, double[] y0, double h, double t){
        int vectorLength = y.getLength(); //this just gets the number of functions to loop through them in the for loo[
        w = y0; //this gets the initial values
        for (int i = 0; i < vectorLength; i++){
            w[i] += h*(y.getFunction(i, y0[i], t)); //this actually the Euler step
            //System.out.println("Function "+i+": "+w[i]);
        }
    }


    public void rungeKuttaStep(Vector y, double[] y0, double h, double t){
        int vectorLength = y.getLength();
        w = y0;
        double[] k1 = new double[vectorLength];
        double[] k2 = new double[vectorLength];
        double[] k3 = new double[vectorLength];
        double[] k4 = new double[vectorLength];

        for (int i = 0; i < vectorLength; i++){
           k1[i] = h*(y.getFunction(i, y0[i], t));
            System.out.println(i+": "+k1[i]);
           k2[i] = h*(y.getFunction(i, (y0[i] + (1d/2d)*k1[i]), (t + 1d/2d*h)));
            System.out.println(i+": "+k2[i]);
           k3[i] = h*(y.getFunction(i, (y0[i] + (1d/2d)*k2[i]), (t + 1d/2d*h)));
            System.out.println(i+": "+k3[i]);
           k4[i] = h*(y.getFunction(i, y0[i] + k3[i], t + h));
            System.out.println(i+": "+k4[i]);

            w[i] += (1d/6d)*(k1[i] + 2d*k2[i] + 2d*k3[i] + k4[i]);
            System.out.println("Function "+i+": "+w[i]);
        }


    }










    /**
     * The fourth order Runge-Kutta solver that approximates the new position and new velocity vectors
     * by calculating the acceleration via the accelerationFunction() method
     *
     * <p>It uses four intermediate values of k
     * kvi being intermediate values for the velocity vector and kxi being the intermediate values for the positional
     * vector
     * </p>
     *
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param otherObject  the PlanetObject with respect to which the changes are being calculated
     * @param timeStep            double representing time step
     */
    public static void rungeKutta4(PlanetObject planetObject, PlanetObject otherObject, double timeStep) {
        // Initialize values
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();

        double[] kx1 = new double[3];
        double[] kx2 = new double[3];
        double[] kx3 = new double[3];
        double[] kx4 = new double[3];
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];
        double[] kv4 = new double[3];

        for (int i = 0; i < 3; i++) {
            //Calculate intermediate values k as vectors
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kx1[i] = velocityVector[i] * timeStep;
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kx1[i] * (timeStep / 2)), planetObject, otherObject, i);
            kx2[i] = velocityVector[i] * (kv1[i] * (timeStep / 2));
            kv3[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kx2[i] * (timeStep / 2)), planetObject, otherObject, i);
            kx3[i] = velocityVector[i] * (kv2[i] * (timeStep / 2));
            kv4[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kx3[i] * (timeStep / 2)), planetObject, otherObject, i);
            kx4[i] = velocityVector[i] * (kv3[i] * timeStep);

            //Use intermediate values k to estimate new position and velocity
            velocityVector[i] = velocityVector[i] + (timeStep / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
            positionalVector[i] = positionalVector[i] + (timeStep / 6) * (kx1[i] + 2 * kx2[i] + 2 * kx3[i] + kx4[i]);
        }
        //Approximate the new value of acceleration
        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        //Update the new values
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);
    }


    /**
     * This is the second order Ralston's solver that approximates the new position and new velocity vectors
     * by calculating the acceleration via the accelerationFunction() method.
     *
     * <p>
     * It uses four intermediate values of k kvi being intermediate values for the velocity vector and
     * kxi being the intermediate values for the positional vector
     * </p>
     *
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param otherObject  the PlanetObject with respect to which the changes are being calculated
     * @param timeStep     double representing the time step
     */
    public static void ralston2(PlanetObject planetObject, PlanetObject otherObject, double timeStep) {
        //Initialize the values
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();
        double kv1[] = new double[3];
        double kv2[] = new double[3];
        double kx1[] = new double[3];
        double kx2[] = new double[3];

        for (int i = 0; i < 3; i++) {
            //Calculate the intermediate values of k as vectors
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kx1[i] = velocityVector[i] * timeStep;
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kx1[i] * (2 * timeStep / 3)), planetObject, otherObject, i);
            kx2[i] = velocityVector[i] * (kv1[i] * (2 * timeStep / 3));

            //Approximate the new values of position and velocity
            velocityVector[i] = velocityVector[i] + (timeStep / 4) * (kv1[i] + 3 * kv2[i]);
            positionalVector[i] = positionalVector[i] + (timeStep / 4) * (kx1[i] + 3 * kx2[i]);
        }
        //Approximate the new value of acceleration
        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        //Update the new values
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);

    }


    /**
     * The third order Heun's solver that approximates the new position and new velocity vectors
     * by calculating the acceleration via the accelerationFunction() method.
     *
     * <p>
     * Uses four intermediate values of k kvi being intermediate values for the velocity vector and
     * kxi being the intermediate values for the positional vector
     * </p>
     *
     * @param planetObject the PlanetObject for which the acceleration is being calculated
     * @param otherObject  the PlanetObject with respect to which the changes are being calculated
     * @param timeStep     double representing the time step
     */
    public static void heun3(PlanetObject planetObject, PlanetObject otherObject, double timeStep) {
        //Initialize values
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();
        double[] kx1 = new double[3];
        double[] kx2 = new double[3];
        double[] kx3 = new double[3];
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];

        for (int i = 0; i < 3; i++) {
            //Calculate the intermediate values of k as vectors
            kx1[i] = timeStep * velocityVector[i];
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kx2[i] = timeStep * (velocityVector[i] * (1 / 3) * kv1[i]);
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kx1[i] / 3), planetObject, otherObject, i);
            kx3[i] = timeStep * (velocityVector[i] * (2 / 3) * kv2[i]);
            kv3[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (2 / 3) * kx2[i], planetObject, otherObject, i);

            //Approximate the new values of position and velocity
            velocityVector[i] = velocityVector[i] + (timeStep / 4) * (kv1[i] + 3 * kv3[i]);
            positionalVector[i] = positionalVector[i] + (timeStep / 4) * (kx1[i] + 3 * kx3[i]);
        }
        //Approximate the new acceleration
        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        //Update the new values
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);

    }

}
