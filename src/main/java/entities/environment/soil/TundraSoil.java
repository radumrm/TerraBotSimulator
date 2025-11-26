package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

public class TundraSoil extends Soil {
    @Getter private double permafrostDepth;
    public TundraSoil(SoilInput input) {
        super(input);
        this.permafrostDepth = input.getPermafrostDepth();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 0.7) + (organicMatter * 0.5)
                            - (permafrostDepth * 1.5);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("permafrostDepth", this.permafrostDepth);
    }


    @Override
    public double PossibilityToGetStuckInSoil() {
        return (50 - permafrostDepth) / 50 * 100;
    }
}