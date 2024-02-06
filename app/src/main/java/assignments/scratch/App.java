package assignments.scratch;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

import assignments.scratch.config.Config;
import assignments.scratch.game.Game;
import assignments.scratch.game.Result;

public class App {
  public String getSchema() {
    SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
        SchemaVersion.DRAFT_2020_12,
        OptionPreset.PLAIN_JSON).with(
            Option.FLATTENED_ENUMS_FROM_TOSTRING,
            Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES,
            Option.INLINE_ALL_SCHEMAS,
            Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT);

    // set fields as required by default unless Optional<>
    configBuilder.forFields()
        .withRequiredCheck(field -> field.getAnnotationConsideringFieldAndGetter(Nullable.class) == null);

    // considered renamed fields in annotations
    configBuilder.forFields()
        .withPropertyNameOverrideResolver(field -> Optional
            .ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonProperty.class))
            .map(JsonProperty::value).orElse(null));

    SchemaGeneratorConfig config = configBuilder.build();
    SchemaGenerator generator = new SchemaGenerator(config);
    JsonNode jsonSchema = generator.generateSchema(Config.class);

    return jsonSchema.toPrettyString();
  }

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
