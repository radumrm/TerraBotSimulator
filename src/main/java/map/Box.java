package map;

import entities.plants.Plant;
import entities.animals.Animal;
import entities.environment.Water;
import entities.environment.air.Air;
import entities.environment.soil.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Box {
    private Air air;
    private Soil soil;
    private Water water;
    private Plant plant;
    private Animal animal;
}
