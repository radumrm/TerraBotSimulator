package entities.animals;

import fileio.AnimalInput;
import map.Box;

public class Omnivores extends Animal {
    protected static double animal_possibility_to_attack = 60;
    public Omnivores(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimal_possibility_to_attack() {
        return animal_possibility_to_attack;
    }
    @Override
    public void eat(Box box) {
        eatPlantOrWater(box);
    }
}
