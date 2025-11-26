package entities.animals;

import fileio.AnimalInput;

public class Carnivores extends  Animal{
    protected static double animal_possibility_to_attack = 30;
    public Carnivores(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimal_possibility_to_attack() {
        return animal_possibility_to_attack;
    }
}
