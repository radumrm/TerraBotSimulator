package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.POINT_ZERO_FIVE;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.POINT_TWO;
import static utils.MagicNumber.D_142;

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
     * Intoarce airQuality in functie de tipul plantei si de starea vremii
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (ONE_HUNDRED - Math.abs(temperature))
        - (iceCrystalConcentration * POINT_ZERO_FIVE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - windSpeed * POINT_TWO;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("iceCrystalConcentration", this.iceCrystalConcentration);
    }
    protected static double maxScore = D_142;
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * Seteaza change weather pentru PolarAir
     */
    public void setWindSpeed(final double windSpeed) {
        this.windSpeed = windSpeed;
        changeWeather();
    }
}
