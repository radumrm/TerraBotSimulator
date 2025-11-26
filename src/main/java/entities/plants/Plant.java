package entities.plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;

@Getter
public abstract class Plant extends Entity {
    public Plant(PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
        maturity = 0;
    }

    protected double maturity;

    public abstract double getPlant_possibility();

    public double PossibiltyToGetStuckInPlants() {
        return getPlant_possibility() / 100.0;
    }
}