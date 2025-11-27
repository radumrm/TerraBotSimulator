package robot;

import entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TerraBot {
    private int x;
    private int y;
    private int energy;
    private int finishedChargeTimestamp = 0;
    private List<Entity> inventory;

    public TerraBot(int energy) {
        this.x = 0;
        this.y = 0;
        this.energy = energy;
        this.inventory = new ArrayList<>();
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
}