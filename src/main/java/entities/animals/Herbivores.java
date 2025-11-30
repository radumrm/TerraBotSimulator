package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.D_85;

public class Herbivores extends Animal {
    protected static double animalPossibilityToAttack = D_85;
    public Herbivores(final AnimalInput input) {
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
