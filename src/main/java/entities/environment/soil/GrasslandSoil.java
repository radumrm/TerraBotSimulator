package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.ONE_POINT_THREE;
import static utils.MagicNumber.ONE_POINT_FIVE;
import static utils.MagicNumber.POINT_EIGHT;
import static utils.MagicNumber.D_50;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.D_75;
import static utils.MagicNumber.ONE_HUNDRED;

public class GrasslandSoil extends Soil {
    @Getter private double rootDensity;
    public GrasslandSoil(final SoilInput input) {
        super(input);
        this.rootDensity = input.getRootDensity();
    }
    /**
     * Intoarce soilQuality in functie de tipul solului
     */
    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * ONE_POINT_THREE) + (organicMatter * ONE_POINT_FIVE)
                + (rootDensity * POINT_EIGHT);
        return normalizedAndRounded(soilQuality);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de soil
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("rootDensity", this.rootDensity);
    }
    /**
     * Calculeaza posibilitatea de a ramane blocat in soil in functie de tipul solului
     */
    @Override
    public double possibilityToGetStuckInSoil() {
        return ((D_50 - rootDensity) + waterRetention * POINT_FIVE) / D_75 * ONE_HUNDRED;
    }
}
