package ModelPackage;


import org.junit.Test;

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
                20,
                20,
                1000,
                Digestion.Omnivore,
                0,
                1500,
                6,
                600,
                30,
                1200,
                600,
                400,
                nextSteps);
        beast2 = new Creature(
                20,
                20,
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
                nextSteps);

        beast3 = beast1.mate(beast2);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMateEnergy () {
        assertEquals("energy beast1",550, beast1.getEnegry());
        assertEquals("energy beast2", 450, beast2.getEnegry());
        assertEquals("energy beast3",900, beast3.getEnegry());

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