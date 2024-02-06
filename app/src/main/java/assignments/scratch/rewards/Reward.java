package assignments.scratch.rewards;

import java.math.BigDecimal;
import java.util.List;

import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public interface Reward {
  public Result calculate(Board board, List<String> symbols, BigDecimal bettingAmount);
}
