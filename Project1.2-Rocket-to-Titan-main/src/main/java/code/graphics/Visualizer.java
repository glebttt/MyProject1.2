package code.graphics;

import code.algorithms.ModelRunner;
import code.graphics.overlay.OverlayPane;
import code.graphics.visuals.SolarSubScene;
import code.graphics.visuals.controllers.SolarKeyController;
import code.graphics.visuals.controllers.SolarMouseController;
import code.graphics.visuals.controllers.SolarScrollController;
import code.model.Model;
import code.model.objects.Boost;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Timer;

/**
 * code.Main graphic class which sets up the Scene and runs the engine which updates all 3D representations of
 * objects in the model in real-time.
 */
public class Visualizer extends Application {
    public static final int SCALE = 50; // don't change this
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private SolarSubScene solarSubScene;
    private OverlayPane overlayPane;
    private double time = 0;
    private Timer timer;
    private int count;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Mission to Titan");
        stage.show();

        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, WIDTH, HEIGHT, true);

        solarSubScene = new SolarSubScene(new Group(), WIDTH, HEIGHT);
        new SolarMouseController(scene, solarSubScene);
        new SolarScrollController(scene, solarSubScene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SolarKeyController(solarSubScene));
        stackPane.getChildren().add(solarSubScene);

        overlayPane = new OverlayPane();
        stackPane.getChildren().add(overlayPane);

        stage.setScene(scene);

        timer = new Timer();
        count = 0;
        stage.setOnCloseRequest(e -> {
            timer.cancel();
            Platform.exit();
            System.exit(0);
        });
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> calculation());
        new Thread(sleeper).start();
    }


    private void calculation() {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (Model.getProbes().get(0).getDistanceToTitan() > Model.getProbes().get(0).getShortestDistanceToTitan()) {
                            Model.getProbes().get(0).setVelocity(new double[]{-128.99164151418873, 46.449291805183115, 3.3594162321263057});
                        }
                        for (int i = 0; i < 10; i++) {
                            double day = time / (60 * 60 * 24);
                            time = ModelRunner.runnerForGUI(time, 180, 4, Model.getPlanetObjectsArrayList(), Model.getProbes());
                            Platform.runLater(() -> {
                                solarSubScene.update();
                                overlayPane.update(day);
                            });
                        }

                        count++;
                        if (count % 25 == 0)
                            Platform.runLater(() -> solarSubScene.addTrail());
                    }
                }, 0, 1);
    }

    public static void main(String[] args) {
        launch();
    }
}
