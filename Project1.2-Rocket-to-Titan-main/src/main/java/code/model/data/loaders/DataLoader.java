package code.model.data.loaders;

import code.model.objects.PlanetObject;

import java.util.Map;

public interface DataLoader {
    /**
     * Loads the information about celestial bodies from a selected location,
     * creates the PlanetObject objects and puts them into the passed Map.
     *
     * @param planetObjects an empty Map where after loading the keys will be strings representing names of
     *                      celestial bodies and values PlanetObject objects
     */
    void load(Map<String, PlanetObject> planetObjects);
}
