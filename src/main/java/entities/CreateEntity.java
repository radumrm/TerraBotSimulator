package entities;

import entities.animals.*;
import entities.environment.Water;
import entities.environment.air.*;
import entities.environment.soil.*;
import entities.plants.*;
import fileio.*;

public class CreateEntity {

    public static Air createAir(AirInput input) {
        return switch (input.getType()) {
            case "TropicalAir" -> new TropicalAir(input);
            case "DesertAir" -> new DesertAir(input);
            case "PolarAir" -> new PolarAir(input);
            case "TemperateAir" -> new TemperateAir(input);
            case "MountainAir" -> new MountainAir(input);
            default -> null;
        };
    }

    public static Soil createSoil(SoilInput input) {
        return switch (input.getType()) {
            case "ForestSoil" -> new ForestSoil(input);
            case "SwampSoil" -> new SwampSoil(input);
            case "DesertSoil" -> new DesertSoil(input);
            case "TundraSoil" -> new TundraSoil(input);
            case "GrasslandSoil" -> new GrasslandSoil(input);
            default -> null;
        };
    }

    public static Water createWater(WaterInput input) {
        return new Water(input);
    }

    public static Plant createPlant(PlantInput input) {
        return switch (input.getType()) {
            case "FloweringPlants" -> new FloweringPlants(input);
            case "GymnospermsPlants" -> new GymnospermsPlants(input);
            case "Ferns" -> new Ferns(input);
            case "Mosses" -> new Mosses(input);
            case "Algae" -> new Algae(input);
            default -> null;
        };
    }

    public static Animal createAnimal(AnimalInput input) {
        return switch (input.getType()) {
            case "Herbivores" -> new Herbivores(input);
            case "Carnivores" -> new Carnivores(input);
            case "Omnivores" -> new Omnivores(input);
            case "Detritivores" -> new Detritivores(input);
            case "Parasites" -> new Parasites(input);
            default -> null;
        };
    }
}