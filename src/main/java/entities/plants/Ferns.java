package entities.plants;

import entities.plants.Plant;
import fileio.PlantInput;

public class Ferns extends Plant {
    protected static double plant_possibility = 30;
    public Ferns(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
}
