package code.algorithms.Solvers;

import code.model.Model;
import code.model.objects.PlanetObject;

import java.util.ArrayList;
import java.util.List;

public class SolverTester {

    public static void main(String args[]){
        Solvers solvers = new Solvers();
        PlanetObject earth = new PlanetObject(new double[] {-148186906.893642, -27823158.5715694, 33746.8987977113}, new double[] {5.05251577575409, -29.3926687625899, 0.00170974277401292});
        PlanetObject sun = new PlanetObject(new double []{0,0,0}, new double[] {0,0,0});
        double[] newPosition = new double[3];
        double[] newVelocity = new double[3];
        double[] newAcceleration = new double[3];
        double h = 0.5; //step size
        double t = 1; //
        earth.setAcceleration(new double[]{0,0,0});
        for (int i = 0; i<6; i++) {
            System.out.println("Iteration: "+i);
            for (int j = 0; j < 3; j++) {
                AccelerationFunction ac = new AccelerationFunction(earth, sun, sun.getCoordinates()[j], j);
                Vector y = new Vector();
                y.addFunction(ac);

                double initVelocity = earth.getVelocity()[j];
                double initPosition = earth.getCoordinates()[j];
                double[] y0 = {initPosition, initVelocity};
                System.out.println("InitPosition: "+initPosition+" initVelocity: "+initVelocity);
                //for (int i = 0; i < 6; i++) {
                solvers.eulerStep(y, y0, h, t);
                newPosition[j] = earth.getVelocity()[j];
                newAcceleration[j] = ac.accelerationForEuler();
                newVelocity[j] = earth.getAcceleration()[j] + solvers.getW(0);
                System.out.println("Function 1: "+newPosition[j]);
                //}
            }
            t += h;
            earth.setCoordinates(newPosition);
            earth.setVelocity(newVelocity);
            earth.setAcceleration(newAcceleration);

       }

    }

    public static void yourMom(String args[]){
        Solvers solvers = new Solvers();
        double h = 0.5;
        double t = 1;
        Vector y = new Vector();
        MyFunction myFunction = new MyFunction();
        y.addFunction(myFunction);
        double[] y0 = {1};
        for (int i = 0; i < 6; i++){
            solvers.rungeKuttaStep(y, y0, h, t);
            t += h;
        }
    }
}
