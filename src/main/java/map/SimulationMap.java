package map;

import entities.environment.Water;
import entities.environment.air.Air;
import entities.environment.soil.Soil;
import entities.plants.Plant;
import fileio.*;
import entities.Entity;
import entities.CreateEntity;
import entities.animals.*;
import lombok.Getter;

@Getter
public class SimulationMap {
    private Box[][] map;
    private int height;
    private int width;

    public SimulationMap(String dimensions) {
        String[] dimensionsArray = dimensions.split("x");
        height = Integer.parseInt(dimensionsArray[0]);
        width = Integer.parseInt(dimensionsArray[1]);
        map = new Box[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new Box();
            }
        }
    }

    public Box getBox(int x, int y) {
        return map[x][y];
    }

    public void PopulateMap(TerritorySectionParamsInput proprieties) {
        if (proprieties.getSoil() != null) {
            for (SoilInput input : proprieties.getSoil()) {
                for (PairInput coord : input.getSections()) {
                    Soil soil =  CreateEntity.createSoil(input);
                    soil.setX(coord.getX());
                    soil.setY(coord.getY());
                    this.getBox(coord.getX(), coord.getY()).setSoil(soil);
                }
            }
        }
        if (proprieties.getAir() != null) {
            for (AirInput input : proprieties.getAir()) {
                for (PairInput coord : input.getSections()) {
                    Air air =  CreateEntity.createAir(input);
                    air.setX(coord.getX());
                    air.setY(coord.getY());
                    this.getBox(coord.getX(), coord.getY()).setAir(air);
                }
            }
        }
        if (proprieties.getWater() != null) {
            for (WaterInput input : proprieties.getWater()) {
                for (PairInput coord : input.getSections()) {
                    Water water =  CreateEntity.createWater(input);
                    water.setX(coord.getX());
                    water.setY(coord.getY());
                    this.getBox(coord.getX(), coord.getY()).setWater(water);
                }
            }
        }
        if (proprieties.getPlants() != null) {
            for (PlantInput input : proprieties.getPlants()) {
                for (PairInput coord : input.getSections()) {
                    Plant plant = CreateEntity.createPlant(input);
                    plant.setX(coord.getX());
                    plant.setY(coord.getY());
                    this.getBox(coord.getX(), coord.getY()).setPlant(plant);
                }
            }
        }
        if (proprieties.getAnimals() != null) {
            for (AnimalInput input : proprieties.getAnimals()) {
                for (PairInput coord : input.getSections()) {
                    Animal animal = CreateEntity.createAnimal(input);
                    animal.setX(coord.getX());
                    animal.setY(coord.getY());
                    this.getBox(coord.getX(), coord.getY()).setAnimal(animal);
                }
            }
        }
    }
}
