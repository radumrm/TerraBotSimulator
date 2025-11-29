package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

@Getter
public class PolarAir extends Air {
    private double iceCrystalConcentration;
    private double windSpeed = 0;
    public PolarAir(AirInput air) {
        super(air);
        this.iceCrystalConcentration = air.getIceCrystalConcentration();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (100 - Math.abs(temperature)) - (iceCrystalConcentration * 0.05);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - windSpeed * 0.2;
        }
        return Math.min(normAirQuality, 100);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("iceCrystalConcentration", this.iceCrystalConcentration);
    }
    protected static double maxScore = 142;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        changeWeather();
    }
}
