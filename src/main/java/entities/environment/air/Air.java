package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import fileio.AirInput;
import lombok.Getter;

@Getter
public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    public Air(AirInput air) {
        super(air.getName(), air.getMass(), air.getType());
        this.humidity = air.getHumidity();
        this.temperature = air.getTemperature();
        this.oxygenLevel = air.getOxygenLevel();
    }

    public abstract double getAirQuality();

    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);
}
