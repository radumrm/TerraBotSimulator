package entities.environment.air;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Getter;
import main.Main;

public class PolarAir extends Air {
    @Getter private double iceCrystalConcentration;
    public PolarAir(AirInput air) {
        super(air);
        this.iceCrystalConcentration = air.getIceCrystalConcentration();
    }
    @Override
    public double getAirQuality() {
        double airQuality = (oxygenLevel * 2) + (100 - Math.abs(temperature)) - (iceCrystalConcentration * 0.05);
        return NormalizedAndRounded(airQuality);
    }
    @Override
    public void addSpecificFieldsToNode (ObjectNode objectNode) {
        objectNode.put("iceCrystalConcentration", this.iceCrystalConcentration);
    }
}
