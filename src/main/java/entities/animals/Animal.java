package entities.animals;

import entities.Entity;
import fileio.AnimalInput;
import lombok.Getter;

@Getter
public abstract class Animal extends Entity {
    public enum State {
        HUNGRY,
        WELL_FED,
        SICK
    }

    protected State state;

    public Animal(AnimalInput animalInput) {
        super(animalInput.getName(), animalInput.getMass(), animalInput.getType());
    }

    public abstract double getAnimal_possibility_to_attack();

    public double PossibilityToBeAttackedByAnimal() {
        return (100 - this.getAnimal_possibility_to_attack()) / 10.0;
    }

}
