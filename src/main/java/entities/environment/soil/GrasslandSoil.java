package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.*;

public class GrasslandSoil extends Soil {
    @Getter private double rootDensity;
    public GrasslandSoil(SoilInput input) {
        super(input);
        this.rootDensity = input.getRootDensity();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * ONE_POINT_THREE) + (organicMatter * ONE_POINT_FIVE)
                            + (rootDensity * POINT_EIGHT);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("rootDensity", this.rootDensity);
    }

    @Override
    public double PossibilityToGetStuckInSoil() {
        return ((50 - rootDensity) + waterRetention * POINT_FIVE) / 75 * ONE_HUNDRED;
    }
}