package assignments.scratch;

import org.junit.jupiter.api.Test;

import assignments.scratch.config.Config;
import assignments.scratch.config.Probabilities;
import assignments.scratch.game.Board;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BoardTest {
  @Test
  public void boardDefaultSize() throws IOException {
    Config config = new Config(null, null, Map.of(), new Probabilities(List.of(), new Probabilities.BonusSymbols(Map.of())), Map.of());
    Board classUnderTest = new Board(config);

    assertEquals(3, classUnderTest.getSymbols().length);
    assertEquals(3, classUnderTest.getSymbols()[2].length);
  }
}
