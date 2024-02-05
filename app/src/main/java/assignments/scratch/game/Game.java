package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.List;

import assignments.scratch.config.Config;
import assignments.scratch.config.Symbol;
import assignments.scratch.rewards.Product;
import assignments.scratch.rewards.Reward;
import assignments.scratch.rewards.Scalar;
import assignments.scratch.rewards.Sum;

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
    Symbol bonus = this.getSymbol(symbol);
    if (bonus != null && bonus.type() == Symbol.Type.BONUS) {
      if (bonus.impact() == Symbol.Impact.EXTRA_BONUS) {
        this.reward = new Sum(List.of(this.reward, new Scalar(bonus.extra())), symbol);
      } else if (bonus.impact() == Symbol.Impact.MULTIPLY_REWARD) {
        this.reward = new Product(List.of(this.reward, new Scalar(bonus.rewardMultiplier())), symbol);
      }
    }

    // this.rewards = config.winCombinations().entrySet().stream().map(entry -> new Scalar(entry.getValue().rewardMultiplier())).toList();
  }

  public Result play() {
    return this.reward.calculate(this.board);
  }

  private Symbol getSymbol(String symbol) {
    return this.config.symbols().get(symbol);
  }
}
