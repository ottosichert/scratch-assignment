package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import assignments.scratch.config.Config;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Group(Config config, String name, List<Reward> rewards) implements Reward {
  @Override
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount) {
    // multipliy betting amount by all matching groups
    Result result = new Result(new HashMap<>(), null, bettingAmount);

    for (Reward reward : this.rewards) {
      Result next = reward.calculate(board, symbols, bettingAmount);

      if (!next.matches().isEmpty()) {
        result = result.merge(new Result(next.matches(), null, next.calculatedAmount().multiply(result.calculatedAmount())));
      }
    }

    // only return if any rewards have matched
    if (result.matches().isEmpty()) {
      return new Result(new HashMap<>(), null, BigDecimal.ZERO);
    }

    return result;
  }
}
