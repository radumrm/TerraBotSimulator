package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.ONE_HUNDRED;

@Getter
@Setter
public abstract class Entity {
    // Coordonates for efficient environment update
    protected int x;
    protected int y;
    // Common attributes for all entities
    protected String name;
    protected double mass;
    protected String type;

    protected boolean isWater = false;
    protected boolean isPlant = false;
    protected boolean isAnimal = false;


    protected int scannedTimestamp = -1;

    public Entity(String name, double mass, String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    protected double NormalizedAndRounded(double score) {
        return Math.round(Math.max(0, Math.min(ONE_HUNDRED, score)) * D_100) / D_100;
    }

    public boolean isScanned() {
        return scannedTimestamp != -1;
    }

    public String improveEnvironment(map.Box box, String improvementType) {
        return "";
    }

}
