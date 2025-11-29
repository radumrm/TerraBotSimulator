package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.D_10;
import static utils.MagicNumber.POINT_FIVE;

public class Parasites extends Animal {
    protected static double animal_possibility_to_attack = D_10;
    public Parasites(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimalPossibilityToAttack() {
        return animal_possibility_to_attack;
    }
    @Override
    public void eat(Box box) {
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
