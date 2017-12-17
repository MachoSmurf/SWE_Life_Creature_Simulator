package ModelPackage;

/**
 * Provides all the information that has to be shown to any user. An instance of this object can also be used to show
 * the result of a simulation. Immutable Object
 */
public class StepResult {

    private GridClone currentGrid;
    private int nonivoreCount;
    private int herbivoreCount;
    private int carnivoreCount;
    private int omnivoreCount;
    private int plantCount;

    public GridClone getCurrentGrid() {
        return currentGrid;
    }

    public int getNonivoreCount() {
        return nonivoreCount;
    }

    public int getHerbivoreCount() {
        return herbivoreCount;
    }

    public int getCarnivoreCount() {
        return carnivoreCount;
    }

    public int getOmnivoreCount() {
        return omnivoreCount;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public int getEnergyNonivore() {
        return energyNonivore;
    }

    public int getEnergyCarnivore() {
        return energyCarnivore;
    }

    public int getEnergyOmnivore() {
        return energyOmnivore;
    }

    public int getEnergyHerbivore() {
        return energyHerbivore;
    }

    public int getEnergyPlants() {
        return energyPlants;
    }

    public int getStepCount() {
        return stepCount;
    }

    private int energyNonivore;
    private int energyCarnivore;
    private int energyOmnivore;
    private int energyHerbivore;
    private int energyPlants;
    private int stepCount;


    public StepResult(GridClone currentGrid, int nonivoreCount, int herbivoreCount, int carnivoreCount, int omnivoreCount, int plantCount, int energyNonivore, int energyCarnivore, int energyOmnivore, int energyHerbivore, int energyPlants, int stepCount) {
        this.currentGrid = currentGrid;
        this.nonivoreCount = nonivoreCount;
        this.herbivoreCount = herbivoreCount;
        this.carnivoreCount = carnivoreCount;
        this.omnivoreCount = omnivoreCount;
        this.plantCount = plantCount;
        this.energyNonivore = energyNonivore;
        this.energyCarnivore = energyCarnivore;
        this.energyOmnivore = energyOmnivore;
        this.energyHerbivore = energyHerbivore;
        this.energyPlants = energyPlants;
        this.stepCount = stepCount;
    }


}
