package assignments.scratch;

import org.junit.jupiter.api.Test;

import assignments.scratch.config.Config;
import assignments.scratch.config.Probabilities;
import assignments.scratch.game.Board;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BoardTest {
  @Test
  public void boardDefaultSize() throws IOException {
    Config config = new Config(null, null, Map.of(), new Probabilities(List.of(), new Probabilities.BonusSymbols(Map.of())), Map.of());
    Board classUnderTest = new Board(config);

    assertEquals(3, classUnderTest.getSymbols().length);
    assertEquals(3, classUnderTest.getSymbols()[2].length);
  }

  @Test
  public void emptyBoard() {
    Config config = new Config(2, 2, Map.of(), new Probabilities(List.of(), new Probabilities.BonusSymbols(Map.of())), Map.of());
    Board classUnderTest = new Board(config);

    assertEquals(null, classUnderTest.getCell(0, 0));
    assertEquals(null, classUnderTest.getCell(1, 0));
    assertEquals(null, classUnderTest.getCell(0, 1));
    assertEquals(null, classUnderTest.getCell(1, 1));
  }

  @Test
  public void simpleBoard() {
    List<Probabilities.StandardSymbols> symbols = List.of(
      new Probabilities.StandardSymbols(0, 0, Map.of("1", 1)),
      new Probabilities.StandardSymbols(1, 0, Map.of("2", 1)),
      new Probabilities.StandardSymbols(0, 1, Map.of("3", 1)),
      new Probabilities.StandardSymbols(1, 1, Map.of("4", 1))
    );
    Config config = new Config(2, 2, Map.of(), new Probabilities(symbols, new Probabilities.BonusSymbols(Map.of())), Map.of());
    Board classUnderTest = new Board(config);

    assertEquals("1", classUnderTest.getCell(0, 0));
    assertEquals("2", classUnderTest.getCell(1, 0));
    assertEquals("3", classUnderTest.getCell(0, 1));
    assertEquals("4", classUnderTest.getCell(1, 1));
  }

  @Test
  public void omittedBoard() {
    List<Probabilities.StandardSymbols> symbols = List.of(
      new Probabilities.StandardSymbols(0, 0, Map.of("1", 1)),
      new Probabilities.StandardSymbols(1, 1, Map.of("4", 1))
    );
    Config config = new Config(2, 2, Map.of(), new Probabilities(symbols, new Probabilities.BonusSymbols(Map.of())), Map.of());
    Board classUnderTest = new Board(config);

    assertEquals("1", classUnderTest.getCell(0, 0));
    assertEquals(null, classUnderTest.getCell(1, 0));
    assertEquals(null, classUnderTest.getCell(0, 1));
    assertEquals("4", classUnderTest.getCell(1, 1));
  }

  @Test
  public void bonusBoard() {
    List<Probabilities.StandardSymbols> symbols = List.of(
      new Probabilities.StandardSymbols(0, 0, Map.of("1", 1)),
      new Probabilities.StandardSymbols(1, 0, Map.of("2", 1)),
      new Probabilities.StandardSymbols(0, 1, Map.of("3", 1)),
      new Probabilities.StandardSymbols(1, 1, Map.of("4", 1))
    );
    Map<String, Integer> bonus = Map.of("BONUS", 1);

    Config config = new Config(2, 2, Map.of(), new Probabilities(symbols, new Probabilities.BonusSymbols(bonus)), Map.of());
    Board classUnderTest = new Board(config, new Random(1337));

    assertEquals("1", classUnderTest.getCell(0, 0));
    assertEquals("BONUS", classUnderTest.getCell(1, 0));
    assertEquals("3", classUnderTest.getCell(0, 1));
    assertEquals("4", classUnderTest.getCell(1, 1));
  }
}
