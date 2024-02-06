package assignments.scratch;

import org.junit.jupiter.api.Test;

import assignments.scratch.cli.CLI;
import assignments.scratch.cli.Input;
import assignments.scratch.config.Symbol;
import assignments.scratch.config.WinCombinations;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class CliTest {
  @Test
  public void parseInvalid() {
    String[] args = new String[]{ "--config", "src/test/resources/invalid.json", "--betting-amount", "100" };
    CLI cli = new CLI();
    Input input = cli.parse(args);

    assertEquals(null, input);
  }

  @Test
  public void parseExample() {
    String[] args = new String[]{ "--config", "src/test/resources/config.json", "--betting-amount", "100" };
    CLI cli = new CLI();
    Input input = cli.parse(args);

    assertEquals(new BigDecimal(100), input.bettingAmount());
    assertEquals(3, input.config().columns());
    assertEquals(3, input.config().rows());
    assertEquals(11, input.config().symbols().size());
    assertEquals(new BigDecimal(50), input.config().symbols().get("A").rewardMultiplier());
    assertEquals(Symbol.Impact.EXTRA_BONUS, input.config().symbols().get("+1000").impact());
    assertEquals(new BigDecimal(1000), input.config().symbols().get("+1000").extra());
    assertEquals(9, input.config().probabilities().standardSymbols().size());
    assertEquals(1, input.config().probabilities().standardSymbols().get(5).column());
    assertEquals(2, input.config().probabilities().standardSymbols().get(5).row());
    assertEquals(3, input.config().probabilities().standardSymbols().get(5).symbols().get("C"));
    assertEquals(5, input.config().probabilities().bonusSymbols().symbols().get("MISS"));
    assertEquals(11, input.config().winCombinations().size());
    assertEquals(WinCombinations.When.SAME_SYMBOLS, input.config().winCombinations().get("same_symbol_5_times").when());
    assertEquals("same_symbols", input.config().winCombinations().get("same_symbol_5_times").group());
    assertEquals(5, input.config().winCombinations().get("same_symbol_5_times").count());
    assertEquals(WinCombinations.When.LINEAR_SYMBOLS, input.config().winCombinations().get("same_symbols_diagonally_left_to_right").when());
    assertEquals(new BigDecimal(5), input.config().winCombinations().get("same_symbols_diagonally_left_to_right").rewardMultiplier());
    assertEquals(1, input.config().winCombinations().get("same_symbols_diagonally_left_to_right").coveredAreas().size());
    assertEquals("2:2", input.config().winCombinations().get("same_symbols_diagonally_left_to_right").coveredAreas().getFirst().getLast());
  }
}
