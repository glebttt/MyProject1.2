import code.model.objects.PlanetObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlanetObjectTest {
    private PlanetObject planet1;
    private PlanetObject planet2;
    private static final double PRECISION = 1e-9;

    @BeforeEach
    public void setUp() {
        planet1 = new PlanetObject(new double[]{0, 0, 0}, new double[]{0, 0, 0});
        planet1.setMass(1000);
        planet2 = new PlanetObject(new double[]{1000, 1000, 1000}, new double[]{0, 0, 0});
        planet2.setMass(1000);
    }

    @Test
    public void testAccelerationBetween() {
        double G = 6.6743e-20;
        double expectedAcceleration = -G / (1000 * 1000 * 1000);
        assertArrayEquals(new double[]{expectedAcceleration, expectedAcceleration, expectedAcceleration}, planet1.accelerationBetween(planet2), PRECISION);
    }

    @Test
    public void testAccelerationBetweenSameLocation() {
        PlanetObject planet3 = new PlanetObject(new double[]{0, 0, 0}, new double[]{0, 0, 0});
        assertThrows(IllegalArgumentException.class, () -> planet1.accelerationBetween(planet3));
    }
}
