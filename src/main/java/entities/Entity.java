package entities;

import lombok.Getter;
import lombok.Setter;

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

    protected int scannedTimestamp = -1;

    public Entity(String name, double mass, String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    protected double NormalizedAndRounded(double score) {
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }

    public boolean isScanned() {
        return scannedTimestamp != -1;
    }
}
