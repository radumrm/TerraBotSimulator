package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.SIXTY;

public class Omnivores extends Animal {
    protected static double animalPossibilityToAttack = SIXTY;
    public Omnivores(final AnimalInput input) {
        super(input);
    }
    /**
     * Intoarce probabilitatea de a ataca specifica tipului de animal
     */
    @Override
    public double getAnimalPossibilityToAttack() {
        return animalPossibilityToAttack;
    }
    /**
     * Algoritm mancare pentru animal care poate manca doar apa si plante
     */
    @Override
    public void eat(final Box box) {
        eatPlantOrWater(box);
    }
}
