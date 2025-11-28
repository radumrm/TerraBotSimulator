package entities.environment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.WaterInput;
import entities.Entity;
import lombok.Getter;

import static java.lang.Math.abs;

@Getter
public class Water extends Entity {
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;

    public Water(WaterInput water) {
        super(water.getName(), water.getMass(), water.getType());
        this.purity = water.getPurity();
        this.salinity = water.getSalinity();
        this.turbidity = water.getTurbidity();
        this.contaminantIndex = water.getContaminantIndex();
        this.pH = water.getPH();
        this.isFrozen = water.isFrozen();
        this.isWater = true;
    }

    public double getWaterQuality() {
        double purity_score = purity / 100;
        double pH_score = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinity_score = 1 - (salinity / 350);
        double turbidity_score = 1 - (turbidity / 100);
        double contaminant_score = 1 - (contaminantIndex / 100);
        double frozen_score = isFrozen ? 0 : 1;
        double water_quality = 0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score + 0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * frozen_score;
        return water_quality * 100;
    }
}
