package assignments.scratch;

import org.junit.jupiter.api.Test;

import assignments.scratch.cli.CLI;
import assignments.scratch.cli.Input;
import assignments.scratch.cli.LostOutput;
import assignments.scratch.cli.WonOutput;
import assignments.scratch.game.Game;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameTest {
  @Test
  public void gameLost() {
    String[] args = new String[]{ "--config", "src/test/resources/config.json", "--betting-amount", "100" };
    CLI cli = new CLI();
    Input input = cli.parse(args);
    Game game = new Game(input.config(), new Random(9001));
    LostOutput lost = ((LostOutput) game.play(input.bettingAmount()));

    assertEquals(BigDecimal.ZERO, lost.reward());
    assertArrayEquals(new String[][]{ { "E", "E", "F" }, { "C", "B", "D" }, { "B", "MISS", "F" } }, lost.matrix());
  }

  @Test
  public void gameWon() {
    String[] args = new String[]{ "--config", "src/test/resources/config.json", "--betting-amount", "100" };
    CLI cli = new CLI();
    Input input = cli.parse(args);
    Game game = new Game(input.config(), new Random(9002));
    WonOutput won = ((WonOutput) game.play(input.bettingAmount()));

    assertEquals(new BigDecimal(1300), won.reward());
    assertEquals("+500", won.appliedBonusSymbol());
    assertEquals(Map.of("D", List.of("same_symbol_3_times"), "E", List.of("same_symbol_3_times")), won.appliedWinningCombinations());
  }

  @Test
  public void gameWonGroup() {
    String[] args = new String[]{ "--config", "src/test/resources/config.json", "--betting-amount", "100" };
    CLI cli = new CLI();
    Input input = cli.parse(args);
    Game game = new Game(input.config(), new Random(9009));
    WonOutput won = ((WonOutput) game.play(input.bettingAmount()));

    assertEquals(0, new BigDecimal(3750).compareTo(won.reward()));
    assertArrayEquals(new String[][]{ { "D", "F", "F" }, { "E", "D", "MISS" }, { "B", "D", "D" } }, won.matrix());
    assertEquals(null, won.appliedBonusSymbol());
    assertEquals(Map.of("D", List.of("same_symbols_diagonally_left_to_right", "same_symbol_4_times")), won.appliedWinningCombinations());
  }
}
