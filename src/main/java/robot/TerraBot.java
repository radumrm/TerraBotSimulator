package robot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerraBot {
    private int x;
    private int y;
    private int energy;
    private int finishedChargeTimestamp = 0;

    public TerraBot(int energy) {
        this.x = 0;
        this.y = 0;
        this.energy = energy;
    }

    public boolean isCharging(int timestamp) {
        return timestamp < finishedChargeTimestamp;
    }

    public void recharge(int charge, int timestamp) {
        energy += charge;
        finishedChargeTimestamp = timestamp + charge;
    }

}