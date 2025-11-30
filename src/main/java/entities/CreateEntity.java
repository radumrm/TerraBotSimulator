package entities;

import entities.animals.Animal;
import entities.animals.Herbivores;
import entities.animals.Detritivores;
import entities.animals.Carnivores;
import entities.animals.Parasites;
import entities.animals.Omnivores;

import entities.environment.Water;

import entities.environment.air.Air;
import entities.environment.air.DesertAir;
import entities.environment.air.TropicalAir;
import entities.environment.air.MountainAir;
import entities.environment.air.PolarAir;
import entities.environment.air.TemperateAir;

import entities.environment.soil.Soil;
import entities.environment.soil.DesertSoil;
import entities.environment.soil.ForestSoil;
import entities.environment.soil.GrasslandSoil;
import entities.environment.soil.SwampSoil;
import entities.environment.soil.TundraSoil;

import entities.plants.Plant;
import entities.plants.Algae;
import entities.plants.Ferns;
import entities.plants.FloweringPlants;
import entities.plants.GymnospermsPlants;
import entities.plants.Mosses;

import fileio.AirInput;
import fileio.SoilInput;
import fileio.WaterInput;
import fileio.PlantInput;
import fileio.AnimalInput;

public final class CreateEntity {

    private CreateEntity() { }
    /**
     * Generam entitate tip air
     */
    public static Air createAir(final AirInput input) {
        return switch (input.getType()) {
            case "TropicalAir" -> new TropicalAir(input);
            case "DesertAir" -> new DesertAir(input);
            case "PolarAir" -> new PolarAir(input);
            case "TemperateAir" -> new TemperateAir(input);
            case "MountainAir" -> new MountainAir(input);
            default -> null;
        };
    }
    /**
     * Generam entitate tip soil
     */
    public static Soil createSoil(final SoilInput input) {
        return switch (input.getType()) {
            case "ForestSoil" -> new ForestSoil(input);
            case "SwampSoil" -> new SwampSoil(input);
            case "DesertSoil" -> new DesertSoil(input);
            case "TundraSoil" -> new TundraSoil(input);
            case "GrasslandSoil" -> new GrasslandSoil(input);
            default -> null;
        };
    }
    /**
     * Generam entitate tip water
     */
    public static Water createWater(final WaterInput input) {
        return new Water(input);
    }
    /**
     * Generam entitate tip plant
     */
    public static Plant createPlant(final PlantInput input) {
        return switch (input.getType()) {
            case "FloweringPlants" -> new FloweringPlants(input);
            case "GymnospermsPlants" -> new GymnospermsPlants(input);
            case "Ferns" -> new Ferns(input);
            case "Mosses" -> new Mosses(input);
            case "Algae" -> new Algae(input);
            default -> null;
        };
    }
    /**
     * Generam entitate tip animal
     */
    public static Animal createAnimal(final AnimalInput input) {
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
