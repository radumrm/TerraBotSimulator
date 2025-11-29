package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import main.Main;

import static utils.MagicNumber.ONE_HUNDRED;
import static utils.MagicNumber.D_100;
import static utils.MagicNumber.MIN_TWO;

@Getter
@Setter
public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    protected boolean isDeserAir = false;
    protected boolean isMountainAir = false;
    protected boolean isPolarAir = false;
    protected boolean isTemperateAir = false;
    protected boolean isTropicalAir = false;
    public Air(final AirInput air) {
        super(air.getName(), air.getMass(), air.getType());
        this.humidity = air.getHumidity();
        this.temperature = air.getTemperature();
        this.oxygenLevel = air.getOxygenLevel();
    }
    /**
     * todo
     * comentriu
     */
    public abstract double getAirQuality();
    /**
     * todo
     * comentriu
     */
    public abstract double getMaxScore();
    /**
     * todo
     * comentriu
     */
    public double getToxicityAQ() {
        double toxicityAQ = ONE_HUNDRED * (1 - getAirQuality() /  getMaxScore());
        return Math.max(0.0, Math.round(toxicityAQ * D_100) / D_100);
    }
    /**
     * todo
     * comentriu
     */
    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);

    protected int startChangeWeatherTimestamp = MIN_TWO;
    /**
     * todo
     * comentriu
     */
    public void changeWeather() {
        startChangeWeatherTimestamp = Main.timestamp;
    }
    /**
     * todo
     * comentriu
     */
    public boolean isWeatherChanged() {
        return (Main.timestamp - startChangeWeatherTimestamp) < 2;
    }
    /**
     * todo
     * comentriu
     */
    public void addOxygenLevel(final double level) {
        this.oxygenLevel += level;
        this.oxygenLevel = Math.round(this.oxygenLevel * D_100) / D_100;
    }
}
