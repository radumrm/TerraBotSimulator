package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.THIRTY;

public class Ferns extends Plant {
    protected static double plantPossibility = THIRTY;
    public Ferns(final PlantInput plantInput) {
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
    protected static double oxygenFromPlant = 0;
    /**
     * todo
     * comentriu
     */
    @Override
    public double getOxygenFromPlant() {
        return oxygenFromPlant;
    }
}
