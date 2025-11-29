package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;
import fileio.TerritorySectionParamsInput;
import map.SimulationMap;
import process_commands.CommandProcessor;
import robot.TerraBot;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */

public final class Main {

    private Main() {
    }
    public static int timestamp = 0;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        List<CommandInput> commands = inputLoader.getCommands();
        List<SimulationInput> simulations = inputLoader.getSimulations();

        int currentSimulation = 0;

        SimulationInput simulationInput = simulations.get(currentSimulation);
        TerritorySectionParamsInput territoryParams = simulationInput.getTerritorySectionParams();

        SimulationMap simulationMap = new SimulationMap(simulationInput.getTerritoryDim());
        simulationMap.populateMap(territoryParams);

        // Initializing the robot
        TerraBot terraBot = new TerraBot(simulationInput.getEnergyPoints());
        // Initializing the command processor
        CommandProcessor processor = new CommandProcessor(simulationMap, terraBot);

        for (CommandInput commandInput : commands) {
            // Update the timestamp at every new command
            timestamp = commandInput.getTimestamp();
            // Update Env
            processor.updateEnvironment();
            // Executing the command and storing the output to ObjectNode
            ObjectNode resultNode = processor.processCommand(commandInput);

            if (resultNode != null) {
                output.add(resultNode);
                if (commandInput.getCommand().equals("endSimulation") && resultNode.has("message")
                        && resultNode.get("message").asText().equals("Simulation has ended.")) {
                    if (currentSimulation < simulations.size() - 1) {
                        currentSimulation++;
                        SimulationInput newSimulationInput = simulations.get(currentSimulation);
                        TerritorySectionParamsInput nextProperties = null;
                        nextProperties = newSimulationInput.getTerritorySectionParams();
                        simulationMap = new SimulationMap(newSimulationInput.getTerritoryDim());
                        simulationMap.populateMap(nextProperties);

                        terraBot = new TerraBot(newSimulationInput.getEnergyPoints());

                        processor = new CommandProcessor(simulationMap, terraBot);
                    }
                }
            }
        }

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
