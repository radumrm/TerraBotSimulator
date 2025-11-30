package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.THIRTY;

public class Carnivores extends  Animal {
    protected static double animalPossibilityToAttack = THIRTY;
    public Carnivores(final AnimalInput input) {
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
     * Algoritm mancare pentru animal care poate manca alte animale
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
