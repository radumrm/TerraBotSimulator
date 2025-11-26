package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

public class GrasslandSoil extends Soil {
    @Getter private double rootDensity;
    public GrasslandSoil(SoilInput input) {
        super(input);
        this.rootDensity = input.getRootDensity();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 1.3) + (organicMatter * 1.5)
                            + (rootDensity * 0.8);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("rootDensity", this.rootDensity);
    }
}