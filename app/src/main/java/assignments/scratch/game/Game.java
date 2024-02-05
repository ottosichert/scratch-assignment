package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import assignments.scratch.config.Config;
import assignments.scratch.rewards.Reward;
import assignments.scratch.rewards.Scalar;

public class Game {
  private Board board;
  private List<Reward> rewards;

  public Game(Config config, BigDecimal bettingAmount) {
    this.board = new Board(config);
    this.rewards = new ArrayList<>();
    this.rewards.add(new Scalar(bettingAmount));

    // this.rewards = config.winCombinations().entrySet().stream().map(entry -> new Scalar(entry.getValue().rewardMultiplier())).toList();
  }

  public BigDecimal play() {
    return this.rewards.get(0).calculate(this.board);
  }
}
