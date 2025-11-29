package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.D_10;
import static utils.MagicNumber.POINT_FIVE;

public class Algae extends Plant {
    protected static double plantPossibility = D_10 + D_10;
    public Algae(final PlantInput plantInput) {
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
    protected static double oxygenFromPlant = POINT_FIVE;
    /**
     * todo
     * comentriu
     */
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
