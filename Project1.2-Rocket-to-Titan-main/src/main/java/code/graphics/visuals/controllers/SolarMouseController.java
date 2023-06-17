package code.graphics.visuals.controllers;

import code.graphics.visuals.SolarSubScene;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;

public class SolarMouseController {
    private double anchorX, anchorY;
    private double anchorAngleX, anchorAngleZ;
    private final Rotate xRotate, zRotate;

    public SolarMouseController(Scene scene, SolarSubScene solarSubScene) {
        Group root = (Group) solarSubScene.getRoot();
        xRotate = new Rotate(-45, Rotate.X_AXIS);
        zRotate = new Rotate(0, Rotate.Z_AXIS);

        root.getTransforms().addAll(xRotate, zRotate);

        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = xRotate.getAngle();
            anchorAngleZ = zRotate.getAngle();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - anchorX;
            double deltaY = event.getSceneY() - anchorY;

            double newXAngle = anchorAngleX + deltaY * 0.2;
            if (newXAngle < -90)
                newXAngle = -90;
            else if (newXAngle > 0)
                newXAngle = 0;

            xRotate.setAngle(newXAngle);
            zRotate.setAngle(anchorAngleZ - deltaX * 0.2);
        });
    }
}
