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

        // Loading the data
        SimulationInput simData = inputLoader.getSimulations().getFirst();
        TerritorySectionParamsInput proprieties = simData.getTerritorySectionParams();

        // Initializing and Populating the map
        SimulationMap simulationMap = new SimulationMap(simData.getTerritoryDim());
        simulationMap.PopulateMap(proprieties);

        // Initializing the robot
        TerraBot terraBot = new TerraBot(simData.getEnergyPoints());

        // Initializing the command processor
        CommandProcessor processor = new CommandProcessor(simulationMap, terraBot);

        // Storing the command in a List and then executing them 1 by 1
        List<CommandInput> commands = inputLoader.getCommands();

        for (CommandInput cmd : commands) {
            // Update the timestamp at every new command
            timestamp = cmd.getTimestamp();

            // Executing the command and storing the output to ObjectNode
            ObjectNode resultNode = processor.processCommand(cmd);

            if (resultNode != null) {
                output.add(resultNode);
            }
        }

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
