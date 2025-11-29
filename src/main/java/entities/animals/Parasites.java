package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.D_10;
import static utils.MagicNumber.POINT_FIVE;

public class Parasites extends Animal {
    protected static double animalPossibilityToAttack = D_10;
    public Parasites(final AnimalInput input) {
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
        Animal prey = box.getAnimal();
        if (prey != null && prey != this) {
            this.mass += prey.getMass();
            prey.setDead();
            box.setAnimal(this);
            box.getSoil().addOrganicMatter(POINT_FIVE);
            return;
        }
        eatPlantOrWater(box);
    }
}
