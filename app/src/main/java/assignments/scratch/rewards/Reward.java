package assignments.scratch.rewards;

import java.math.BigDecimal;

import assignments.scratch.game.Board;

public interface Reward {
  public BigDecimal calculate(Board board);
}
