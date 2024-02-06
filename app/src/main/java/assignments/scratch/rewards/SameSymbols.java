package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignments.scratch.config.Config;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record SameSymbols(Config config, String name, int count, BigDecimal rewardMultiplier) implements Reward {
  @Override
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount) {
    Result zero = new Result(new HashMap<>(), null, BigDecimal.ZERO);

    // only valid to be called with one symbol
    if (symbols.size() != 1) return zero;

    String symbol = symbols.getFirst();
    List<int[]> cells = board.getSymbolCells().get(symbol);

    // only succeed on exact match
    if (cells == null || count != cells.size()) return zero;

    return new Result(Map.of(symbol, List.of(this.name)), null, this.rewardMultiplier);
  }
}
