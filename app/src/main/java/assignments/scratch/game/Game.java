package assignments.scratch.game;

import java.math.BigDecimal;

import assignments.scratch.config.Config;
import assignments.scratch.config.Symbol;
import assignments.scratch.rewards.Bonus;
import assignments.scratch.rewards.Reward;
import assignments.scratch.rewards.Scalar;

public class Game {
  private Config config;
  private Board board;
  private Reward reward;

  public Game(Config config, BigDecimal bettingAmount) {
    this.config = config;
    this.board = new Board(config);
    this.reward = new Scalar(bettingAmount);

    // apply bonus at the end
    String symbol = board.getBonus();
    Symbol bonus = this.config.symbols().get(symbol);
    if (bonus != null && bonus.type() == Symbol.Type.BONUS) {
      this.reward = new Bonus(this.reward, symbol, bonus);
    }
  }

  public Result play() {
    return this.reward.calculate(this.board);
  }
}
