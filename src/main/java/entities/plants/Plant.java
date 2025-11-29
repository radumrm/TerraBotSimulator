package entities.plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public abstract class Plant extends Entity {
    protected double maturity;

    public Plant(PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
        maturity = 0.0;
        this.isPlant = true;
    }


    public abstract double getPlant_possibility();

    public double PossibiltyToGetStuckInPlants() {
        return getPlant_possibility() / D_100;
    }

    public void grow() {
        if (!isDead()) {
            double addedMaturity = POINT_TWO;
            this.maturity += addedMaturity;
            this.maturity = Math.round(this.maturity * D_100) / D_100;
        }
    }

    public boolean isDead() {
        return maturity >= 3;
    }

    public abstract double getOxygenFromPlant();

    public double maturityOxygenRate() {
        if (maturity < 1.0) {
            return POINT_TWO;
        } else if (maturity < 2.0) {
            return POINT_SEVEN;
        } else if (maturity < D_3) {
            return POINT_FOUR;
        }
        return 0;
    }

    public double oxygenLevel() {
        if (isDead()) {
            return 0;
        }
        return maturityOxygenRate() + getOxygenFromPlant();
    }

    @Override
    public String improveEnvironment(map.Box box, String improvementType) {
        box.getAir().addOxygenLevel(POINT_THREE);
        return "The " + this.name + " was planted successfully.";
    }
}