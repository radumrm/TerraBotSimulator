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
    // Nu am invatat pana acum 2 zile cum se face Double dispatch asa ca am aceste
    // variabile pentru a evita folosirea lui instaceof, nu mai am timp sa modific
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
     * Intoarce airQuality in functie de tipul plantei
     */
    public abstract double getAirQuality();
    /**
     * Intoarce maxScore in functie de tipul plantei
     */
    public abstract double getMaxScore();
    /**
     * Calcularea a toxicitatii, si verificarea de a nu fi negativa
     */
    public double getToxicityAQ() {
        double toxicityAQ = ONE_HUNDRED * (1 - getAirQuality() /  getMaxScore());
        return Math.max(0.0, Math.round(toxicityAQ * D_100) / D_100);
    }
    /**
     * Functie pentru afisarea variabilei specifice tipului de planta
     */
    public abstract void addSpecificFieldsToNode(ObjectNode objectNode);

    // Tine minte cand a inceput schimbarea vremii
    // Initializara cu -2 pentru a nu avea probleme la timestamp 1
    protected int startChangeWeatherTimestamp = MIN_TWO;
    /**
     * Functie pentru setarea variabilelei de startChangeWeatherTimestamp
     */
    public void changeWeather() {
        startChangeWeatherTimestamp = Main.timestamp;
    }
    /**
     * Verificare daca vremea inca mai este schimbata
     */
    public boolean isWeatherChanged() {
        return (Main.timestamp - startChangeWeatherTimestamp) < 2;
    }
    /**
     * Marirea nivelului de oxygen
     */
    public void addOxygenLevel(final double level) {
        this.oxygenLevel += level;
        this.oxygenLevel = Math.round(this.oxygenLevel * D_100) / D_100;
    }
}
