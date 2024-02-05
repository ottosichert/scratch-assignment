package assignments.scratch.rewards;

import assignments.scratch.game.Board;
import assignments.scratch.game.Result;

public interface Reward {
  public Result calculate(Board board);
}
