package entities.plants;

import entities.plants.Plant;
import fileio.PlantInput;

import static utils.MagicNumber.THIRTY;

public class Ferns extends Plant {
    protected static double plant_possibility = THIRTY;
    public Ferns(PlantInput plantInput) {
        super(plantInput);
    }
    @Override
    public double getPlant_possibility() {
        return plant_possibility;
    }
    protected static double oxygenFromPlant = 0.0;
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
