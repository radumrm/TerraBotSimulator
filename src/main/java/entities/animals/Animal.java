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
     * Intoarce probabilitatea de a ataca specifica tipului de animal
     */
    public abstract double getAnimalPossibilityToAttack();
    /**
     * Aplica formula pentru determinarea posibilitatii de atac a robotului
     */
    public double possibilityToBeAttackedByAnimal() {
        return (ONE_HUNDRED - this.getAnimalPossibilityToAttack()) / D_10;
    }
    /**
     * Sterge animalul de pe patratica veche si ii da alte coordonate, se va pune
     * iar pe o casuta din harta in commandProcessor
     */
    private void executeMove(final SimulationMap simulationMap, final int newX, final int newY) {
        simulationMap.getBox(this.x, this.y).setAnimal(null);

        this.x = newX;
        this.y = newY;

    }

    private int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    /**
     * Algoritm pentru determinarea si mutarea animalului pe noua pozitie ca in cerinta
     * plus verificarea ca un alt animal sa nu fie deja acolo
     */
    public void move(final SimulationMap simulationMap, final TerraBot terraBot) {
        // Index curent al pozitiei animalului
        int currentX = this.getX();
        int currentY = this.getY();
        int width = simulationMap.getWidth();
        int height = simulationMap.getHeight();
        // Index nou
        int newX = -1;
        int newY = -1;
        double maxWaterQuality = -1;

        // Vom genera 2 liste, fiecare cu 4 elemente, primul element corespune primului vecin
        // in oridea cautarii, ultimul element corespune ultimului vecin in ordinea cautarii
        // Daca un vecin este out of bounds, adaugam null in lista pentru a pastra ordinea
        List<Water> waterList = new ArrayList<Water>();
        List<Plant> plantList = new ArrayList<Plant>();

        // Popularea listelor
        for (int[] direction : directions) {
            int auxX = currentX + direction[0];
            int auxY = currentY + direction[1];
            if (auxX >= 0 && auxX < width && auxY >= 0 && auxY < height) {
                waterList.add(terraBot.getWaterFromInventory(auxX, auxY));
                plantList.add(terraBot.getPlantFromInventory(auxX, auxY));
            } else {
                // Adaugare vecin out of bounds pentru ordine
                waterList.add(null);
                plantList.add(null);
            }
        }

        // Primul tip de verificare apa + planta si mergem dupa Max(WaterQuality)
        // Si verificam sa nu fie un animal pe noua casuta
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
        // Verificam daca am gasit o noua pozitie potrivita si mutam animalul
        if (newX != -1 && newY != -1) {
            executeMove(simulationMap, newX, newY);
            return;
        }

        // Verificare planta (prima in ordinea parcurgerii)
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

        // Verificare apa, ne mutam dupa maxWaterQuality
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
        // Verificam vecin valid
        if (newX != -1 && newY != -1) {
            executeMove(simulationMap, newX, newY);
            return;
        }

        // Mergem la primul vecin valabil (in ordinea cautarii)
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
     * Setem animalul dead pentru a nu mai face update la env
     */
    public void setDead() {
        this.isDead = true;
    }
    /**
     * Algoritm de mancare doar pentru apa si planta
     */
    protected void eatPlantOrWater(final Box box) {
        // Variabile in care stocam daca s-a mancat planta sau baut apa scanata
        // Pentru a stii cat organicMatter ii dam soil-ului
        boolean canEatPlant = false;
        boolean canEatWater = false;
        // Verificare + mancare planta
        if (box.getPlant() != null && box.getPlant().isScanned()) {
            this.mass += box.getPlant().getMass();
            box.setPlant(null);
            canEatPlant = true;
        }
        // Verificare + baut apa
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
        // Verificare adaugare organicMatter
        if (canEatWater && canEatPlant) {
            box.getSoil().addOrganicMatter(POINT_EIGHT);
        } else if (canEatWater || canEatPlant) {
            box.getSoil().addOrganicMatter(POINT_FIVE);
        }
    }
    /**
     * Metoda mostenita de toate animalele pentru a manca
     */
    public abstract void eat(Box box);
    /**
     * Metoda pentru improveEnv
     */
    @Override
    public String improveEnvironment(final Box box, final String improvementType) {
        box.getSoil().addOrganicMatter(POINT_THREE);
        return "The soil was successfully fertilized using " + this.name;
    }
}
