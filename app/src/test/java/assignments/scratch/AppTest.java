package assignments.scratch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppTest {
  // capture stdout and stderr for assertions
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void appPrintsHelp() throws IOException {
    String[] args = new String[]{ "--help" };
    String help = Files.readString(Paths.get("src/test/resources/help.txt"));

    App.main(args);

    assertEquals(help, outContent.toString());
  }

  @Test
  public void appValidatesCount() throws IOException {
    String[] args = new String[]{ "this", "is", "invalid" };
    String help = Files.readString(Paths.get("src/test/resources/help.txt"));

    App.main(args);

    assertEquals("Invalid number of arguments (got 3, expected 4)", errContent.toString().stripTrailing());
    assertEquals(help, outContent.toString());
  }

  @Test
  public void appValidatesOptions() throws IOException {
    String[] args = new String[]{ "--config", "this/does/not/exist.json", "--betting-amount", "1.2.3" };
    String help = Files.readString(Paths.get("src/test/resources/help.txt"));

    App.main(args);

    assertEquals("Failed to parse options", errContent.toString().stripTrailing());
    assertEquals(help, outContent.toString());
  }
}
