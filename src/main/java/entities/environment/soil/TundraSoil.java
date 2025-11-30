package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.POINT_SEVEN;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.ONE_POINT_FIVE;
import static utils.MagicNumber.D_50;
import static utils.MagicNumber.ONE_HUNDRED;

public class TundraSoil extends Soil {
    @Getter private double permafrostDepth;
    public TundraSoil(final SoilInput input) {
        super(input);
        this.permafrostDepth = input.getPermafrostDepth();
    }
    /**
     * Intoarce soilQuality in functie de tipul solului
     */
    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * POINT_SEVEN) + (organicMatter * POINT_FIVE)
                            - (permafrostDepth * ONE_POINT_FIVE);
        return normalizedAndRounded(soilQuality);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de soil
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("permafrostDepth", this.permafrostDepth);
    }
    /**
     * Calculeaza posibilitatea de a ramane blocat in soil in functie de tipul solului
     */
    @Override
    public double possibilityToGetStuckInSoil() {
        return (D_50 - permafrostDepth) / D_50 * ONE_HUNDRED;
    }
}
