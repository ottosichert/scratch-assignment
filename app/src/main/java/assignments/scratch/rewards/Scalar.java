package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.HashMap;

import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public record Scalar(BigDecimal value) implements Reward {
  @Override
  public Result calculate(Board board) {
    return new Result(new HashMap<>(), null, value);
  }
}
