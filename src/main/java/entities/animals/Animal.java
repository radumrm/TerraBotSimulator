package entities.animals;
import entities.Entity;
import entities.environment.Water;
import entities.plants.Plant;
import fileio.AnimalInput;
import lombok.Getter;
import map.Box;
import map.SimulationMap;
import robot.TerraBot;

import java.util.ArrayList;
import java.util.List;

import static utils.MagicNumber.D_10;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.POINT_ZERO_EIGHT;
import static utils.MagicNumber.POINT_EIGHT;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.FOUR;


@Getter
public abstract class Animal extends Entity {
    public enum State {
        HUNGRY,
        WELL_FED,
        SICK
    }

    protected State state;

    public Animal(final AnimalInput animalInput) {
        super(animalInput.getName(), animalInput.getMass(), animalInput.getType());
        this.isAnimal = true;
    }
    /**
     * todo
     * comentriu
     */
    public abstract double getAnimalPossibilityToAttack();
    /**
     * todo
     * comentriu
     */
    public double possibilityToBeAttackedByAnimal() {
        return (ONE_HUNDRED - this.getAnimalPossibilityToAttack()) / D_10;
    }
    /**
     * todo
     * comentriu
     */
    private void executeMove(final SimulationMap simulationMap, final int newX, final int newY) {
        simulationMap.getBox(this.x, this.y).setAnimal(null);

        this.x = newX;
        this.y = newY;

    }

    private int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    /**
     * todo
     * comentriu
     */
    public void move(final SimulationMap simulationMap, final TerraBot terraBot) {
        int currentX = this.getX();
        int currentY = this.getY();
        int width = simulationMap.getWidth();
        int height = simulationMap.getHeight();

        int newX = -1;
        int newY = -1;
        double maxWaterQuality = -1;

        // Generating water and plant lists for every neighbour in the right order
        List<Water> waterList = new ArrayList<Water>();
        List<Plant> plantList = new ArrayList<Plant>();

        // Populating the lists
        for (int[] direction : directions) {
            int auxX = currentX + direction[0];
            int auxY = currentY + direction[1];
            if (auxX >= 0 && auxX < width && auxY >= 0 && auxY < height) {
                waterList.add(terraBot.getWaterFromInventory(auxX, auxY));
                plantList.add(terraBot.getPlantFromInventory(auxX, auxY));
            } else {
                // If out of bound add null to preserve correct movement order
                waterList.add(null);
                plantList.add(null);
            }
        }

        // Checking to see if we have a neighbour with a scanned plant and a scanned water
        for (int i = 0; i < FOUR; i++) {
            int auxX = currentX + directions[i][0];
            int auxY = currentY + directions[i][1];
            if (waterList.get(i) != null && plantList.get(i) != null
                    && simulationMap.getBox(auxX, auxY).getAnimal() == null) {
                if (waterList.get(i).getWaterQuality() > maxWaterQuality) {
                    newX = currentX + directions[i][0];
                    newY = currentY + directions[i][1];
                    maxWaterQuality = waterList.get(i).getWaterQuality();
                }
            }
        }
        if (newX != -1 && newY != -1) {
            executeMove(simulationMap, newX, newY);
            return;
        }

        // Checking to see if we have a neighbour with a scanned plant
        for (int i = 0; i < FOUR; i++) {
            int auxX = currentX + directions[i][0];
            int auxY = currentY + directions[i][1];
            if (plantList.get(i) != null && simulationMap.getBox(auxX, auxY).getAnimal() == null) {
                newX =  currentX + directions[i][0];
                newY =  currentY + directions[i][1];
                executeMove(simulationMap, newX, newY);
                return;
            }
        }

        // Checking to see if we have a neighbour with a scanned water
        for (int i = 0; i < FOUR; i++) {
            int auxX = currentX + directions[i][0];
            int auxY = currentY + directions[i][1];
            if (waterList.get(i) != null && waterList.get(i).getWaterQuality()  > maxWaterQuality
                    && simulationMap.getBox(auxX, auxY).getAnimal() == null) {
                newX =  currentX + directions[i][0];
                newY =  currentY + directions[i][1];
                maxWaterQuality = waterList.get(i).getWaterQuality();
            }
        }
        if (newX != -1 && newY != -1) {
            executeMove(simulationMap, newX, newY);
            return;
        }

        // Going to the first in bounds neighbour
        for (int i = 0; i < FOUR; i++) {
            int auxX = currentX + directions[i][0];
            int auxY = currentY + directions[i][1];
            if (auxX >= 0 && auxX < width && auxY >= 0
                    && auxY < height && simulationMap.getBox(auxX, auxY).getAnimal() == null) {
                executeMove(simulationMap, auxX, auxY);
                return;
            }
        }
    }

    private boolean isDead = false;
    /**
     * todo
     * comentriu
     */
    public void setDead() {
        this.isDead = true;
    }
    /**
     * todo
     * comentriu
     */
    protected void eatPlantOrWater(final Box box) {
        boolean canEatPlant = false;
        boolean canEatWater = false;
        if (box.getPlant() != null && box.getPlant().isScanned()) {
            this.mass += box.getPlant().getMass();
            box.setPlant(null);
            canEatPlant = true;
        }

        if (box.getWater() != null && box.getWater().isScanned()) {
            double intakeRate = POINT_ZERO_EIGHT;
            double waterMass = box.getWater().getMass();
            double animalMass = this.getMass();
            double waterToDrink = Math.min(animalMass * intakeRate, waterMass);
            this.setMass(animalMass + waterToDrink);
            box.getWater().setMass(waterMass - waterToDrink);
            if (box.getWater().getMass() <= 0) {
                box.setWater(null);
            }
            canEatWater = true;
        }

        if (canEatWater && canEatPlant) {
            box.getSoil().addOrganicMatter(POINT_EIGHT);
        } else if (canEatWater || canEatPlant) {
            box.getSoil().addOrganicMatter(POINT_FIVE);
        }
    }
    /**
     * todo
     * comentriu
     */
    public abstract void eat(Box box);
    /**
     * todo
     * comentriu
     */
    @Override
    public String improveEnvironment(final Box box, final String improvementType) {
        box.getSoil().addOrganicMatter(POINT_THREE);
        return "The soil was successfully fertilized using " + this.name;
    }
}
