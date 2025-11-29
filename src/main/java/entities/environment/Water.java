package entities.environment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.WaterInput;
import entities.Entity;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.abs;
import static utils.MagicNumber.*;

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
        double purity_score = purity / ONE_HUNDRED;
        double pH_score = 1 - Math.abs(pH - SEVEN_POINT_FIVE) / SEVEN_POINT_FIVE;
        double salinity_score = 1 - (salinity / 350);
        double turbidity_score = 1 - (turbidity / ONE_HUNDRED);
        double contaminant_score = 1 - (contaminantIndex / ONE_HUNDRED);
        double frozen_score = isFrozen ? 0 : 1;
        double water_quality = POINT_THREE * purity_score + POINT_TWO * pH_score + POINT_ONE_FIVE * salinity_score + POINT_ONE * turbidity_score + POINT_ONE_FIVE * contaminant_score + POINT_TWO * frozen_score;
        return water_quality * ONE_HUNDRED;
    }

    @Override
    public String improveEnvironment(map.Box box, String improvementType) {
        if (improvementType.equals("increaseHumidity")) {
            box.getAir().setHumidity(box.getAir().getHumidity() + POINT_TWO);
            return "The humidity was successfully increased using " + this.name;
        } else {
            box.getSoil().setWaterRetention(box.getSoil().getWaterRetention() + POINT_TWO);
            return "The moisture was successfully increased using " + this.name;
        }
    }
}
