package entities.plants;

import fileio.PlantInput;

public class Gymnosperms extends Plant {
    protected static double plant_possibility = 60;
    public Gymnosperms(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
}
