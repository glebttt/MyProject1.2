package code.graphics.visuals.objects;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class TrailSphere extends Sphere {
    private final double MIN_RADIUS;
    private final double MAX_RADIUS;


    public TrailSphere(double probeMinRadius, double probeMaxRadius) {
        setVisible(false);
        MIN_RADIUS = probeMinRadius / 2;
        MAX_RADIUS = probeMaxRadius / 3;
        setMaterial(new PhongMaterial(Color.rgb(182, 255, 46)));
    }


    public double getMinRadius() {
        return MIN_RADIUS;
    }

    public double getMaxRadius() {
        return MAX_RADIUS;
    }
}
