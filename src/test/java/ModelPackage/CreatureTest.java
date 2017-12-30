package ModelPackage;


import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CreatureTest {

    Creature beast1;
    Creature beast2;
    Creature beast3;

    @org.junit.Before
    public void setUp() throws Exception {
        List<TargetCoordinate> nextSteps = new ArrayList<>();
        new TargetCoordinate();
        nextSteps.add(new TargetCoordinate());

        beast1 = new Creature(
                new Point(20,20),
                1000,
                Digestion.Omnivore,
                60,
                1500,
                6,
                600,
                30,
                1200,
                600,
                400,
                null);
        beast2 = new Creature(
                new Point(20,20),
                900,
                Digestion.Omnivore,
                0,
                1500,
                6,
                600,
                30,
                1200,
                600,
                400,
                null);

        beast3 = beast1.mate(beast2);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMateEnergy () {
        assertEquals("energy beast1",550, beast1.getEnergy());
        assertEquals("energy beast2", 450, beast2.getEnergy());
        assertEquals("energy beast3",900, beast3.getEnergy());
    }

    @Test
    public void testMateStrength () {
        assertEquals("strength beast3", 1200, beast3.getStrength());
    }

    @Test
    public void testMateHunger () {
        assertEquals(600, beast3.getHunger());
    }

}