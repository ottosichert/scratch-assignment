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
    // multiply all multipliers of all matching groups
    for (Reward reward : this.rewards) {
      Result result = reward.calculate(board, symbols, bettingAmount);

      // only match one element per group
      if (!result.matches().isEmpty()) {
        return result;
      }
    }

    return new Result(new HashMap<>(), null, BigDecimal.ZERO);
  }
}
