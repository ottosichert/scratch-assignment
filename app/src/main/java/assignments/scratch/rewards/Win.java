package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignments.scratch.config.Config;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Win(Config config, Map<String, Reward> groups) implements Reward {
  @Override
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount) {
    Result result = new Result(new HashMap<>(), null, BigDecimal.ZERO);

    // calculate each symbol for each group
    for (String symbol : symbols) {
      for (Map.Entry<String, Reward> group : this.groups.entrySet()) {
        Result groupResult = group.getValue().calculate(board, List.of(symbol), bettingAmount);

        result = result.merge(
          new Result(groupResult.matches(), null, result.calculatedAmount().add(groupResult.calculatedAmount())));
      }
    }
  
    return result;
  }
}
