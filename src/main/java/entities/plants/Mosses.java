package entities.plants;

import fileio.PlantInput;

public class Mosses extends Plant {
    protected static double plant_possibility = 40;
    public Mosses(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
}
