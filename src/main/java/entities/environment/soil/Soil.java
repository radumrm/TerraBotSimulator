package entities.environment.soil;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import fileio.SoilInput;
import lombok.Getter;
import lombok.Setter;

import static utils.MagicNumber.D_100;

@Getter
@Setter
public abstract class Soil extends Entity {
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;

    public Soil(final SoilInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.nitrogen = input.getNitrogen();
        this.waterRetention = input.getWaterRetention();
        this.soilpH = input.getSoilpH();
        this.organicMatter = input.getOrganicMatter();
    }
    /**
     * Intoarce soilQuality in functie de tipul solului
     */
    public abstract double getSoilQuality();
    /**
     * Calculeaza posibilitatea de a ramane blocat in soil in functie de tipul solului
     */
    public abstract double possibilityToGetStuckInSoil();
    /**
     * Functie pentru afisarea variabilei specifice tipului de soil
     */
    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);
    /**
     * Adaugam organic matter
     */
    public void addOrganicMatter(final double ammount) {
        this.organicMatter += ammount;
        this.organicMatter = Math.round(this.organicMatter * D_100) / D_100;
    }
}
