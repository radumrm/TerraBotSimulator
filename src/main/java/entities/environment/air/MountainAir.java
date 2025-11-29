package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

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
        double airQuality = ((oxygenLevel - (altitude/ONE_THOUSAND*POINT_FIVE)) * 2) + (humidity * POINT_SIX);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - POINT_ONE * numberOfHikers;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("altitude", this.altitude);
    }
    protected static double maxScore = SEVENTY_EIGHT;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setNumberOfHikers(int numberOfHikers) {
        this.numberOfHikers = numberOfHikers;
        changeWeather();
    }
}
