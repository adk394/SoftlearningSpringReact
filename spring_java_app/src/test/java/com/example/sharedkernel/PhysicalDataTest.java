package com.example.sharedkernel;

import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("physicaldata - datos fisicos de un producto")
class PhysicalDataTest {

    private static final double WEIGHT = 1.5;
    private static final double HEIGHT = 20.0;
    private static final double WIDTH = 15.0;
    private static final double DEPTH = 5.0;

    private PhysicalData pd;

    @BeforeEach
    void setUp() throws BuildException {
        pd = PhysicalData.getInstance(WEIGHT, HEIGHT, WIDTH, DEPTH);
    }

    @Nested
    class GetInstance {

        @Test
        void validDataCreatesInstance() {
            assertEquals(WEIGHT, pd.getWeight());
        }

        @Test
        void throwsWhenWeightZeroOrNegative() {
            assertThrows(BuildException.class,
                    () -> PhysicalData.getInstance(0, HEIGHT, WIDTH, DEPTH));
        }
    }
}