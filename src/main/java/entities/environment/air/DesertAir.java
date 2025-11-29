package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(final AirInput air) {
        super(air);
        this.dustParticles = air.getDustParticles();
        super.isDeserAir = true;
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) - (dustParticles * POINT_TWO) - (temperature * POINT_THREE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - THIRTY;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * todo
     * comentriu
     */
    @Override
    public void addSpecificFieldsToNode (final ObjectNode objectNode) {
        objectNode.put("desertStorm", isWeatherChanged());
    }
    protected static double maxScore = SIXTY_FIVE;
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
    public void setDesertStorm() {
        changeWeather();
    }
}
