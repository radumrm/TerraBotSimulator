package entities.plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;
import map.Box;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.POINT_TWO;
import static utils.MagicNumber.D_3;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.POINT_FOUR;
import static utils.MagicNumber.POINT_SEVEN;
import static utils.MagicNumber.TWO;


@Getter
public abstract class Plant extends Entity {
    protected double maturity;

    public Plant(final PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
        maturity = 0.0;
        this.isPlant = true;
    }
    /**
     * todo
     * comentriu
     */
    public abstract double getPlantPossibility();
    /**
     * todo
     * comentriu
     */
    public double possibiltyToGetStuckInPlants() {
        return getPlantPossibility() / D_100;
    }
    /**
     * todo
     * comentriu
     */
    public void grow() {
        if (!isDead()) {
            double addedMaturity = POINT_TWO;
            this.maturity += addedMaturity;
            this.maturity = Math.round(this.maturity * D_100) / D_100;
        }
    }
    /**
     * todo
     * comentriu
     */
    public boolean isDead() {
        return maturity >= D_3;
    }
    /**
     * todo
     * comentriu
     */
    public abstract double getOxygenFromPlant();
    /**
     * todo
     * comentriu
     */
    public double maturityOxygenRate() {
        if (maturity < 1.0) {
            return POINT_TWO;
        } else if (maturity < TWO) {
            return POINT_SEVEN;
        } else if (maturity < D_3) {
            return POINT_FOUR;
        }
        return 0;
    }
    /**
     * todo
     * comentriu
     */
    public double oxygenLevel() {
        if (isDead()) {
            return 0;
        }
        return maturityOxygenRate() + getOxygenFromPlant();
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public String improveEnvironment(final Box box, final String improvementType) {
        box.getAir().addOxygenLevel(POINT_THREE);
        return "The " + this.name + " was planted successfully.";
    }
}
