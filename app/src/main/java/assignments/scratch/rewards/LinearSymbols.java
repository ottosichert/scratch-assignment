package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignments.scratch.config.Config;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record LinearSymbols(Config config, String name, List<List<String>> coveredAreas, BigDecimal rewardMultiplier) implements Reward {
  @Override
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount) {
    Result zero = new Result(new HashMap<>(), null, BigDecimal.ZERO);

    // only valid to be called with one symbol
    if (symbols.size() != 1) return zero;

    String symbol = symbols.getFirst();
    
    // validate if any of the cell combinations is matching
    for (List<String> combinations : coveredAreas) {
      boolean matched = true;

      for (String cell : combinations) {
        String[] coordinates = cell.split(":");
        if (board.getCell(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])) != symbol) {
          matched = false;
          break;
        }
      }

      if (matched) {
        return new Result(Map.of(symbol, List.of(this.name)), null, this.rewardMultiplier);
      }
    }
    
    return zero;
  }
}
