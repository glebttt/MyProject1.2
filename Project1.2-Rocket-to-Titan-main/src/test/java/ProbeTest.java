import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ProbeTest {
    private Probe probe;
    private static final double PRECISION = 1e-9;

    @BeforeEach
    public void setUp() {
        Model.loadData(new FileDataLoader());
        probe = new Probe();
    }

    @Test
    public void testBoosterMECH() {
        probe.addBoost(new Boost(1, new double[]{1, 1, 1}));
        probe.BoosterMECH(1);
        assertArrayEquals(new double[]{1, 1, 1}, probe.getVelocity(), PRECISION);
    }

    @Test
    public void testBoosterMECHNoBoost() {
        probe.addBoost(new Boost(1, new double[]{1, 1, 1}));
        probe.BoosterMECH(0);
        assertArrayEquals(new double[]{0, 0, 0}, probe.getVelocity(), PRECISION);
    }
}
