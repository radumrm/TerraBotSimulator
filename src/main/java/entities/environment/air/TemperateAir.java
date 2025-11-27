package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

@Getter
public class TemperateAir extends Air {
    private double pollenLevel;
    private String season = "";
    public TemperateAir(AirInput air) {
        super(air);
        this.pollenLevel = air.getPollenLevel();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (humidity * 0.7) - (pollenLevel * 0.1);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            double seasonPenalty = season.equalsIgnoreCase("Spring") ? 15 : 0;
            return normAirQuality - seasonPenalty;
        }
        return normAirQuality;
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
    public void setSeason(String season) {
        this.season = season;
        changeWeather();
    }
}

