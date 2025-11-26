package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

public class ForestSoil extends Soil {
    @Getter private double leafLitter;
    public ForestSoil(SoilInput input) {
        super(input);
        this.leafLitter = input.getLeafLitter();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 1.2) + (organicMatter * 2)
                            + (waterRetention * 1.5) + (leafLitter * 0.3);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("leafLitter", this.leafLitter);
    }

    @Override
    public double PossibilityToGetStuckInSoil() {
        return (waterRetention * 0.6 + leafLitter * 0.4) / 80 * 100;
    }
}