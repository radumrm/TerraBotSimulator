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
        // Stocam atat comenzile cat si simulatiile in liste
        List<CommandInput> commands = inputLoader.getCommands();
        List<SimulationInput> simulations = inputLoader.getSimulations();
        // Indexul simularii curente, il vom folosii pentru a calcula daca
        // mai exista inca o simulare dupa cea curenta
        int currentSimulation = 0;
        // Luam simularea curenta de la indexul nostru
        SimulationInput simulationInput = simulations.get(currentSimulation);
        // Luam parametrii acesteia
        TerritorySectionParamsInput territoryParams = simulationInput.getTerritorySectionParams();
        // Initializam harta curenta si o populam
        SimulationMap simulationMap = new SimulationMap(simulationInput.getTerritoryDim());
        simulationMap.populateMap(territoryParams);
        // Inializam robotul
        TerraBot terraBot = new TerraBot(simulationInput.getEnergyPoints());
        // Initializam procesorul de comenzi
        CommandProcessor processor = new CommandProcessor(simulationMap, terraBot);

        for (CommandInput commandInput : commands) {
            // Update la timestamp pentru fiecare comanda
            timestamp = commandInput.getTimestamp();
            // Update la env
            processor.updateEnvironment();
            // Executam comanda si pastram out-ul comenzii curente in resultNode
            ObjectNode resultNode = processor.processCommand(commandInput);
            // Verificam daca comanda s-a procesat corect
            if (resultNode != null) {
                // Adaugam la out-ul programului, out-ul comezii curente
                output.add(resultNode);
                // Verificam daca am primit comanda endSimulation si aceasta
                // a returnat mesajul valid: Simulation has ended.
                if (commandInput.getCommand().equals("endSimulation")
                        && resultNode.get("message").asText().equals("Simulation has ended.")) {
                    // Verificam daca mai exista cel putin inca o simulare dispoinibila
                    if (currentSimulation < simulations.size() - 1) {
                        // Daca exista, incrementam counterul
                        currentSimulation++;
                        // Stocam noua simulare din siluations in newSimulationInput
                        SimulationInput newSimulationInput = simulations.get(currentSimulation);
                        // Generam si reinitializam iar proprietatiile, harta, robotul si
                        // commandProcessorul
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
