package assignments.scratch;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import assignments.scratch.config.Config;
import assignments.scratch.game.Game;
import assignments.scratch.game.Result;

public class App {
  public Config parseJson(String json) throws JsonMappingException, JsonProcessingException {
    ObjectMapper objectMapper = JsonMapper.builder()
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .build();
    return objectMapper.readValue(json, Config.class);
  }

  public static void main(String[] args) {
    Config config;
    try {
      String input = Files.readString(Paths.get("src/test/resources/config.json"));
      config = new App().parseJson(input);
    } catch (IOException e) {
      System.err.println("Could not read config file");
      e.printStackTrace();
      return;
    }

    Game game = new Game(config);
    BigDecimal bettingAmount = BigDecimal.valueOf(100);
    Result result = game.play(bettingAmount);
    
    System.out.println(game.getMatrix());
    System.out.println(result);
  }
}
