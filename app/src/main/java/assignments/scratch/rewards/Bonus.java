package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;

import assignments.scratch.config.Symbol;
import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Bonus(Reward reward, String symbol, Symbol bonus) implements Reward {
  @Override
  public Result calculate(Board board) {
    Result total = this.reward().calculate(board);

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
