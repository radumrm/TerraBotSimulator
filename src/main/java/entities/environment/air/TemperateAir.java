package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public class TemperateAir extends Air {
    private double pollenLevel;
    private String season = "";
    public TemperateAir(final AirInput air) {
        super(air);
        this.pollenLevel = air.getPollenLevel();
        super.isTemperateAir = true;
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (humidity * POINT_SEVEN) - (pollenLevel * POINT_ONE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            double seasonPenalty = season.equalsIgnoreCase("Spring") ? 15 : 0;
            return normAirQuality - seasonPenalty;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("pollenLevel", this.pollenLevel);
    }
    protected static double maxScore = 84;
    /**
     * todo
     * comentriu
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * todo
     * comentriu
     */
    public void setSeason(final String season) {
        this.season = season;
        changeWeather();
    }
}

