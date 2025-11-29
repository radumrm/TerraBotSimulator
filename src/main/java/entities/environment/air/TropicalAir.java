package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public class TropicalAir extends Air {
    private double co2Level;
    private double rainfall = 0;
    public TropicalAir(AirInput air) {
        super(air);
        setCo2Level(air.getCo2Level());
        this.isTropicalAir = true;
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (humidity * POINT_FIVE) - (co2Level * POINT_ZERO_ONE);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality + rainfall * POINT_THREE;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("co2Level", this.co2Level);
    }
    protected static double maxScore = 82;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
        changeWeather();
    }
    public void  setCo2Level(double co2Level) {
        this.co2Level = Math.round(co2Level * D_100) / D_100;
    }
}
