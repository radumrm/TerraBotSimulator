package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;

import static utils.MagicNumber.ONE_THOUSAND;
import static utils.MagicNumber.POINT_FIVE;
import static utils.MagicNumber.POINT_SIX;
import static utils.MagicNumber.POINT_ONE;
import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.SEVENTY_EIGHT;
import static utils.MagicNumber.TWO;

@Getter
public class MountainAir extends Air {
    private double altitude;
    private int numberOfHikers = 0;
    public MountainAir(final AirInput air) {
        super(air);
        this.altitude = air.getAltitude();
        super.isMountainAir = true;
    }
    /**
     * Intoarce airQuality in functie de tipul plantei si de starea vremii
     */
    @Override
    public double getAirQuality() {
        double airQuality = ((oxygenLevel - (altitude / ONE_THOUSAND * POINT_FIVE)) * TWO)
                + (humidity * POINT_SIX);
        double normAirQuality = normalizedAndRounded(airQuality);
        if (isWeatherChanged()) {
            return normAirQuality - POINT_ONE * numberOfHikers;
        }
        return Math.min(normAirQuality, ONE_HUNDRED);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    @Override
    public void addSpecificFieldsToNode(final ObjectNode objectNode) {
        objectNode.put("altitude", this.altitude);
    }
    protected static double maxScore = SEVENTY_EIGHT;
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    @Override
    public double getMaxScore() {
        return maxScore;
    }
    /**
     * Seteaza change weather pentru MountainAir
     */
    public void setNumberOfHikers(final int numberOfHikers) {
        this.numberOfHikers = numberOfHikers;
        changeWeather();
    }
}
