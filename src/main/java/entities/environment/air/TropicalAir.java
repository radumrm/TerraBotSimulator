package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

public class TropicalAir extends Air {
    @Getter private double co2Level;
    public TropicalAir(AirInput air) {
        super(air);
        this.co2Level = air.getCo2Level();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (humidity * 0.5) - (co2Level * 0.01);
        return NormalizedAndRounded(airQuality);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("co2Level", this.co2Level);
    }
}
