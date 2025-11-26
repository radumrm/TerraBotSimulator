package entities.plants;

import fileio.PlantInput;

public class GymnospermsPlants extends Plant {
    protected static double plant_possibility = 60;
    public GymnospermsPlants(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
}
