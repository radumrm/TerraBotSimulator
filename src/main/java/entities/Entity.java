package entities;

import lombok.Getter;

public abstract class Entity {
    // Common attributes for all entities
    @Getter protected String name;
    @Getter protected double mass;
    @Getter protected String type;
    public Entity(String name, double mass, String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    protected double NormalizedAndRounded(double score) {
        return Math.round(Math.max(0, Math.min(100, score)) * 100.0) / 100.0;
    }
}
