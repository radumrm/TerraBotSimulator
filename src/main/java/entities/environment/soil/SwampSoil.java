package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.*;

public class SwampSoil extends Soil {
    @Getter private double waterLogging;
    public SwampSoil(SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * ONE_POINT_ONE) + (organicMatter * TWO_POINT_TWO)
                            - (waterLogging * 5);
        return normalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("waterLogging", this.waterLogging);
    }

    @Override
    public double PossibilityToGetStuckInSoil() {
        return waterLogging * D_10;
    }
}