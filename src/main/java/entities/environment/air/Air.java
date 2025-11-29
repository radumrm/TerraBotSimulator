package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import fileio.AirInput;
import lombok.Getter;
import lombok.Setter;
import main.Main;

import static utils.MagicNumber.*;

@Getter
@Setter
public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    boolean isDeserAir = false;
    boolean isMountainAir = false;
    boolean isPolarAir = false;
    boolean isTemperateAir = false;
    boolean isTropicalAir = false;
    public Air(AirInput air) {
        super(air.getName(), air.getMass(), air.getType());
        this.humidity = air.getHumidity();
        this.temperature = air.getTemperature();
        this.oxygenLevel = air.getOxygenLevel();
    }

    public abstract double getAirQuality();

    public abstract double getMaxScore();

    public double getToxicityAQ() {
        double toxicityAQ = ONE_HUNDRED * (1 - getAirQuality() /  getMaxScore());
        return Math.max(0.0, Math.round((ONE_HUNDRED * (1 - getAirQuality() / getMaxScore())) * D_100) / D_100);
    }

    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);

    protected int startChangeWeatherTimestamp = MIN_TWO;
    public void changeWeather() {
        startChangeWeatherTimestamp = Main.timestamp;
    }

    public boolean isWeatherChanged() {
        return (Main.timestamp - startChangeWeatherTimestamp) < 2;
    }

    public void addOxygenLevel(double level) {
        this.oxygenLevel += level;
        this.oxygenLevel = Math.round(this.oxygenLevel * D_100) / D_100;
    }
}
