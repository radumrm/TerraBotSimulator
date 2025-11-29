package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.*;

@Getter
public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(AirInput air) {
        super(air);
        this.dustParticles = air.getDustParticles();
        this.isDeserAir = true;
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) - (dustParticles * POINT_TWO) - (temperature * POINT_THREE);
        double normAirQuality = NormalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - THIRTY;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("desertStorm", isWeatherChanged());
    }
    protected static double maxScore = SIXTY_FIVE;
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    public void setDesertStorm() {
        changeWeather();
    }
}
