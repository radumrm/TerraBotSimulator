package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.POINT_EIGHT;
import static utils.MagicNumber.D_40;

public class Mosses extends Plant {
    protected static double plantPossibility = D_40;
    public Mosses(final PlantInput plantInput) {
        super(plantInput);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double getPlantPossibility() {
        return plantPossibility;
    }
    protected static double oxygenFromPlant = POINT_EIGHT;
    /**
     * todo
     * comentriu
     */
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
