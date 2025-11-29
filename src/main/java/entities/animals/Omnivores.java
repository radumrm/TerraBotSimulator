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
     * todo
     * comentriu
     */
    @Override
    public double getAnimalPossibilityToAttack() {
        return animalPossibilityToAttack;
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void eat(final Box box) {
        eatPlantOrWater(box);
    }
}
