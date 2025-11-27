package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

@Getter
public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(AirInput air) {
        super(air);
        this.dustParticles = air.getDustParticles();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) - (dustParticles * 0.2) - (temperature * 0.3);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - 30;
        }
        return normAirQuality;
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("desertStorm", isWeatherChanged());
    }
    protected static double maxScore = 65;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setDesertStorm() {
        changeWeather();
    }
}
