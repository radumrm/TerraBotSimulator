package entities.plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;

@Getter
public abstract class Plant extends Entity {
    protected double maturity;

    public Plant(PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
        maturity = 0.0;
    }


    public abstract double getPlant_possibility();

    public double PossibiltyToGetStuckInPlants() {
        return getPlant_possibility() / 100.0;
    }

    public void grow() {
        if (!isDead()) {
            double addedMaturity = 0.2;
            this.maturity += addedMaturity;
            this.maturity = Math.round(this.maturity * 100.0) / 100.0;
        }
    }

    public boolean isDead() {
        return maturity >= 3;
    }

    public abstract double getOxygenFromPlant();

    public double maturityOxygenRate() {
        if (maturity < 1.0) {
            return 0.2;
        } else if (maturity < 2.0) {
            return 0.7;
        } else if (maturity < 3.0) {
            return 0.4;
        }
        return 0;
    }

    public double oxygenLevel() {
        if (isDead()) {
            return 0;
        }
        return maturityOxygenRate() + getOxygenFromPlant();
    }
}