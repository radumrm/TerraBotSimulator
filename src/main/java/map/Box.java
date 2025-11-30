package map;

import entities.plants.Plant;
import entities.animals.Animal;
import entities.environment.Water;
import entities.environment.air.Air;
import entities.environment.soil.Soil;
import lombok.Getter;
import lombok.Setter;

import static utils.MagicNumber.TWO;

@Getter
@Setter
public class Box {
    private Air air;
    private Soil soil;
    private Water water;
    private Plant plant;
    private Animal animal;

    /**
     * Calculam costul de miscare pe o celula vecina
     */
    public int getCost() {
        double sum = 0.0;
        double count = TWO;
        sum += soil.possibilityToGetStuckInSoil() + air.getToxicityAQ();
        if (animal != null) {
            sum += animal.possibilityToBeAttackedByAnimal();
            count++;
        }
        if (plant != null) {
            sum += plant.possibiltyToGetStuckInPlants();
            count++;
        }
        double mean = Math.abs(sum / count);
        return (int) Math.round(mean);
    }
}
