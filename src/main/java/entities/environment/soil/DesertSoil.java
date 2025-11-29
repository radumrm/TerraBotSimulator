package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.*;

public class DesertSoil extends Soil {
    @Getter private double salinity;
    public DesertSoil(SoilInput input) {
        super(input);
        this.salinity = input.getSalinity();
    }

    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * POINT_FIVE) + (waterRetention * POINT_THREE)
                            - (salinity * 2);
        return NormalizedAndRounded(soilQuality);
    }

    @Override
    public void addSpecificFieldsToNode(ObjectNode objectNode) {
        objectNode.put("salinity", this.salinity);
    }

    @Override
    public double PossibilityToGetStuckInSoil() {
        return (ONE_HUNDRED - waterRetention + salinity) / D_100 * ONE_HUNDRED;
    }
}