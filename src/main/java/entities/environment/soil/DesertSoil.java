package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

public class DesertSoil extends Soil {
    @Getter private double salinity;
    public DesertSoil(SoilInput input) {
        super(input);
        this.salinity = input.getSalinity();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 0.5) + (waterRetention * 0.3)
                            - (salinity * 2);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("salinity", this.salinity);
    }
}