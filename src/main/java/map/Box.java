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

    public int getCost() {
        double sum = 0.0;
        double count = 2.0;
        sum += soil.possibilityToGetStuckInSoil() + air.getToxicityAQ();
        if (animal != null) {
            sum += animal.possibilityToBeAttackedByAnimal();
            count++;
        }
        if (plant != null) {
            sum += plant.PossibiltyToGetStuckInPlants();
            count++;
        }
        double mean = Math.abs(sum / count);
        return (int) Math.round(mean);
    }
}
