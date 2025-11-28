package process_commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import entities.environment.air.*;
import entities.plants.Plant;
import entities.animals.Animal;
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
            case "moveRobot" -> moveRobot(command);
            case "getEnergyStatus" -> getEnergyStatus(command);
            case "rechargeBattery" -> rechargeBattery(commandInput);
            case "changeWeatherConditions" -> changeWeatherConditions(commandInput);
            case "scanObject" -> scanObject(commandInput);
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
        objectNode.put("type", soil.getType());
        objectNode.put("name", soil.getName());
        objectNode.put("mass", soil.getMass());
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
        objectNode.put("type", air.getType());
        objectNode.put("name", air.getName());
        objectNode.put("mass", air.getMass());
        objectNode.put("humidity", air.getHumidity());
        objectNode.put("temperature", air.getTemperature());
        objectNode.put("oxygenLevel", air.getOxygenLevel());
        objectNode.put("airQuality", air.getAirQuality());
        air.addSpecificFieldsToNode(objectNode);
        return objectNode;
    }

    private ObjectNode createPlantNode(Plant plant) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", plant.getType());
        objectNode.put("name", plant.getName());
        objectNode.put("mass", plant.getMass());
        return  objectNode;
    }

    private ObjectNode createWaterNode(Water water) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", water.getType());
        objectNode.put("name", water.getName());
        objectNode.put("mass", water.getMass());
        return  objectNode;
    }

    private ObjectNode createAnimalNode(Animal animal) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", animal.getType());
        objectNode.put("name", animal.getName());
        objectNode.put("mass", animal.getMass());
        return  objectNode;
    }

    private ObjectNode checkStartAndCharge(String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }
        if (terraBot.isCharging(Main.timestamp)) {
            return createNode(command, "ERROR: Robot still charging. Cannot perform action");
        }
        return null;
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
        ObjectNode error = checkStartAndCharge(command);
        if (error != null) {
            return error;
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
        ObjectNode error = checkStartAndCharge(command);
        if (error != null) {
            return error;
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
                if (box.getPlant() != null)
                    count++;
                if (box.getAnimal() != null)
                    count++;
                if (box.getWater() != null)
                    count++;
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

    private ObjectNode moveRobot(String command) {
        ObjectNode error = checkStartAndCharge(command);
        if (error != null) {
            return error;
        }
        int x = this.terraBot.getX();
        int y = this.terraBot.getY();
        int height = simulationMap.getHeight();
        int width = simulationMap.getWidth();

        // Up Right Down Left
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int newX = -1;
        int newY = -1;
        int bestScore = Integer.MAX_VALUE;

        for (int[] direction : directions) {
            int auxX = x + direction[0];
            int auxY = y + direction[1];

            if (auxX >= 0 && auxX < width && auxY >= 0 && auxY < height) {
                Box auxBox = simulationMap.getBox(auxX, auxY);
                int score = auxBox.getCost();
                if (score < bestScore) {
                    bestScore = score;
                    newX = auxX;
                    newY = auxY;
                }
            }
        }
        if (newX != -1) {
            if (terraBot.getEnergy() >= bestScore) {
                terraBot.setEnergy(terraBot.getEnergy() - bestScore);
                terraBot.setX(newX);
                terraBot.setY(newY);
                return createNode(command, "The robot has successfully moved to position (" + newX + ", " + newY + ").");
            } else {
                return createNode(command, "ERROR: Not enough battery left. Cannot perform action");
            }
        }
        return null;
    }

    private ObjectNode getEnergyStatus(String command) {
        ObjectNode error = checkStartAndCharge(command);
        if (error != null) {
            return error;
        }
        return  createNode(command, "TerraBot has " + terraBot.getEnergy() +" energy points left.");
    }

    private ObjectNode rechargeBattery(CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        terraBot.recharge(commandInput.getTimeToCharge(), Main.timestamp);
        return createNode(commandInput.getCommand(), "Robot battery is charging.");
    }

    private ObjectNode changeWeatherConditions(CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }

        String type = commandInput.getType();
        boolean typeFound = false;
        for (int y = 0; y < simulationMap.getHeight(); y++) {
            for (int x = 0; x < simulationMap.getWidth(); x++) {
                Air air =  simulationMap.getBox(x, y).getAir();
                switch (type) {
                    case "desertStorm":
                        if (air instanceof DesertAir) {
                            ((DesertAir) air).setDesertStorm();
                            typeFound = true;
                        }
                        break;

                        case "rainfall":
                        if (air instanceof TropicalAir) {
                            ((TropicalAir) air).setRainfall(commandInput.getRainfall());
                            typeFound = true;
                        }
                        break;

                        case "peopleHiking":
                        if (air instanceof MountainAir) {
                            ((MountainAir) air).setNumberOfHikers(commandInput.getNumberOfHikers());
                            typeFound = true;
                        }
                        break;

                        case "polarStorm":
                        if (air instanceof PolarAir) {
                            ((PolarAir) air).setWindSpeed(commandInput.getWindSpeed());
                            typeFound = true;
                        }
                        break;

                        case "newSeason":
                        if (air instanceof TemperateAir) {
                            ((TemperateAir) air).setSeason(commandInput.getSeason());
                            typeFound = true;
                        }
                        break;
                }
            }
        }
        if (typeFound) {
            return createNode(commandInput.getCommand(), "The weather has changed.");
        }
        return createNode(commandInput.getCommand(), "ERROR: The weather change does not affect the environment. Cannot perform action");
    }

    private ObjectNode scanObject(CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        if (terraBot.getEnergy() < 7) {
            return createNode(commandInput.getCommand(), "ERROR: Not enough battery left. Cannot perform action");
        }
        String scannedType = "";
        if (!commandInput.getSound().equals("none")) {
            scannedType = "animal";
        } else if (!commandInput.getSmell().equals("none") || !commandInput.getColor().equals("none")) {
            scannedType = "plant";
        } else {
            scannedType = "water";
        }
        Box box = simulationMap.getBox(terraBot.getX(),  terraBot.getY());

        switch(scannedType) {
            case "animal":
                if (box.getAnimal() != null) {
                    terraBot.addToInventory(box.getAnimal());
                    box.getAnimal().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - 7);
                    return createNode(commandInput.getCommand(), "The scanned object is an animal.");
                }
                break;
            case "plant":
                if (box.getPlant() != null) {
                    terraBot.addToInventory(box.getPlant());
                    box.getPlant().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - 7);
                    return createNode(commandInput.getCommand(), "The scanned object is a plant.");
                }
                break;
            case "water":
                if (box.getWater() != null) {
                    terraBot.addToInventory(box.getWater());
                    box.getWater().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - 7);
                    return createNode(commandInput.getCommand(), "The scanned object is water.");
                }
                break;
        }
        return createNode(commandInput.getCommand(), "ERROR: Object not found. Cannot perform action");
    }

    public void updateEnvironment() {
        if (!simulationStarted)
            return;
        for (Entity entity : terraBot.getInventory()) {
            if (entity instanceof Plant) {
                Box box = simulationMap.getBox(entity.getX(), entity.getY());
                Plant plant =  (Plant) entity;
                 plant.grow();
                if (!plant.isDead()) {
                    box.getAir().addOxygenLevel(plant.oxygenLevel());
                }
            }
        }
        for (Entity entity : terraBot.getInventory()) {
            if (entity instanceof Water) {
                Water water = (Water) entity;
                Box box = simulationMap.getBox(entity.getX(), entity.getY());
                int age = Main.timestamp - water.getScannedTimestamp();
                if (box.getPlant() != null && box.getWater().isScanned()) {
                    box.getPlant().grow();
                }
                if (age % 2 == 0) {
                    Air air = box.getAir();
                    double newHumidity = air.getHumidity() + 0.1;
                    air.setHumidity(Math.round(newHumidity * 100.0) / 100.0);

                    Soil soil = box.getSoil();
                    double newWaterRetention = soil.getWaterRetention() + 0.1;
                    soil.setWaterRetention(Math.round(newWaterRetention * 100.0) / 100.0);
                }
            }
        }

        for (Entity entity : terraBot.getInventory()) {
            if (entity instanceof Animal) {
                Animal animal = (Animal) entity;

                if (animal.isDead()) {
                    continue;
                }

                int age =  Main.timestamp - animal.getScannedTimestamp();
                if (age % 2 == 0) {
                    animal.move(simulationMap, terraBot);
                    animal.eat(simulationMap.getBox(animal.getX(), animal.getY()));
                    simulationMap.getBox(animal.getX(), animal.getY()).setAnimal(animal);
                } else {
                    animal.eat(simulationMap.getBox(animal.getX(), animal.getY()));
                }
            }
        }
    }
}
