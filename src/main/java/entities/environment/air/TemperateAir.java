package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.D_84;
import static utils.MagicNumber.D_15;
import static utils.MagicNumber.TWO;
import static utils.MagicNumber.POINT_SEVEN;
import static utils.MagicNumber.POINT_ONE;
import static utils.MagicNumber.ONE_HUNDRED;

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
        double airQuality = (oxygenLevel * TWO)
                + (humidity * POINT_SEVEN) - (pollenLevel * POINT_ONE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            double seasonPenalty = season.equalsIgnoreCase("Spring") ? D_15 : 0;
            return normAirQuality - seasonPenalty;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("pollenLevel", this.pollenLevel);
    }
    protected static double maxScore = D_84;
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

