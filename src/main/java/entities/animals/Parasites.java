package entities.animals;

import fileio.AnimalInput;
import map.Box;

import static utils.MagicNumber.D_10;

public class Parasites extends Animal {
    protected static double animal_possibility_to_attack = D_10;
    public Parasites(AnimalInput input) {
        super(input);
    }
    @Override
    public double getAnimal_possibility_to_attack() {
        return animal_possibility_to_attack;
    }
    @Override
    public void eat(Box box) {
        Animal prey = box.getAnimal();
        if (prey != null && prey != this) {
            this.mass += prey.getMass();
            prey.setDead();
            box.setAnimal(this);
            box.getSoil().addOrganicMatter(0.5);
            return;
        }
        eatPlantOrWater(box);
    }
}
