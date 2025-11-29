package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.POINT_FIVE;

public class Algae extends Plant {
    protected static double plant_possibility = 20;
    public Algae(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
    protected static double oxygenFromPlant = POINT_FIVE;
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
