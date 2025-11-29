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

    public Soil(SoilInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.nitrogen = input.getNitrogen();
        this.waterRetention = input.getWaterRetention();
        this.soilpH = input.getSoilpH();
        this.organicMatter = input.getOrganicMatter();
    }

    public abstract double getSoilQuality();

    public abstract double PossibilityToGetStuckInSoil();

    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);

    public void addOrganicMatter(double ammount) {
        this.organicMatter += ammount;
        this.organicMatter = Math.round(this.organicMatter * D_100) / D_100;
    }
}
