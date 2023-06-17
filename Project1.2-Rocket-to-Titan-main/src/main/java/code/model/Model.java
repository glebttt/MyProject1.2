package code.model;

import code.model.data.loaders.DataLoader;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Class representing the objects in the simulation. It holds the PlanetObject objects and probes and
 * is responsible for loading their properties.
 *
 * <p>
 * It is a singleton to ensure that there is only one instance of every modeled object.
 * </p>
 *
 * <p>
 * It loads masses and radii of planets from xlsx files in resources.
 * It loads the initial coordinates and velocities depending on the data loader.
 * </p>
 */
public class Model {
    private Map<String, PlanetObject> planetObjects;
    private List<Probe> probes;


    private Model() {
    }

    /**
     * Subclass holding the instance of the model.
     * Allows use of the singleton design without calling the instance each time.
     */
    private static final class InstanceHolder {
        private static final Model INSTANCE = new Model();
    }

    /**
     * Used inside this class to refer to the instance holding the objects.
     *
     * @return the Model instance
     */
    public static Model getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * @return a Map where the name of each celestial body starting with a capital letter is the key and
     * the PlanetObject corresponding to it is the value
     */
    public static Map<String, PlanetObject> getPlanetObjects() {
        return getInstance().planetObjects;
    }

    /**
     * Returns an ArrayList of PlanetObjects for when they need to be accessed in this particular order.
     *
     * @return an ArrayList of PlanetObjects in the model
     */
    public static ArrayList<PlanetObject> getPlanetObjectsArrayList() {
        ArrayList<PlanetObject> planets = new ArrayList<>();
        planets.add(Model.getPlanetObjects().get("Sun"));
        planets.add(Model.getPlanetObjects().get("Mercury"));
        planets.add(Model.getPlanetObjects().get("Venus"));
        planets.add(Model.getPlanetObjects().get("Earth"));
        planets.add(Model.getPlanetObjects().get("Moon"));
        planets.add(Model.getPlanetObjects().get("Mars"));
        planets.add(Model.getPlanetObjects().get("Jupiter"));
        planets.add(Model.getPlanetObjects().get("Saturn"));
        planets.add(Model.getPlanetObjects().get("Titan"));
        planets.add(Model.getPlanetObjects().get("Neptune"));
        planets.add(Model.getPlanetObjects().get("Uranus"));
        return planets;
    }

    public static void freezeModel(){
        for(PlanetObject planet:getPlanetObjectsArrayList())
            planet.setVelocity(new double[]{0, 0, 0});
        for (Probe probe:getProbes())
            probe.setVelocity(new double[]{0, 0, 0});
    }

    /**
     * @return a List of Probe objects in the model
     */
    public static List<Probe> getProbes() {
        return getInstance().probes;
    }

    /**
     * Adds a Probe object to the model
     *
     * @param probe the Probe object to be added to the model
     */
    public static void addProbe(Probe probe) {
        getInstance().probes.add(probe);
    }

    /**
     * For hill climbing algorithm. Removes all other probes from the model and keeps only the one passed in parameter.
     *
     * @param probe the Probe object to be kept in the model
     */
    public static void chooseProbe(Probe probe) {
        getInstance().probes = new ArrayList<>();
        addProbe(probe);
    }

    /**
     * Loads the information about initial positions and velocities of the celestial bodies to the model.
     *
     * @param dataLoader depending on the implementation of the interface passed in this argument
     *                   data from different sources can be chosen.
     */
    public static void loadData(DataLoader dataLoader) {
        getInstance().planetObjects = new HashMap<>();
        getInstance().probes = new ArrayList<>();
        dataLoader.load(getInstance().planetObjects);
        getInstance().loadRadii();
        getInstance().loadMass();
    }

    /**
     * Loads the radii of each celestial body from the xlsx file in resources and assigns it to the
     * PlanetObjects in the model.
     */
    private void loadRadii() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/radius.xlsx")) {
            assert inputStream != null;
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    String name = sheet.getRow(index).getCell(0).getStringCellValue();
                    double radius = sheet.getRow(index).getCell(1).getNumericCellValue();

                    planetObjects.get(name).setRadius(radius);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Loads the mass of each celestial body from the xlsx file in resources and assigns it to the
     * PlanetObjects in the model.
     */
    private void loadMass() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/mass.xlsx")) {
            assert inputStream != null;
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    String name = sheet.getRow(index).getCell(0).getStringCellValue();
                    double mass = sheet.getRow(index).getCell(1).getNumericCellValue();

                    planetObjects.get(name).setMass(mass);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
