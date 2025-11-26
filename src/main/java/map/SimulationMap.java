package map;

import fileio.*;
import entities.Entity;
import entities.CreateEntity;
import entities.animals.*;
import lombok.Getter;

public class SimulationMap {
    private Box[][] map;
    @Getter private int height;
    @Getter private int width;

    public SimulationMap(String dimensions) {
        String[] dimensionsArray = dimensions.split("x");
        height = Integer.parseInt(dimensionsArray[0]);
        width = Integer.parseInt(dimensionsArray[1]);
        map = new Box[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
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
                    this.getBox(coord.getX(), coord.getY()).setSoil(CreateEntity.createSoil(input));
                }
            }
        }
        if (proprieties.getAir() != null) {
            for (AirInput input : proprieties.getAir()) {
                for (PairInput coord : input.getSections()) {
                    this.getBox(coord.getX(), coord.getY()).setAir(CreateEntity.createAir(input));
                }
            }
        }
        if (proprieties.getWater() != null) {
            for (WaterInput input : proprieties.getWater()) {
                for (PairInput coord : input.getSections()) {
                    this.getBox(coord.getX(), coord.getY()).setWater(CreateEntity.createWater(input));
                }
            }
        }
        if (proprieties.getPlants() != null) {
            for (PlantInput input : proprieties.getPlants()) {
                for (PairInput coord : input.getSections()) {
                    this.getBox(coord.getX(), coord.getY()).setPlant(CreateEntity.createPlant(input));
                }
            }
        }
        if (proprieties.getAnimals() != null) {
            for (AnimalInput input : proprieties.getAnimals()) {
                for (PairInput coord : input.getSections()) {
                    this.getBox(coord.getX(), coord.getY()).setAnimal(CreateEntity.createAnimal(input));
                }
            }
        }
    }
}
