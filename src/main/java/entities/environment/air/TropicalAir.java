package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.D_82;
import static utils.MagicNumber.TWO;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.POINT_ZERO_ONE;
import static utils.MagicNumber.POINT_THREE;
import static utils.MagicNumber.ONE_HUNDRED;

@Getter
public class TropicalAir extends Air {
    private double co2Level;
    private double rainfall = 0;
    public TropicalAir(final AirInput air) {
        super(air);
        setCo2Level(air.getCo2Level());
        super.isTropicalAir = true;
    }
    /**
     * Intoarce airQuality in functie de tipul plantei si de starea vremii
     */
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * TWO) + (humidity * POINT_FIVE)
                - (co2Level * POINT_ZERO_ONE);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality + rainfall * POINT_THREE;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("co2Level", this.co2Level);
    }
    protected static double maxScore = D_82;
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * Seteaza change weather pentru tropicalAir
     */
    public void setRainfall(final double rainfall) {
        this.rainfall = rainfall;
        changeWeather();
    }
    /**
     * Seteaza noul nivel de co2
     */
    public void  setCo2Level(final double co2Level) {
        this.co2Level = Math.round(co2Level * D_100) / D_100;
    }
}
