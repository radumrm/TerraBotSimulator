package entities.animals;

import entities.Entity;
import entities.environment.Water;
import entities.plants.Plant;
import fileio.AnimalInput;
import lombok.Getter;
import map.SimulationMap;
import robot.TerraBot;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Animal extends Entity {
    public enum State {
        HUNGRY,
        WELL_FED,
        SICK
    }

    protected State state;

    public Animal(AnimalInput animalInput) {
        super(animalInput.getName(), animalInput.getMass(), animalInput.getType());
    }

    public abstract double getAnimal_possibility_to_attack();

    public double PossibilityToBeAttackedByAnimal() {
        return (100 - this.getAnimal_possibility_to_attack()) / 10.0;
    }

    private void executeMove(SimulationMap simulationMap, int newX, int newY) {
        simulationMap.getBox(this.x, this.y).setAnimal(null);

        this.x = newX;
        this.y = newY;

         // simulationMap.getBox(this.x, this.y).setAnimal(this);
    }

    int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public void move(SimulationMap simulationMap, TerraBot terraBot) {
        int currentX = this.getX();
        int currentY = this.getY();
        int width = simulationMap.getWidth();
        int height = simulationMap.getHeight();

        int newX = -1;
        int newY = -1;
        double maxWaterQuality = -1;

        // Generating water and plant lists for every neighbour in the right order
        List <Water> waterList = new ArrayList<Water>();
        List <Plant> plantList = new ArrayList<Plant>();

        // Populating the lists
        for (int[] direction : directions ) {
            int auxX = currentX + direction[0] ;
            int auxY = currentY + direction[1] ;
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
        for (int i = 0; i < 4; i++) {
            if (waterList.get(i) != null && plantList.get(i) != null) {
                if (waterList.get(i).getWaterQuality() > maxWaterQuality) {
                    newX =  currentX + directions[i][0];
                    newY =  currentY + directions[i][1];
                    maxWaterQuality = waterList.get(i).getWaterQuality();
                }
            }
        }
        if (newX != -1 && newY != -1) {
            executeMove(simulationMap, newX, newY);
            return;
        }

        // Checking to see if we have a neighbour with a scanned plant
        for (int i = 0; i < 4; i++) {
            if (plantList.get(i) != null) {
                newX =  currentX + directions[i][0];
                newY =  currentY + directions[i][1];
                executeMove(simulationMap, newX, newY);
                return;
            }
        }

        // Checking to see if we have a neighbour with a scanned water
        for (int i = 0; i < 4; i++) {
            if (waterList.get(i) != null && waterList.get(i).getWaterQuality()  > maxWaterQuality) {
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
        for (int[] direction : directions) {
            int auxX = currentX + direction[0] ;
            int auxY = currentY + direction[1] ;
            if (auxX >= 0 && auxX < width && auxY >= 0 && auxY < height) {
                executeMove(simulationMap, auxX, auxY);
                return;
            }
        }
    }

}
