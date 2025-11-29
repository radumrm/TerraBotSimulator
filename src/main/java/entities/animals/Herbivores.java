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
