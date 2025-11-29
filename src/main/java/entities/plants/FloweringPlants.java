package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.D_6;

public class FloweringPlants extends Plant {
    protected static double plant_possibility = 90;
    public FloweringPlants(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
    protected static double oxygenFromPlant = D_6;
    @Override
    public double getOxygenFromPlant() {
        return D_6;
    }
}
