package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.POINT_TWO;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.THIRTY;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.SIXTY_FIVE;

@Getter
public class DesertAir extends Air {
    private double dustParticles;

    public DesertAir(final AirInput air) {
        super(air);
        this.dustParticles = air.getDustParticles();
        super.isDeserAir = true;
    }
    /**
     * Intoarce airQuality in functie de tipul plantei si de starea vremii
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) - (dustParticles * POINT_TWO)
                - (temperature * POINT_THREE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - THIRTY;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("desertStorm", isWeatherChanged());
    }
    protected static double maxScore = SIXTY_FIVE;
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * Seteaza change weather pentru DesertAir
     */
    public void setDesertStorm() {
        changeWeather();
    }
}
