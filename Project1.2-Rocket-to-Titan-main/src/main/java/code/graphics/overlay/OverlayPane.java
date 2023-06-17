package code.graphics.overlay;

import code.model.Model;
import code.model.objects.Probe;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Defines an AnchorPane with some pre-defined labels which will display on top of the visualization and provide
 * information about the model in real time.
 */
public class OverlayPane extends AnchorPane {
    private final ProbeStatsLabel DISTANCE_LABEL;
    private final DateLabel DATE_LABEL;
    private final DecimalFormat DF;
    private final Probe PROBE;
    private final Font font = Font.font("Verdana", FontWeight.BOLD, 15);


    public OverlayPane() {
        super();
        PROBE = Model.getProbes().get(0);
        DISTANCE_LABEL = new ProbeStatsLabel();
        DATE_LABEL = new DateLabel();
        getChildren().add(new GuideLabel());
        getChildren().add(DISTANCE_LABEL);
        getChildren().add(DATE_LABEL);

        DF = new DecimalFormat("#.###");
        DF.setRoundingMode(RoundingMode.HALF_UP);

        update(0);
    }


    /**
     * Updates the labels
     */
    public void update(double day) {
        String labelText = "Distance to Titan: " + DF.format(PROBE.getDistanceToTitan()) + " km\nFuel used: " + DF.format(PROBE.getFuelUsed());
        DISTANCE_LABEL.setText(labelText);
        DATE_LABEL.setText("Day of Simulation: " + (int) day);
    }

    /**
     * Subclass defining the Label object displaying a guide to which key
     */
    private class GuideLabel extends Label {
        GuideLabel() {
            super("""
                    Spacecraft - [1]
                    Sun - [2]
                    Titan - [3]
                    Earth - [4]
                    Saturn - [5]
                    """);
            setFont(font);
            setStyle("-fx-line-spacing: 5;");
            setTextFill(Color.WHITE);
            setTextAlignment(TextAlignment.RIGHT);
            setBottomAnchor(this, 15d);
            setRightAnchor(this, 15d);
        }
    }

    /**
     * Subclass representing the label displaying distance to Titan and fuel used by the probe.
     */
    private class ProbeStatsLabel extends Label {
        ProbeStatsLabel() {
            super();
            setFont(font);
            setTextFill(Color.WHITE);
            setStyle("-fx-line-spacing: 5;");
            setTopAnchor(this, 10d);
            setLeftAnchor(this, 10d);
        }
    }

    /**
     * Subclass representing the label displaying the number of days from launch.
     */
    private class DateLabel extends Label {
        DateLabel() {
            super("Day of Simulation: ");
            setFont(font);
            setTextFill(Color.WHITE);
            setTopAnchor(this, 10d);
            setRightAnchor(this, 10d);
        }
    }
}
