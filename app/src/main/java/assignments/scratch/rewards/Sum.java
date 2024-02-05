package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.List;

import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Sum(List<Reward> rewards) implements Reward {
  @Override
  public Result calculate(Board board) {
    BigDecimal total = BigDecimal.ZERO;
    for (Reward reward : this.rewards) {
      total = total.add(reward.calculate(board).calculatedAmount());
    }
    return new Result(null, null, total);
  }
  
}
