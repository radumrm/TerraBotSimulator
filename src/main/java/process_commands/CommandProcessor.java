package process_commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Entity;
import entities.environment.air.TemperateAir;
import entities.environment.air.DesertAir;
import entities.environment.air.TropicalAir;
import entities.environment.air.MountainAir;
import entities.environment.air.PolarAir;
import entities.environment.air.Air;
import entities.plants.Plant;
import entities.animals.Animal;
import entities.environment.Water;
import entities.environment.soil.Soil;
import fileio.CommandInput;
import main.Main;
import map.Box;
import map.SimulationMap;
import robot.TerraBot;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.POINT_ONE;
import static utils.MagicNumber.D_40;
import static utils.MagicNumber.D_70;
import static utils.MagicNumber.TEN;
import static utils.MagicNumber.SEVEN;

public class CommandProcessor {
    private final SimulationMap simulationMap;
    private final TerraBot terraBot;
    private final ObjectMapper mapper;

    private boolean simulationStarted = false;

    public CommandProcessor(final SimulationMap simulationMap, final TerraBot terraBot) {
        this.simulationMap = simulationMap;
        this.terraBot = terraBot;
        this.mapper = new ObjectMapper();
    }
    /**
     * todo
     * comentriu
     */
    public ObjectNode processCommand(final CommandInput commandInput) {
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
            case "learnFact"  -> learnFact(commandInput);
            case "printKnowledgeBase" -> printKnowledgeBase(commandInput);
            case "improveEnvironment" -> improveEnvironment(commandInput);
            default -> null;
        };
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode createNode(final String command, final String message) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("message", message);
        objectNode.put("timestamp", Main.timestamp);
        return objectNode;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode createSoilNode(final Soil soil) {
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
    /**
     * todo
     * comentriu
     */
    private ObjectNode createAirNode(final Air air) {
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
    /**
     * todo
     * comentriu
     */
    private ObjectNode createPlantNode(final Plant plant) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", plant.getType());
        objectNode.put("name", plant.getName());
        objectNode.put("mass", plant.getMass());
        return  objectNode;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode createWaterNode(final Water water) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", water.getType());
        objectNode.put("name", water.getName());
        objectNode.put("mass", water.getMass());
        return  objectNode;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode createAnimalNode(final Animal animal) {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", animal.getType());
        objectNode.put("name", animal.getName());
        objectNode.put("mass", animal.getMass());
        return  objectNode;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode checkStartAndCharge(final String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }
        if (terraBot.isCharging(Main.timestamp)) {
            return createNode(command, "ERROR: Robot still charging. Cannot perform action");
        }
        return null;
    }
    /**
     * todo
     * comentriu
     */
    private String getQualityAsString(final double quality) {
        if (quality >= D_70) {
            return "good";
        } else if (quality >= D_40) {
            return "moderate";
        }
        return "poor";
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode startSimulation(final String command) {
        if (simulationStarted) {
            return createNode(command, "ERROR: Simulation already started. Cannot perform action");
        }
        this.simulationStarted = true;
        return createNode(command, "Simulation has started.");
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode endSimulation(final String command) {
        if (!simulationStarted) {
            return createNode(command, "ERROR: Simulation not started. Cannot perform action");
        }
        this.simulationStarted = false;
        return createNode(command, "Simulation has ended.");
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode printEnvConditions(final String command) {
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
    /**
     * todo
     * comentriu
     */
    private ObjectNode printMap(final String command) {
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
                if (box.getPlant() != null) {
                    count++;
                }
                if (box.getAnimal() != null) {
                    count++;
                }
                if (box.getWater() != null) {
                    count++;
                }

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
    /**
     * todo
     * comentriu
     */
    private ObjectNode moveRobot(final String command) {
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
                String out = null;
                out = "The robot has successfully moved to position (" + newX + ", " + newY + ").";
                return createNode(command, out);
            } else {
                return createNode(command, "ERROR: Not enough battery left. Cannot perform action");
            }
        }
        return null;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode getEnergyStatus(final String command) {
        ObjectNode error = checkStartAndCharge(command);
        if (error != null) {
            return error;
        }
        String out = "TerraBot has " + terraBot.getEnergy() + " energy points left.";
        return  createNode(command, out);
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode rechargeBattery(final CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        terraBot.recharge(commandInput.getTimeToCharge(), Main.timestamp);
        for (int i = 0; i < commandInput.getTimeToCharge(); i++) {
            updateEnvironment();
        }
        return createNode(commandInput.getCommand(), "Robot battery is charging.");
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode changeWeatherConditions(final CommandInput commandInput) {
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
                        if (air.isDeserAir()) {
                            ((DesertAir) air).setDesertStorm();
                            typeFound = true;
                        }
                        break;

                    case "rainfall":
                        if (air.isTropicalAir()) {
                            ((TropicalAir) air).setRainfall(commandInput.getRainfall());
                            typeFound = true;
                        }
                        break;

                    case "peopleHiking":
                        if (air.isMountainAir()) {
                            ((MountainAir) air).setNumberOfHikers(commandInput.getNumberOfHikers());
                            typeFound = true;
                        }
                        break;

                    case "polarStorm":
                        if (air.isPolarAir()) {
                            ((PolarAir) air).setWindSpeed(commandInput.getWindSpeed());
                            typeFound = true;
                        }
                        break;

                    case "newSeason":
                        if (air.isTemperateAir()) {
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
        String out = null;
        out = "ERROR: The weather change does not affect the environment. Cannot perform action";
        return createNode(commandInput.getCommand(), out);
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode scanObject(final CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        if (terraBot.getEnergy() < SEVEN) {
            String out = "ERROR: Not enough energy to perform action";
            return createNode(commandInput.getCommand(), out);
        }
        String scannedType = "";
        if (!commandInput.getSound().equals("none")) {
            scannedType = "animal";
        } else if (!commandInput.getSmell().equals("none")
                || !commandInput.getColor().equals("none")) {
            scannedType = "plant";
        } else {
            scannedType = "water";
        }
        Box box = simulationMap.getBox(terraBot.getX(),  terraBot.getY());
        switch (scannedType) {
            case "animal":
                if (box.getAnimal() != null) {
                    terraBot.addToInventory(box.getAnimal());
                    box.getAnimal().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - SEVEN);
                    String out = "The scanned object is an animal.";
                    return createNode(commandInput.getCommand(), out);
                }
                break;
            case "plant":
                if (box.getPlant() != null) {
                    terraBot.addToInventory(box.getPlant());
                    box.getPlant().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - SEVEN);
                    return createNode(commandInput.getCommand(), "The scanned object is a plant.");
                }
                break;
            case "water":
                if (box.getWater() != null) {
                    terraBot.addToInventory(box.getWater());
                    box.getWater().setScannedTimestamp(Main.timestamp);
                    terraBot.setEnergy(terraBot.getEnergy() - SEVEN);
                    return createNode(commandInput.getCommand(), "The scanned object is water.");
                }
                break;
        }
        String out = "ERROR: Object not found. Cannot perform action";
        return createNode(commandInput.getCommand(), out);
    }
    /**
     * todo
     * comentriu
     */
    public void updateEnvironment() {
        if (!simulationStarted) {
            return;
        }
        for (Entity entity : terraBot.getScannedEntities()) {
            if (entity.isPlant()) {
                Box box = simulationMap.getBox(entity.getX(), entity.getY());
                Plant plant =  (Plant) entity;
                 plant.grow();
                if (!plant.isDead()) {
                    box.getAir().addOxygenLevel(plant.oxygenLevel());
                } else {
                     simulationMap.getBox(plant.getX(), plant.getY()).setPlant(null);
                }
            }
        }
        for (Entity entity : terraBot.getScannedEntities()) {
            if (entity.isWater()) {
                Water water = (Water) entity;
                Box box = simulationMap.getBox(entity.getX(), entity.getY());
                int age = Main.timestamp - water.getScannedTimestamp();
                if (box.getPlant() != null && box.getWater().isScanned()) {
                    box.getPlant().grow();
                }
                if (age % 2 == 0) {
                    Air air = box.getAir();
                    double newHumidity = air.getHumidity() + POINT_ONE;
                    air.setHumidity(Math.round(newHumidity * D_100) / D_100);

                    Soil soil = box.getSoil();
                    double newWaterRetention = soil.getWaterRetention() + POINT_ONE;
                    soil.setWaterRetention(Math.round(newWaterRetention * D_100) / D_100);
                }
            }
        }

        for (Entity entity : terraBot.getScannedEntities()) {
            if (entity.isAnimal()) {
                Animal animal = (Animal) entity;

                if (animal.isDead()) {
                    continue;
                }

                int age =  Main.timestamp - animal.getScannedTimestamp();
                if (age % 2 == 0) {
                    animal.move(simulationMap, terraBot);
                    simulationMap.getBox(animal.getX(), animal.getY()).setAnimal(animal);
                }
            }
        }
    }
    /**
     * todo
     * comentriu
     */
    public ObjectNode learnFact(final CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        if (terraBot.getEnergy() < 2) {
            String out = "ERROR: Not enough battery left. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }
        String scannedEntityName = commandInput.getComponents();
        if (!terraBot.hasEntityScanned(scannedEntityName)) {
            String out = "ERROR: Subject not yet saved. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }
        terraBot.addFact(scannedEntityName, commandInput.getSubject());
        terraBot.setEnergy(terraBot.getEnergy() - 2);
        String out = "The fact has been successfully saved in the database.";
        return createNode(commandInput.getCommand(), out);
    }
    /**
     * todo
     * comentriu
     */
    public ObjectNode printKnowledgeBase(final CommandInput commandInput) {
        if (!simulationStarted) {
            String out = "ERROR: Simulation not started. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        ArrayNode outputArray = mapper.createArrayNode();

        for (Entity entity : terraBot.getScannedEntities()) {
            String topic = entity.getName();
            if (terraBot.getFactsDataBase().containsKey(topic)) {
                ObjectNode topicNode = mapper.createObjectNode();
                topicNode.put("topic", topic);
                ArrayNode factsNode = mapper.createArrayNode();
                for (String fact : terraBot.getFactsDataBase().get(topic)) {
                    factsNode.add(fact);
                }
                topicNode.set("facts", factsNode);
                outputArray.add(topicNode);
            }
        }
        objectNode.set("output", outputArray);
        objectNode.put("timestamp", Main.timestamp);
        return objectNode;
    }
    /**
     * todo
     * comentriu
     */
    private ObjectNode improveEnvironment(final CommandInput commandInput) {
        ObjectNode error = checkStartAndCharge(commandInput.getCommand());
        if (error != null) {
            return error;
        }
        if (terraBot.getEnergy() < TEN) {
            String out = "ERROR: Not enough battery left. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }

        String entityName = commandInput.getName();
        String improvementType = commandInput.getImprovementType();

        Entity entity = terraBot.getEntityFromInventory(entityName);

        if (entity == null) {
            String out = "ERROR: Subject not yet saved. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }

        if (!terraBot.hasFactsAbout(entityName)) {
            String out = "ERROR: Fact not yet saved. Cannot perform action";
            return createNode(commandInput.getCommand(), out);
        }

        terraBot.setEnergy(terraBot.getEnergy() - TEN);
        terraBot.removeFromInventory(entityName);

        Box box = simulationMap.getBox(terraBot.getX(),  terraBot.getY());

        String outputMessage = entity.improveEnvironment(box, improvementType);
        return  createNode(commandInput.getCommand(), outputMessage);
    }
}
