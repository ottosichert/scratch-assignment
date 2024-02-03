/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package assignments.scratch;

import java.util.Optional;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

import assignments.scratch.config.Config;

public class App {
  public String getSchema() {
    SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
        SchemaVersion.DRAFT_2020_12,
        OptionPreset.PLAIN_JSON).with(
            Option.FLATTENED_ENUMS_FROM_TOSTRING,
            Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES,
            Option.INLINE_ALL_SCHEMAS,
            Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT);

    // required by default unless Optional<>
    configBuilder.forFields()
        .withRequiredCheck(field -> field.getAnnotationConsideringFieldAndGetter(Nullable.class) == null);

    //
    configBuilder.forFields()
        .withPropertyNameOverrideResolver(field -> Optional
            .ofNullable(field.getAnnotationConsideringFieldAndGetter(JsonProperty.class))
            .map(JsonProperty::value).orElse(null));

    SchemaGeneratorConfig config = configBuilder.build();
    SchemaGenerator generator = new SchemaGenerator(config);
    JsonNode jsonSchema = generator.generateSchema(Config.class);

    return jsonSchema.toPrettyString();

  }

  public static void main(String[] args) {
    System.out.println(new App().getSchema());
  }
}
