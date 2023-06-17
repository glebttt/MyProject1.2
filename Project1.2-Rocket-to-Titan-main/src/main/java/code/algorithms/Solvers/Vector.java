package code.algorithms.Solvers;

import code.algorithms.Solvers.Function;

import java.util.ArrayList;

public class Vector {

    private int length;
    private ArrayList<Function> y = new ArrayList<>();

    public Vector(){

    }

    public int getLength(){
        return y.size();
    }

    public void addFunction(Function function){
        y.add(function);
    }

    public double getFunction(int i, double y0, double t){
         return y.get(i).evaluation(y0, t);
    }

}
