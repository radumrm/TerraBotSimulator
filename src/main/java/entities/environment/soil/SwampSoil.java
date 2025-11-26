package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

public class SwampSoil extends Soil {
    @Getter private double waterLogging;
    public SwampSoil(SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 1.1) + (organicMatter * 2.2)
                            - (waterLogging * 5);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("waterLogging", this.waterLogging);
    }
}