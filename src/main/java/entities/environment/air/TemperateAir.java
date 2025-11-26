package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

@Getter
public class TemperateAir extends Air {
    private double pollenLevel;
    public TemperateAir(AirInput air) {
        super(air);
        this.pollenLevel = air.getPollenLevel();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (humidity * 0.7) - (pollenLevel * 0.1);
        return NormalizedAndRounded(airQuality);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("pollenLevel", this.pollenLevel);
    }
    protected static double maxScore = 84;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
}

