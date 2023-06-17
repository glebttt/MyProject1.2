package code.algorithms.Solvers;

import code.model.objects.PlanetObject;

public class VelocityFunction implements Function{

    double velocity;

    public VelocityFunction(double velocity){
        this.velocity = velocity;
    }
    public double evaluation(double y0, double t){
        return velocity;
    }
}

