package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.List;

import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Product(List<Reward> rewards, String symbol) implements Reward {
  @Override
  public Result calculate(Board board) {
    BigDecimal total = BigDecimal.ONE;
    for (Reward reward : this.rewards) {
      total = total.multiply(reward.calculate(board).calculatedAmount());
    }
    return new Result(null, this.symbol, total);
  }
}
