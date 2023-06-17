package code.graphics.visuals.objects;

import code.model.objects.PlanetObject;
import javafx.scene.shape.Sphere;

import static code.graphics.Visualizer.SCALE;

/**
 *
 */
public class PlanetSphere extends Sphere {
    private final PlanetObject PLANET_OBJECT;
    private double minRadius;
    private double maxRadius;


    public PlanetSphere(PlanetObject planetObject) {
        super();
        PLANET_OBJECT = planetObject;
        setMinRadius(PLANET_OBJECT.getRadius() / SCALE);
        setRadius(minRadius);
        updateCoordinates();
    }


    /**
     * Updates the coordinates of the Sphere based on the associated PlanetObjects.
     */
    public void updateCoordinates() {
        setTranslateX(PLANET_OBJECT.getCoordinates()[0] / SCALE);
        setTranslateY(PLANET_OBJECT.getCoordinates()[1] / SCALE);
        setTranslateZ(PLANET_OBJECT.getCoordinates()[2] / SCALE);
    }


    // GETTERS AND SETTERS

    public void setMinRadius(double minRadius) {
        this.minRadius = minRadius;
    }

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }
}
