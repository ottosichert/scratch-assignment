package assignments.scratch;

import org.junit.jupiter.api.Test;

import assignments.scratch.cli.Schema;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SchemaTest {
  @Test
  public void schemaMatchesSnapshot() throws IOException {
    Schema classUnderTest = new Schema();

    String schema = Files.readString(Paths.get("src/test/resources/schema.json"));
    assertEquals(classUnderTest.getSchema(), schema);
  }
}
