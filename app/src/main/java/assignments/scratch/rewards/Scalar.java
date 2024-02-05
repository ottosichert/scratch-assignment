package assignments.scratch.rewards;

import java.math.BigDecimal;

import assignments.scratch.game.Board;

public record Scalar(BigDecimal value) implements Reward {
  public BigDecimal calculate(Board board) {
    return this.value;
  }
}
