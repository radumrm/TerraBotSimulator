package entities.animals;

import fileio.AnimalInput;
import map.Box;

public class Omnivores extends Animal {
    protected static double animal_possibility_to_attack = 60;
    public Omnivores(AnimalInput input) {
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
