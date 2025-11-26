package entities.animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
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

    @JsonIgnore
    protected State state;

    public Animal(AnimalInput animalInput) {
        super(animalInput.getName(), animalInput.getMass(), animalInput.getType());
    }

    @JsonIgnore
    public abstract double getAnimal_possibility_to_attack();

    @JsonIgnore
    public double getAttack_probability () {
        return (100 - this.getAnimal_possibility_to_attack()) / 10.0;
    }

}
