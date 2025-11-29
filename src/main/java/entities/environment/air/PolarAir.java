package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public class PolarAir extends Air {
    private double iceCrystalConcentration;
    private double windSpeed = 0;
    public PolarAir(final AirInput air) {
        super(air);
        this.iceCrystalConcentration = air.getIceCrystalConcentration();
        super.isPolarAir = true;
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (ONE_HUNDRED - Math.abs(temperature)) - (iceCrystalConcentration * POINT_ZERO_FIVE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - windSpeed * POINT_TWO;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("iceCrystalConcentration", this.iceCrystalConcentration);
    }
    protected static double maxScore = ONE_FOUR_TWO;
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
    public void setWindSpeed(final double windSpeed) {
        this.windSpeed = windSpeed;
        changeWeather();
    }
}
