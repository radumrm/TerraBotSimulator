package entities.plants;

import entities.Entity;
import fileio.PlantInput;
import lombok.Getter;
import map.Box;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.POINT_TWO;
import static utils.MagicNumber.D_3;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.POINT_FOUR;
import static utils.MagicNumber.POINT_SEVEN;
import static utils.MagicNumber.TWO;


@Getter
public abstract class Plant extends Entity {
    protected double maturity;

    public Plant(final PlantInput plant) {
        super(plant.getName(), plant.getMass(), plant.getType());
        maturity = 0.0;
        this.isPlant = true;
    }
    /**
     * Returneaza posibilitatea plantei de a bloca robotul specifica tipului
     */
    public abstract double getPlantPossibility();
    /**
     * Returneaza calculul posibilitatii
     */
    public double possibiltyToGetStuckInPlants() {
        return getPlantPossibility() / D_100;
    }
    /**
     * Functie de maturizare a plantei pentru fiecare iteratie
     */
    public void grow() {
        if (!isDead()) {
            double addedMaturity = POINT_TWO;
            this.maturity += addedMaturity;
            this.maturity = Math.round(this.maturity * D_100) / D_100;
        }
    }
    /**
     * Verificare daca planta e moarta
     */
    public boolean isDead() {
        return maturity >= D_3;
    }
    /**
     * Returneaza oxigenul plantei in functie de tipul plantei
     */
    public abstract double getOxygenFromPlant();
    /**
     * Calculeaza maturitatea
     */
    public double maturityOxygenRate() {
        if (maturity < 1.0) {
            return POINT_TWO;
        } else if (maturity < TWO) {
            return POINT_SEVEN;
        } else if (maturity < D_3) {
            return POINT_FOUR;
        }
        return 0;
    }
    /**
     * Calcului oxygenLevel-ului
     */
    public double oxygenLevel() {
        if (isDead()) {
            return 0;
        }
        return maturityOxygenRate() + getOxygenFromPlant();
    }
    /**
     * Functie de improve env
     */
    @Override
    public String improveEnvironment(final Box box, final String improvementType) {
        box.getAir().addOxygenLevel(POINT_THREE);
        return "The " + this.name + " was planted successfully.";
    }
}
