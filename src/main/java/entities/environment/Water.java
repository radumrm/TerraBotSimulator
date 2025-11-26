package entities.environment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.WaterInput;
import entities.Entity;
import lombok.Getter;

@Getter
public class Water extends Entity {
    private double purity;
    private double salinity;
    private double turbidity;
    private double contaminantIndex;
    private double pH;
    private boolean isFrozen;

    public Water(WaterInput water) {
        super(water.getName(), water.getMass(), water.getType());
        this.purity = water.getPurity();
        this.salinity = water.getSalinity();
        this.turbidity = water.getTurbidity();
        this.contaminantIndex = water.getContaminantIndex();
        this.pH = water.getPH();
        this.isFrozen = water.isFrozen();
    }
}
