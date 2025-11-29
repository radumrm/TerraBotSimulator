package entities;

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

    public Entity(final String name, final double mass, final String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    /**
     * todo
     * comentriu
     */
    protected double normalizedAndRounded(final double score) {
        return Math.round(Math.max(0, Math.min(ONE_HUNDRED, score)) * D_100) / D_100;
    }
    /**
     * todo
     * comentriu
     */
    public boolean isScanned() {
        return scannedTimestamp != -1;
    }
    /**
     * todo
     * comentriu
     */
    public String improveEnvironment(final map.Box box, final String improvementType) {
        return "";
    }

}
