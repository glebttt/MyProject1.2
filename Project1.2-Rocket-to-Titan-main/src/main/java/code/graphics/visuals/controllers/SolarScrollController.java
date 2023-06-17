package code.graphics.visuals.controllers;

import code.graphics.visuals.SolarCamera;
import code.graphics.visuals.SolarSubScene;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;

public class SolarScrollController {
    public SolarScrollController(Scene scene, SolarSubScene solarSubScene) {
        SolarCamera camera = (SolarCamera) solarSubScene.getCamera();
        addScrollHandler(scene, solarSubScene, camera);
    }


    private void addScrollHandler(Scene scene, SolarSubScene solarSubScene, SolarCamera camera) {
        scene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();

            double zoomStep;
            if(camera.getTranslateZ()> -4500000)
                zoomStep = 0.03;
            else
                zoomStep = 0.01;

            if (camera.getTranslateZ() - deltaY * camera.getTranslateZ() * zoomStep > camera.getMinZoom())
                camera.setTranslateZ(camera.getMinZoom());
            else
                camera.setTranslateZ(Math.max(camera.getTranslateZ() - deltaY * camera.getTranslateZ() * zoomStep, camera.getMaxZoom()));

            solarSubScene.rescaleObjects();
        });
    }
}
