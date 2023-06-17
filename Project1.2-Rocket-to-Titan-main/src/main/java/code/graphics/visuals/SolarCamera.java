package code.graphics.visuals;

import javafx.scene.PerspectiveCamera;

/**
 * Class representing the PerspectiveCamera for the SolarSubScene.
 */
public class SolarCamera extends PerspectiveCamera {
    /**
     * Double representing how close the camera can go while zooming.
     */
    private double minZoom;
    /**
     * Double representing how far the camera can go while zooming.
     */
    private final double MAX_ZOOM;


    public SolarCamera(){
        super();
        setNearClip(0.1);
        setFarClip(1_000_000_000);

        MAX_ZOOM = -1_000_000_00d;

        setTranslateZ(-30_000);
    }


    public void setMinZoom(double minZoom) {
        this.minZoom = minZoom;
    }

    public double getMinZoom() {
        return minZoom;
    }

    public double getMaxZoom() {
        return MAX_ZOOM;
    }
}
