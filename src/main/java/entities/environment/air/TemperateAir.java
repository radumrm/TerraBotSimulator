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
     * Intoarce airQuality in functie de tipul plantei si de starea vremii
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
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("pollenLevel", this.pollenLevel);
    }
    protected static double maxScore = D_84;
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * Seteaza change weather pentru TemperateAir
     */
    public void setSeason(final String season) {
        this.season = season;
        changeWeather();
    }
}

