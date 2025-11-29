package entities.animals;

import fileio.AnimalInput;
import map.Box;

public class Herbivores extends Animal {
    protected static double animal_possibility_to_attack = 85;
    public Herbivores(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimalPossibilityToAttack() {
        return animal_possibility_to_attack;
    }
    @Override
    public void eat(Box box) {
        eatPlantOrWater(box);
    }
}
