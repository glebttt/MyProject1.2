import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests methods in utils.HelperFunctions
 */
class HelperFunctionsTest {
    @Test
    public void testGetDistanceBetween() {
        PlanetObject one = new PlanetObject(new double[]{0, 0, 5}, new double[]{0, 0, 0});
        PlanetObject two = new PlanetObject(new double[]{0, 0, 0}, new double[]{0, 0, 0});
        double expected = 5;
        double actual = HelperFunctions.getDistanceBetween(one, two);
        assertEquals(expected, actual);
    }

    @Test
    void testGetVectorMagnitude() {
        double[] vector = {3, 4, 5};

        double expectedMagnitude = Math.sqrt(50);
        double actualMagnitude = HelperFunctions.getVectorMagnitude(vector);

        assertEquals(expectedMagnitude, actualMagnitude);
    }


    @Test
    void testGetDistanceBetweenWithVectors() {
        double[] one = new double[]{0, 0, 0};
        double[] two = new double[]{3, 4, 0};

        double distance = HelperFunctions.getDistanceBetweenWithVectors(one, two);
        assertEquals(5, distance, "The distance between the two vectors should be 5");
    }

    @Test
    void testGetDistanceBetweenWithVectors_EdgeCase() {
        double[] one = new double[]{Double.MAX_VALUE, 0, 0};
        double[] two = new double[]{-Double.MAX_VALUE, 0, 0};

        double distance = HelperFunctions.getDistanceBetweenWithVectors(one, two);
        assertEquals(Double.POSITIVE_INFINITY, distance, "The distance between the two vectors should be infinite");
    }

    @Test
    void testAddition() {
        double[] vectorOne = new double[]{5, 7, 9};
        double[] vectorTwo = new double[]{2, 3, 4};

        double[] result = HelperFunctions.addition(vectorOne, vectorTwo);
        assertArrayEquals(new double[]{7, 10, 13}, result, "The resulting vector after addition should be {7, 10, 13}");
    }

    @Test
    void testSubtraction() {
        double[] vectorOne = new double[]{9, 7, 15};
        double[] vectorTwo = new double[]{3, 5, 11};

        double[] result = HelperFunctions.subtract(vectorOne, vectorTwo);

        assertArrayEquals(new double[]{6, 2, 4}, result, "The resulting vector after subtraction should be {6, 2, 4}");
    }

    @Test
    void testStringToVector() {
        String input = "X = 1.0 Y = 2.0 Z = 3.0";
        double[] result = HelperFunctions.stringToVector(input);
        assertArrayEquals(new double[]{1, 2, 3}, result, "The resulting vector should be {1, 2, 3}");
    }

    @Test
    void testStringToVector_EdgeCase() {
        String input = "X = " + Double.MAX_VALUE + " Y = " + (-Double.MAX_VALUE) + " Z = 0";
        double[] result = HelperFunctions.stringToVector(input);
        assertArrayEquals(new double[]{Double.MAX_VALUE, -Double.MAX_VALUE, 0}, result, "The resulting vector should be {Double.MAX_VALUE, -Double.MAX_VALUE, 0}");
    }
}
