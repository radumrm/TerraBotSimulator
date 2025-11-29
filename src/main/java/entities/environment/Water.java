package entities.environment;

import fileio.WaterInput;
import entities.Entity;
import lombok.Getter;
import map.Box;

import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.SEVEN_POINT_FIVE;
import static utils.MagicNumber.D_350;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.POINT_TWO;
import static utils.MagicNumber.POINT_ONE_FIVE;
import static utils.MagicNumber.POINT_ONE;


@Getter
public class Water extends Entity {
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;

    public Water(final WaterInput water) {
        super(water.getName(), water.getMass(), water.getType());
        this.purity = water.getPurity();
        this.salinity = water.getSalinity();
        this.turbidity = water.getTurbidity();
        this.contaminantIndex = water.getContaminantIndex();
        this.pH = water.getPH();
        this.isFrozen = water.isFrozen();
        this.isWater = true;
    }
    /**
     * todo
     * comentriu
     */
    public double getWaterQuality() {
        double purityScore = purity / ONE_HUNDRED;
        double pHScore = 1 - Math.abs(pH - SEVEN_POINT_FIVE) / SEVEN_POINT_FIVE;
        double salinityScore = 1 - (salinity / D_350);
        double turbidityScore = 1 - (turbidity / ONE_HUNDRED);
        double contaminantScore = 1 - (contaminantIndex / ONE_HUNDRED);
        double frozenScore = isFrozen ? 0 : 1;
        double waterQuality = POINT_THREE * purityScore + POINT_TWO * pHScore
                + POINT_ONE_FIVE * salinityScore + POINT_ONE * turbidityScore
                + POINT_ONE_FIVE * contaminantScore + POINT_TWO * frozenScore;
        return waterQuality * ONE_HUNDRED;
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public String improveEnvironment(final Box box, final String improvementType) {
        if (improvementType.equals("increaseHumidity")) {
            box.getAir().setHumidity(box.getAir().getHumidity() + POINT_TWO);
            return "The humidity was successfully increased using " + this.name;
        } else {
            box.getSoil().setWaterRetention(box.getSoil().getWaterRetention() + POINT_TWO);
            return "The moisture was successfully increased using " + this.name;
        }
    }
}

