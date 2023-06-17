package code.algorithms.Solvers;

import code.algorithms.Solvers.Function;

public class MyFunction implements Function {

    public MyFunction(){
    }

    public double evaluation(double y0, double t){
        return y0 + t;
    }


}
