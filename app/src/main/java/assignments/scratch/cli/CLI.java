package assignments.scratch.cli;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;

import assignments.scratch.config.Config;

public class CLI {
  public void help() {
    System.out.println("Scratch CLI");
    System.out.println("    Calculate total reward based on probabilities and betting amount");
    System.out.println();
    System.out.println("Usage:");
    System.out.println("    java -jar scratch.jar --config config.json --betting-amount 100");
    System.out.println();
    System.out.println("--config <path>");
    System.out.println("    Relative path to JSON config file");
    System.out.println();
    System.out.println("--betting-amount <decimal>");
    System.out.println("    Initial betting amount");
    System.out.println();
    System.out.println("--help");
    System.out.println("    Prints this dialog");
  }

  public Input parse(String[] args) {
    // trigger help dialog
    if (args.length == 0 || (args.length == 1 && args[0].equals("--help"))) return null;

    if (args.length != 4) {
      System.err.println("Invalid number of arguments (got " + args.length + ", expected 4)");
      return null;
    }

    // naive parsing of pairs, let failures be handled on each argument
    Map<String, String> options = Map.of(args[0], args[1], args[2], args[3]);

    try {
      Config config = this.parseConfig(options);
      BigDecimal bettingAmount = this.parseBettingAmount(options);
      return new Input(config, bettingAmount);
    } catch (Exception e) {
      System.err.println("Failed to parse options");
      return null;
    }
  }

  private Config parseConfig(Map<String, String> options) throws IOException {
    String input = Files.readString(Paths.get(options.get("--config")));
    ObjectMapper objectMapper = JsonMapper.builder()
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .build();

    return objectMapper.readValue(input, Config.class);
  }

  private BigDecimal parseBettingAmount(Map<String, String> options) {
    return new BigDecimal(options.get("--betting-amount"));
  }

  public void print(Output output) {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

    try {
      String json = writer.writeValueAsString(output);
      System.out.println(json);
    } catch (JsonProcessingException e) {
      System.err.println("Failed to serialize output to JSON");
    }
  }
}
