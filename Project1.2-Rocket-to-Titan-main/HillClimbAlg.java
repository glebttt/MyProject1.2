package helperFunction;

import java.util.Random;

public class HillClimbAlg {

    public int HillClimb(){
        Random rn = new Random();

        int newDistanceFromRocketToTitan;

        int initialLaunchX = rn.nextInt();
        int newLaunchXPlus = initialLaunchX + 1;
        int newLaunchXMinus = initialLaunchX - 1;
        int finalLaunchX;

        int initialLaunchY = rn.nextInt();
        int newLaunchYPlus = initialLaunchY + 1;
        int newLaunchYMinus = initialLaunchY - 1;
        int finalLaunchY;

        int initialLaunchZ = rn.nextInt();
        int newLaunchZPlus = initialLaunchZ + 1;
        int newLaunchZMinus = initialLaunchZ - 1;
        int finalLaunchZ;

        //launch rocket with initialLaunchX, initialLaunchY, initialLaunchZ
        //initialDistanceFromRocketToTitan =
        //int finalDistancePlus = rocket after 1 year
        //int finalDistanceMinus = finalDistancePlus;

        //launch rocket with newLaunchXPlus, initialLaunchY, initialLaunchZ
        //newDistanceFromRocketToTitan = rocket after 1 year
        while(newDistanceFromRocketToTitan > finalDistancePlus){
            finalDistancePlus = newDistanceFromRocketToTitan;
            newLaunchXPlus++;
            //newDistanceFromRocketToTitan = rocket after 1 year
        }

        //launch rocket with newLaunchXMinus
        //newDistanceFromRocketToTitan = rocket after 1 year

        while(newDistanceFromRocketToTitan > finalDistanceMinus){
            finalDistanceMinus = newDistanceFromRocketToTitan;
            newLaunchXMinus--;
            //newDistanceFromRocketToTitan = rocket after 1 year
        }

        if(finalDistancePlus > finalDistanceMinus){
            finalLaunchX =  newLaunchXPlus;
        }else{
            finalLaunchX = newLaunchXMinus;
        }

        //repeat with y and z

    }
}
