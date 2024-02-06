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

    // for each symbol, multiply all matching groups together with bettingAmount and get total sum of all symbols at the end
    for (String symbol : symbols) {
      BigDecimal reward = this.config.symbols().get(symbol).rewardMultiplier();
      Result groupProduct = new Result(new HashMap<>(), null, bettingAmount.multiply(reward));
      boolean matched = false;

      for (Map.Entry<String, Reward> group : this.groups.entrySet()) {
        Result groupResult = group.getValue().calculate(board, List.of(symbol), bettingAmount);

        if (!groupResult.matches().isEmpty()) {
          matched = true;
          groupProduct = groupProduct.merge(
            new Result(groupResult.matches(), null, groupProduct.calculatedAmount().multiply(groupResult.calculatedAmount())));
        }
      }

      if (matched) {
        result = result.merge(
          new Result(groupProduct.matches(), null, result.calculatedAmount().add(groupProduct.calculatedAmount())));
      }
    }
  
    return result;
  }
}
