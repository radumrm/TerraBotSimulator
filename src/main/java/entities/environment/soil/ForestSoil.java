package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.*;

public class ForestSoil extends Soil {
    @Getter private double leafLitter;
    public ForestSoil(SoilInput input) {
        super(input);
        this.leafLitter = input.getLeafLitter();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * 1.2) + (organicMatter * 2)
                            + (waterRetention * ONE_POINT_FIVE) + (leafLitter * POINT_THREE);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("leafLitter", this.leafLitter);
    }

    @Override
    public double PossibilityToGetStuckInSoil() {
        return (waterRetention * POINT_SIX + leafLitter * POINT_FOUR) / 80 * ONE_HUNDRED;
    }
}