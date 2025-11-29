package robot;

import entities.Entity;
import entities.environment.Water;
import entities.plants.Plant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
public class TerraBot {
    private int x;
    private int y;
    private int energy;
    private int finishedChargeTimestamp = 0;
    private List<Entity> inventory;
    private Map<String, List<String>> factsDataBase;
    private List<Entity> scannedEntities;

    public TerraBot(final int energy) {
        this.x = 0;
        this.y = 0;
        this.energy = energy;
        this.inventory = new ArrayList<>();
        this.factsDataBase = new HashMap<>();
        this.scannedEntities = new ArrayList<>();
    }
    /**
     * todo
     * comentriu
     */
    public boolean isCharging(final int timestamp) {
        return timestamp < finishedChargeTimestamp;
    }
    /**
     * todo
     * comentriu
     */
    public void recharge(final int charge, final int timestamp) {
        energy += charge;
        finishedChargeTimestamp = timestamp + charge;
    }
    /**
     * todo
     * comentriu
     */
    public void addToInventory(final Entity entity) {
        if (!inventory.contains(entity)) {
            inventory.add(entity);
        }
        if (!scannedEntities.contains(entity)) {
            scannedEntities.add(entity);
        }
    }
    /**
     * todo
     * comentriu
     */
    public Water getWaterFromInventory(final int coordX, final int coordY) {
        for (Entity entity : inventory) {
            if (entity.getX() == coordX && entity.getY() == coordY && entity.isWater()) {
                return (Water) entity;
            }
        }
        return null;
    }
    /**
     * todo
     * comentriu
     */
    public Plant getPlantFromInventory(final int coordX, final int coordY) {
        for (Entity entity : inventory) {
            if (entity.getX() == coordX && entity.getY() == coordY && entity.isPlant()) {
                return (Plant) entity;
            }
        }
        return null;
    }
    /**
     * todo
     * comentriu
     */
    public boolean hasEntityScanned(final String entityName) {
        for (Entity entity : inventory) {
            if (entity.getName().equals(entityName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * todo
     * comentriu
     */
    public void addFact(final String entityName, final String fact) {
        if (!factsDataBase.containsKey(entityName)) {
            factsDataBase.put(entityName, new ArrayList<>());
        }
        List<String> facts = factsDataBase.get(entityName);
        if (!facts.contains(fact)) {
            facts.add(fact);
        }
    }
    /**
     * todo
     * comentriu
     */
    public boolean hasFactsAbout(final String entityName) {
        List<String> facts = factsDataBase.get(entityName);
        return (facts != null)  && (!facts.isEmpty());
    }
    /**
     * todo
     * comentriu
     */
    public Entity getEntityFromInventory(final String entityName) {
        for (Entity entity : inventory) {
            if (entity.getName().equals(entityName)) {
                return entity;
            }
        }
        return null;
    }
    /**
     * todo
     * comentriu
     */
    public void removeFromInventory(final String name) {
        Entity entity = getEntityFromInventory(name);
        if (entity != null) {
            inventory.remove(entity);
        }
    }
}
