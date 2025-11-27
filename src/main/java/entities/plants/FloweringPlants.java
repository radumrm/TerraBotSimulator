package entities.plants;

import fileio.PlantInput;

public class FloweringPlants extends Plant {
    protected static double plant_possibility = 90;
    public FloweringPlants(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
    protected static double oxygenFromPlant = 6.0;
    @Override
    public double getOxygenFromPlant() {
        return 6.0;
    }
}
