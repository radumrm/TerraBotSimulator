package entities.plants;

import fileio.PlantInput;

import static utils.MagicNumber.D_6;
import static utils.MagicNumber.D_90;

public class FloweringPlants extends Plant {
    protected static double plantPossibility = D_90;
    public FloweringPlants(final PlantInput plantInput) {
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
    protected static double oxygenFromPlant = D_6;
    /**
     * todo
     * comentriu
     */
    @Override
    public double getOxygenFromPlant() {
        return D_6;
    }
}
