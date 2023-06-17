package code;

import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        Model.loadData(new FileDataLoader());
        Probe probe = new Probe();

        // velocities calculated by HillClimbingAlg
        Boost boost = new Boost(0, new double[]{67.73988800000001, -44.03988500000006, -4.258907});
        // velocities and time calculated by HillClimbingAlgReturn
        Boost boost1 = new Boost(5259600.0, new double[]{-128.99164151418873, 46.449291805183115, 3.3594162321263057});

        probe.addBoost(boost);
        probe.addBoost(boost1);
        Model.addProbe(probe);

        Visualizer.main(args);
    }
}
