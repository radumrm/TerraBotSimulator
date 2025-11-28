package robot;

import entities.Entity;
import entities.environment.Water;
import entities.plants.Plant;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class TerraBot {
    private int x;
    private int y;
    private int energy;
    private int finishedChargeTimestamp = 0;
    private List<Entity> inventory;
    private Map<String, List<String>> factsDataBase;

    public TerraBot(int energy) {
        this.x = 0;
        this.y = 0;
        this.energy = energy;
        this.inventory = new ArrayList<>();
        this.factsDataBase = new HashMap<>();
    }

    public boolean isCharging(int timestamp) {
        return timestamp < finishedChargeTimestamp;
    }

    public void recharge(int charge, int timestamp) {
        energy += charge;
        finishedChargeTimestamp = timestamp + charge;
    }

    public void addToInventory(Entity entity) {
        if (!inventory.contains(entity)) {
            inventory.add(entity);
        }
    }

    public Water getWaterFromInventory(int x, int y) {
        for (Entity entity : inventory) {
            if (entity.getX() == x && entity.getY() == y && entity instanceof Water) {
                return (Water) entity;
            }
        }
        return null;
    }

    public Plant getPlantFromInventory(int x, int y) {
        for (Entity entity : inventory) {
            if (entity.getX() == x && entity.getY() == y && entity instanceof Plant) {
                return (Plant) entity;
            }
        }
        return null;
    }

    public boolean hasEntityScanned(String entityName) {
        for (Entity entity : inventory) {
            if (entity.getName().equals(entityName)) {
                return true;
            }
        }
        return false;
    }

    public void addFact(String entityName, String fact) {
        if (!factsDataBase.containsKey(entityName)) {
            factsDataBase.put(entityName, new ArrayList<>());
        }
        List<String> facts = factsDataBase.get(entityName);
        if (!facts.contains(fact)) {
            facts.add(fact);
        }
    }

}