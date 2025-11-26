package process_commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Animal;
import entities.Plant;
import entities.environment.Water;
import entities.environment.air.Air;
import entities.environment.soil.Soil;
import fileio.CommandInput;
import main.Main;
import map.Box;
import map.SimulationMap;
import robot.TerraBot;

public class CommandProcessor {
    private final SimulationMap simulationMap;
    private final TerraBot terraBot;
    private final ObjectMapper mapper;

    private boolean simulationStarted = false;

    public CommandProcessor(SimulationMap simulationMap, TerraBot terraBot) {
        this.simulationMap = simulationMap;
        this.terraBot = terraBot;
        this.mapper = new ObjectMapper();
    }

    public ObjectNode processCommand(CommandInput commandInput) {
        String command = commandInput.getCommand();
        return switch (command) {
            case "startSimulation" -> startSimulation(command);
            case "endSimulation" -> endSimulation(command);
            case "printEnvConditions" -> printEnvConditions(command);
            case "printMap" -> printMap(command);
            default -> null;
        };
    }

    private ObjectNode createNode(String command, String message) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("message", message);
        objectNode.put("timestamp", Main.timestamp);
        return objectNode;
    }

    private ObjectNode createSoilNode(Soil soil) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", soil.getName());
        objectNode.put("mass", soil.getMass());
        objectNode.put("type", soil.getType());
        objectNode.put("nitrogen", soil.getNitrogen());
        objectNode.put("waterRetention", soil.getWaterRetention());
        objectNode.put("soilpH", soil.getSoilpH());
        objectNode.put("organicMatter", soil.getOrganicMatter());
        objectNode.put("soilQuality", soil.getSoilQuality());
        soil.addSpecificFieldsToNode(objectNode);
        return objectNode;
    }

    private ObjectNode createAirNode(Air air) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", air.getName());
        objectNode.put("mass", air.getMass());
        objectNode.put("type", air.getType());
        objectNode.put("humidity", air.getHumidity());
        objectNode.put("temperature", air.getTemperature());
        objectNode.put("oxygenLevel", air.getOxygenLevel());
        objectNode.put("airQuality", air.getAirQuality());
        air.addSpecificFieldsToNode(objectNode);
        return objectNode;
    }

    private ObjectNode createPlantNode(Plant plant) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", plant.getName());
        objectNode.put("mass", plant.getMass());
        objectNode.put("type", plant.getType());
        return  objectNode;
    }

    private ObjectNode createWaterNode(Water water) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", water.getName());
        objectNode.put("mass", water.getMass());
        objectNode.put("type", water.getType());
        return  objectNode;
    }

    private ObjectNode createAnimalNode(Animal animal) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", animal.getName());
        objectNode.put("mass", animal.getMass());
        objectNode.put("type", animal.getType());
        return  objectNode;
    }

    private String getQualityAsString(double quality) {
        if (quality >= 70)
            return "good";
        else if (quality >= 40)
            return "moderate";
        return "poor";
    }

    private ObjectNode startSimulation(String command) {
        if (simulationStarted) {
            return createNode(command, "ERROR: Simulation already started. Cannot perform action");
        }
        this.simulationStarted = true;
        return createNode(command, "Simulation has started.");
    }

    private ObjectNode endSimulation(String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }
        this.simulationStarted = false;
        return createNode(command, "Simulation has ended.");
    }

    private ObjectNode printEnvConditions(String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);

        Box currentBox = simulationMap.getBox(terraBot.getX(), terraBot.getY());

        ObjectNode outputNode = mapper.createObjectNode();

        if (currentBox.getSoil() != null) {
            outputNode.set("soil", createSoilNode(currentBox.getSoil()));
        }
        if (currentBox.getPlant() != null) {
            outputNode.set("plants", createPlantNode(currentBox.getPlant()));
        }
        if (currentBox.getAnimal() != null) {
            outputNode.set("animals", createAnimalNode(currentBox.getAnimal()));
        }
        if (currentBox.getWater() != null) {
            outputNode.set("water", createWaterNode(currentBox.getWater()));
        }
        if (currentBox.getAir() != null) {
            outputNode.set("air", createAirNode(currentBox.getAir()));
        }

        objectNode.set("output", outputNode);
        objectNode.put("timestamp", Main.timestamp);
        return objectNode;
    }

    private ObjectNode printMap(String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);

        ArrayNode arrayNode = mapper.createArrayNode();

        for (int y = 0; y < simulationMap.getHeight(); y++) {
            for (int x = 0; x < simulationMap.getWidth(); x++) {
                Box box = simulationMap.getBox(x, y);
                ObjectNode boxNode = mapper.createObjectNode();
                ArrayNode sectionNode = mapper.createArrayNode();

                sectionNode.add(x);
                sectionNode.add(y);
                boxNode.set("section", sectionNode);

                int count = 0;
                if (box.getPlant() != null) count++;
                if (box.getAnimal() != null) count++;
                if (box.getWater() != null) count++;
                boxNode.put("totalNrOfObjects", count);

                double airQ = box.getAir().getAirQuality();
                boxNode.put("airQuality", getQualityAsString(airQ));

                double soilQ = box.getSoil().getSoilQuality();
                boxNode.put("soilQuality", getQualityAsString(soilQ));

                arrayNode.add(boxNode);
            }
        }

        objectNode.set("output", arrayNode);
        objectNode.put("timestamp", Main.timestamp);
        return objectNode;
    }

}
