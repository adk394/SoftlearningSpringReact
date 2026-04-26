package com.example.sharedkernel;

import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("physicaldata")
class PhysicalDataTest {

    static final double VALID_WEIGHT = 0.5;
    static final double VALID_HEIGHT = 10.0;
    static final double VALID_WIDTH = 5.0;
    static final double VALID_DEPTH = 2.0;

    @Nested
    @DisplayName("validacion")
    class GetInstanceValid {
        @Test
        @DisplayName("valores válidos")
        void createInstance() throws BuildException {
            PhysicalData pd = PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
            assertAll(
                    () -> assertEquals(VALID_WEIGHT, pd.getWeight()),
                    () -> assertEquals(VALID_HEIGHT, pd.getHeight()),
                    () -> assertEquals(VALID_WIDTH, pd.getWidth()),
                    () -> assertEquals(VALID_DEPTH, pd.getDepth()));
        }
    }

    @Nested
    class GetInstanceInvalid {
        @Test
        @DisplayName("peso inválido")
        void weightInvalid() {
            assertThrows(BuildException.class,
                    () -> PhysicalData.getInstance(0, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH));
        }

        @Test
        @DisplayName("dimensiones inválidas")
        void dimsInvalid() {
            assertThrows(BuildException.class,
                    () -> PhysicalData.getInstance(VALID_WEIGHT, 0, VALID_WIDTH, VALID_DEPTH));
            assertThrows(BuildException.class,
                    () -> PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, 0, VALID_DEPTH));
            assertThrows(BuildException.class,
                    () -> PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, 0));
        }
    }

    @Nested
    @DisplayName("setters y cálculos")
    class SettersAndCalculations {
        @Test
        @DisplayName("setters")
        void settersWork() throws BuildException {
            PhysicalData pd = PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
            assertEquals(-1, pd.setWeight(0));
            assertEquals(0, pd.setWeight(1.2));
            assertEquals(0, pd.setHeight(2.0));
            assertEquals(-1, pd.setWidth(0));
            assertEquals(0, pd.setDepth(3.0));
        }

        @Test
        @DisplayName("volumen y area")
        void volumeArea() throws BuildException {
            PhysicalData pd = PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
            double expectedVolume = VALID_HEIGHT * VALID_WIDTH * VALID_DEPTH;
            double expectedArea = VALID_WIDTH * VALID_HEIGHT;
            assertEquals(expectedVolume, pd.getVolume(), 1e-6);
            assertEquals(expectedArea, pd.getArea(), 1e-6);
        }

        @Test
        @DisplayName("formato tamaño")
        void sizeFormat() throws BuildException {
            PhysicalData pd = PhysicalData.getInstance(VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
            String s = pd.getSize();
            assertTrue(s.contains("height:"));
            assertTrue(s.contains("width:"));
            assertTrue(s.contains("depth:"));
        }
    }
}
