package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

@Getter
public class MountainAir extends Air {
    private double altitude;
    private int numberOfHikers = 0;
    public MountainAir(AirInput air) {
        super(air);
        this.altitude = air.getAltitude();
        this.isMountainAir = true;
    }
    @Override
    public double getAirQuality() {
        double airQuality = ((oxygenLevel - (altitude/1000*0.5)) * 2) + (humidity * 0.6);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - 0.1 * numberOfHikers;
        }
        return Math.min(normAirQuality, 100);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("altitude", this.altitude);
    }
    protected static double maxScore = 78;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setNumberOfHikers(int numberOfHikers) {
        this.numberOfHikers = numberOfHikers;
        changeWeather();
    }
}
