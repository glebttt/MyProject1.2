package code.graphics.visuals;

import code.graphics.visuals.objects.ProbeSphere;
import code.graphics.visuals.objects.TrailSphere;
import code.graphics.visuals.objects.PlanetSphere;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;

import java.util.*;

import static code.graphics.Visualizer.SCALE;

/**
 * Class representing the group containing 3D representations of the objects in the model.
 */
public class SolarGroup extends Group {
    private final Map<String, PlanetSphere> PLANET_SPHERES;
    private final List<ProbeSphere> PROBE_SPHERES;
    private PlanetSphere currentFocus;


    public SolarGroup(Map<String, PlanetObject> planetObjects, List<Probe> probes) {
        super();

        PLANET_SPHERES = new HashMap<>();
        PROBE_SPHERES = new ArrayList<>();

        initializePlanetSpheres(planetObjects);
        initializeProbes(probes);

        setCurrentFocus("Probe");
        updateGroup();
    }


    /**
     * Calls the updateCoordinates method of every PlanetSphere object in the Group to set its coordinates
     * to ones determined by the associated PlanetObject.
     */
    public void updateGroup() {
        for (PlanetSphere planetSphere : PLANET_SPHERES.values())
            planetSphere.updateCoordinates();

        for (PlanetSphere probeSphere : PROBE_SPHERES)
            probeSphere.updateCoordinates();

        setTranslateX(-currentFocus.getTranslateX());
        setTranslateY(-currentFocus.getTranslateY());
        setTranslateZ(-currentFocus.getTranslateZ());
    }

    private void initializePlanetSpheres(Map<String, PlanetObject> planetObjects) {
        for (Map.Entry<String, PlanetObject> planetObject : planetObjects.entrySet()) {
            // create planet sphere
            PlanetSphere planetSphere = new PlanetSphere(planetObject.getValue());
            PLANET_SPHERES.put(planetObject.getKey(), planetSphere);

            // set texture
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/textures/" + planetObject.getKey().toLowerCase() + ".jpg"))));
            planetSphere.setMaterial(material);

            // add to group
            getChildren().add(planetSphere);
        }

        setMaxRadii();
    }

    private void initializeProbes(List<Probe> probes) {
        for (Probe probe : probes) {
            ProbeSphere probeSphere = new ProbeSphere(probe);
            PROBE_SPHERES.add(probeSphere);
            getChildren().add(probeSphere);
            for (TrailSphere trailSphere : probeSphere.getTrails())
                getChildren().add(trailSphere);
        }
    }

    public void resizeObjects(double zoomRatio) {
        for (PlanetSphere planetSphere : PLANET_SPHERES.values()) {
            double minRadius = planetSphere.getMinRadius();
            double newRadius = minRadius + zoomRatio * (planetSphere.getMaxRadius() - minRadius);

            planetSphere.setRadius(newRadius);
        }
        for (ProbeSphere probeSphere : PROBE_SPHERES) {
            double minRadius = probeSphere.getMinRadius();
            double newRadius = minRadius + zoomRatio * (probeSphere.getMaxRadius() - minRadius);

            probeSphere.setRadius(newRadius);

            for (TrailSphere trailSphere : probeSphere.getTrails()) {
                double minTrailRadius = trailSphere.getMinRadius();
                double newTrailRadius = minTrailRadius + zoomRatio * (trailSphere.getMaxRadius() - minTrailRadius);

                trailSphere.setRadius(newTrailRadius);
            }
        }
    }

    public void addTrail() {
        PROBE_SPHERES.get(0).moveTrail();
    }


    // GETTERS AND SETTERS

    /**
     * Sets the maximal radius for the PlanetSphere objects in the visualization for the purpose of maintaining a balance between scale and visibility when zooming
     */
    private void setMaxRadii() {
        PLANET_SPHERES.get("Sun").setMaxRadius(PLANET_SPHERES.get("Sun").getMinRadius() * 20000 / SCALE);
        PLANET_SPHERES.get("Mercury").setMaxRadius(PLANET_SPHERES.get("Mercury").getMinRadius() * 1000000 / SCALE);
        PLANET_SPHERES.get("Venus").setMaxRadius(PLANET_SPHERES.get("Venus").getMinRadius() * 500000 / SCALE);
        PLANET_SPHERES.get("Earth").setMaxRadius(PLANET_SPHERES.get("Earth").getMinRadius() * 1000000 / SCALE);
        PLANET_SPHERES.get("Moon").setMaxRadius(PLANET_SPHERES.get("Moon").getMinRadius() * 900000 / SCALE);
        PLANET_SPHERES.get("Mars").setMaxRadius(PLANET_SPHERES.get("Mars").getMinRadius() * 800000 / SCALE);
        PLANET_SPHERES.get("Jupiter").setMaxRadius(PLANET_SPHERES.get("Jupiter").getMinRadius() * 200000 / SCALE);
        PLANET_SPHERES.get("Saturn").setMaxRadius(PLANET_SPHERES.get("Saturn").getMinRadius() * 200000 / SCALE);
        PLANET_SPHERES.get("Titan").setMaxRadius(PLANET_SPHERES.get("Titan").getMinRadius() * 1000000 / SCALE);
        PLANET_SPHERES.get("Neptune").setMaxRadius(PLANET_SPHERES.get("Neptune").getMinRadius() * 300000 / SCALE);
        PLANET_SPHERES.get("Uranus").setMaxRadius(PLANET_SPHERES.get("Uranus").getMinRadius() * 200000 / SCALE);
    }

    public void setCurrentFocus(String planetName) {
        if (planetName.equals("Probe"))
            this.currentFocus = PROBE_SPHERES.get(0);
        else
            this.currentFocus = PLANET_SPHERES.get(planetName);
    }

    public PlanetSphere getPlanetSphereByName(String planetName) {
        if (planetName.equals("Probe"))
            return PROBE_SPHERES.get(0);
        else
            return PLANET_SPHERES.get(planetName);
    }
}
