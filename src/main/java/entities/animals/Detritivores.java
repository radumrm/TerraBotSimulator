package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.D_90;

public class Detritivores extends  Animal {
    protected static double animalPossibilityToAttack = D_90;
    public Detritivores(final AnimalInput input) {
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
