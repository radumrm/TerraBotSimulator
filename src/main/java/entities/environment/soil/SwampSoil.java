package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.ONE_POINT_ONE;
import static utils.MagicNumber.TWO_POINT_TWO;
import static utils.MagicNumber.D_10;
import static utils.MagicNumber.FIVE;


public class SwampSoil extends Soil {
    @Getter private double waterLogging;
    public SwampSoil(final SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * ONE_POINT_ONE) + (organicMatter * TWO_POINT_TWO)
                            - (waterLogging * FIVE);
        return normalizedAndRounded(soilQuality);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("waterLogging", this.waterLogging);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double possibilityToGetStuckInSoil() {
        return waterLogging * D_10;
    }
}
