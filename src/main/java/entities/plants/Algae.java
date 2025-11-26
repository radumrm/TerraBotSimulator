package entities.plants;

import fileio.PlantInput;

public class Algae extends Plant {
    protected static double plant_possibility = 20;
    public Algae(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
}
