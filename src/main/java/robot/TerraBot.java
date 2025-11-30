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
    // Stocam timpul la care s-a terminat incarcarea
    private int finishedChargeTimestamp = 0;
    // Inventar
    private List<Entity> inventory;
    // Pe langa inventar avem un Map care tine corespondenta dintre numele unei enititati
    // scanate si lista de fact-uri pe care o avem despre aceasta
    private Map<String, List<String>> factsDataBase;
    // Lista cu entitatiile scanate, ne trebuie deoarece din inventar o sa mai scoatem entitati
    // pentru care o sa trebuiasca sa dam updateEnv, printKnowledge ...
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
     * Verificam daca se incarca
     */
    public boolean isCharging(final int timestamp) {
        return timestamp < finishedChargeTimestamp;
    }
    /**
     * Functie pentru a incarca robotul
     */
    public void recharge(final int charge, final int timestamp) {
        energy += charge;
        finishedChargeTimestamp = timestamp + charge;
    }
    /**
     * Funtie pentru a adauga in inventar un obiect scanat
     * DAR si in scannedEntities
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
     * Returnam water daca avem in inventar apa scnata la coordonatele x si y
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
     * Returnam plant daca avem in inventar plant scnata la coordonatele x si y
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
     * Verificare daca o entitate a fost scanata
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
     * Adaugam un fact pentru o entitate scanata (dupa nume)
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
     * Returnam daca avem un fact pentru o entitate cu un nume specific
     */
    public boolean hasFactsAbout(final String entityName) {
        List<String> facts = factsDataBase.get(entityName);
        return (facts != null)  && (!facts.isEmpty());
    }
    /**
     * Returnam entitatea din inventar daca exista
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
     * Scoatem entitatea din inventar(pentru cand dam improve)
     */
    public void removeFromInventory(final String name) {
        Entity entity = getEntityFromInventory(name);
        if (entity != null) {
            inventory.remove(entity);
        }
    }
}
