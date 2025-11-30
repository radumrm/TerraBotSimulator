package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;
import lombok.Getter;

import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.D_100;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.TWO;

public class DesertSoil extends Soil {
    @Getter private double salinity;
    public DesertSoil(final SoilInput input) {
        super(input);
        this.salinity = input.getSalinity();
    }
    /**
     * Intoarce soilQuality in functie de tipul solului
     */
    @Override
    public double getSoilQuality() {
        double soilQuality = (nitrogen * POINT_FIVE) + (waterRetention * POINT_THREE)
                            - (salinity * TWO);
        return normalizedAndRounded(soilQuality);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de soil
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("salinity", this.salinity);
    }
    /**
     * Calculeaza posibilitatea de a ramane blocat in soil in functie de tipul solului
     */
    @Override
    public double possibilityToGetStuckInSoil() {
        return (ONE_HUNDRED - waterRetention + salinity) / D_100 * ONE_HUNDRED;
    }
}
