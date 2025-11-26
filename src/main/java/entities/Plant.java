package entities;

import fileio.PlantInput;
import lombok.Getter;

@Getter
public class Plant extends Entity {
    public Plant(PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
    }
}
