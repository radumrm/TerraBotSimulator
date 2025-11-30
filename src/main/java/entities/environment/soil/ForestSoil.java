package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.ONE_POINT_TWO;
import static utils.MagicNumber.ONE_POINT_FIVE;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.TWO;
import static utils.MagicNumber.POINT_SIX;
import static utils.MagicNumber.POINT_FOUR;
import static utils.MagicNumber.D_80;

public class ForestSoil extends Soil {
    @Getter private double leafLitter;
    public ForestSoil(final SoilInput input) {
        super(input);
        this.leafLitter = input.getLeafLitter();
    }
    /**
     * Intoarce soilQuality in functie de tipul solului
     */
    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * ONE_POINT_TWO) + (organicMatter * TWO)
                            + (waterRetention * ONE_POINT_FIVE) + (leafLitter * POINT_THREE);
        return normalizedAndRounded(soilQuality);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de soil
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("leafLitter", this.leafLitter);
    }
    /**
     * Calculeaza posibilitatea de a ramane blocat in soil in functie de tipul solului
     */
    @Override
    public double possibilityToGetStuckInSoil() {
        return (waterRetention * POINT_SIX + leafLitter * POINT_FOUR) / D_80 * ONE_HUNDRED;
    }
}
