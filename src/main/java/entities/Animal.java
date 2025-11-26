package entities;

import fileio.AnimalInput;
import lombok.Getter;

@Getter
public class Animal extends Entity {
    public Animal(AnimalInput animal) {
        super(animal.getName(), animal.getMass(), animal.getType());
    }
}
