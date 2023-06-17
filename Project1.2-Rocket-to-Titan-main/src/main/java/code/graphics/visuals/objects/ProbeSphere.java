package code.graphics.visuals.objects;

import code.model.Model;
import code.model.objects.Probe;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import static code.graphics.Visualizer.SCALE;

public class ProbeSphere extends PlanetSphere {
    private final TrailSphere[] TRAILS;
    private int trailIndex;


    public ProbeSphere(Probe probe) {
        super(probe);
        setMinRadius(Model.getPlanetObjects().get("Earth").getRadius() / 3 / SCALE);
        setRadius(getMinRadius());
        setMaxRadius(getMinRadius() * 3 * 1000000 / SCALE / 2);
        setMaterial(new PhongMaterial(Color.PURPLE));
        TRAILS = initializeTrails();
        trailIndex = 0;
    }


    public void moveTrail() {
        TRAILS[trailIndex%TRAILS.length].setTranslateX(getTranslateX());
        TRAILS[trailIndex%TRAILS.length].setTranslateY(getTranslateY());
        TRAILS[trailIndex%TRAILS.length].setTranslateZ(getTranslateZ());
        TRAILS[trailIndex%TRAILS.length].setVisible(true);
        trailIndex++;
    }

    private TrailSphere[] initializeTrails() {
        TrailSphere[] trails = new TrailSphere[1000];
        for (int i = 0; i < trails.length; i++)
            trails[i] = new TrailSphere(getMinRadius(), getMaxRadius());

        return trails;
    }

    public TrailSphere[] getTrails() {
        return TRAILS;
    }
}
