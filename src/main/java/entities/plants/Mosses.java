package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.POINT_EIGHT;

public class Mosses extends Plant {
    protected static double plant_possibility = 40;
    public Mosses(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
    protected static double oxygenFromPlant = POINT_EIGHT;
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
