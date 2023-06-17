package code.graphics.visuals;

import code.graphics.visuals.controllers.SolarKeyController;
import code.model.Model;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;

/**
 * Class responsible for creating and managing the movement of the 3D objects in the visualization.
 */
public class SolarSubScene extends SubScene {
    private final SolarGroup SOLAR_GROUP;
    private final SolarCamera CAMERA;


    public SolarSubScene(Group root, double width, double height) {
        super(root, width, height, true, SceneAntialiasing.BALANCED);

        SOLAR_GROUP = new SolarGroup(Model.getPlanetObjects(), Model.getProbes());
        CAMERA = new SolarCamera();
        root.getChildren().add(SOLAR_GROUP);

        root.setTranslateX(width / 2);
        root.setTranslateY(height / 2);

        initializeSubScene();
    }


    private void initializeSubScene() {
        setFill(Color.BLACK.brighter());
        setCamera(CAMERA);
        new SolarKeyController(this);
        setCurrentFocus("Probe");
        rescaleObjects();
    }

    /**
     * Updates the positions of all 3D objects representations.
     */
    public void update() {
        SOLAR_GROUP.updateGroup();
    }

    public void rescaleObjects() {
        double zoomThreshold = -100;
        double maxZoom = -4_000_000_00d;
        double currentZoom = CAMERA.getTranslateZ();

        if (currentZoom > zoomThreshold) {
            SOLAR_GROUP.resizeObjects(0);
            return;
        }

        double zoomRatio = (currentZoom - zoomThreshold) / (maxZoom - zoomThreshold);
        zoomRatio = Math.max(0, Math.min(zoomRatio, 1));

        SOLAR_GROUP.resizeObjects(zoomRatio);
    }

    /**
     * Makes the passed object stay in the middle of the screen.
     *
     * @param planetName name of the object
     */
    public void setCurrentFocus(String planetName) {
        SOLAR_GROUP.setCurrentFocus(planetName);
        CAMERA.setMinZoom(-SOLAR_GROUP.getPlanetSphereByName(planetName).getMinRadius());
    }

    /**
     * Adds one piece of the trail at the coordinates of the Probe when run.
     */
    public void addTrail(){
        SOLAR_GROUP.addTrail();
    }
}
