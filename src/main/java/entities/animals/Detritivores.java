package entities.animals;

import fileio.AnimalInput;

public class Detritivores extends  Animal {
    protected static double animal_possibility_to_attack = 90;
    public Detritivores(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimal_possibility_to_attack() {
        return animal_possibility_to_attack;
    }
}
