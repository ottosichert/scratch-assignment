package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import assignments.scratch.config.Config;
import assignments.scratch.config.Symbol;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Bonus(Config config, Reward reward, String symbol, Symbol bonus) implements Reward {
  @Override
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount) {
    Result total = this.reward().calculate(board, symbols, bettingAmount);

    // don't apply any bonus if game is lost
    if (total.calculatedAmount().equals(BigDecimal.ZERO)) {
      return new Result(new HashMap<>(), null, BigDecimal.ZERO);
    }

    // apply known bonuses and ignore MISS
    if (bonus.impact() == Symbol.Impact.EXTRA_BONUS) {
      return total.merge(new Result(new HashMap<>(), symbol, total.calculatedAmount().add(bonus.extra())));
    } else if (bonus.impact() == Symbol.Impact.MULTIPLY_REWARD) {
      return total.merge(new Result(new HashMap<>(), symbol, total.calculatedAmount().multiply(bonus.rewardMultiplier())));
    }

    return total;
  }
}
