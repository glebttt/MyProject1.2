package code.graphics.visuals.controllers;

import code.graphics.visuals.SolarSubScene;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class SolarKeyController implements EventHandler<KeyEvent> {
    private final SolarSubScene solarSubScene;

    public SolarKeyController(SolarSubScene solarSubScene) {
        this.solarSubScene = solarSubScene;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case DIGIT1 -> solarSubScene.setCurrentFocus("Probe");
            case DIGIT2 -> solarSubScene.setCurrentFocus("Sun");
            case DIGIT3 -> solarSubScene.setCurrentFocus("Titan");
            case DIGIT4 -> solarSubScene.setCurrentFocus("Earth");
            case DIGIT5 -> solarSubScene.setCurrentFocus("Saturn");
        }
    }
}
