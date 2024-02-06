package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignments.scratch.config.Config;
import assignments.scratch.config.Symbol;
import assignments.scratch.config.WinCombinations;
import assignments.scratch.rewards.Bonus;
import assignments.scratch.rewards.Group;
import assignments.scratch.rewards.Reward;
import assignments.scratch.rewards.SameSymbols;
import assignments.scratch.rewards.Win;

public class Game {
  private Config config;
  private Board board;
  private Reward reward;
  private Map<String, Reward> groups;

  public Game(Config config) {
    this.config = config;
    this.groups = this.prepareGroups(config);
    this.board = new Board(config);
    this.reward = new Win(config, this.groups);

    this.wrapBonus();
  }

  public Result play(BigDecimal bettingAmount) {
    // calculate rewards for all standard symbols
    List<String> symbols = config.symbols().entrySet().stream()
        .filter(entry -> entry.getValue().type() == Symbol.Type.STANDARD)
        .map(entry -> entry.getKey())
        .toList();

    return this.reward.calculate(this.board, symbols, bettingAmount);
  }

  public List<List<String>> getMatrix() {
    List<List<String>> rows = new ArrayList<>();

    for (int row = 0; row < this.config.rows(); row++) {
      List<String> columns = new ArrayList<>();
      rows.add(columns);

      for (int column = 0; column < this.config.columns(); column++) {
        columns.add(this.board.getCell(column, row));
      }
    }

    return rows;
  }

  private void wrapBonus() {
    // ensure bonus is being considered for calculations if applicable
    String symbol = board.getBonus();
    Symbol bonus = this.config.symbols().get(symbol);
    if (bonus != null && bonus.type() == Symbol.Type.BONUS) {
      this.reward = new Bonus(this.config, this.reward, symbol, bonus);
    }
  }

  private Map<String, Reward> prepareGroups(Config config) {
    // sort into groups
    Map<String, List<Reward>> groupRewards = new HashMap<>();

    for (Map.Entry<String, WinCombinations> entry : config.winCombinations().entrySet()) {
      WinCombinations combination = entry.getValue();
      List<Reward> rewards = groupRewards.computeIfAbsent(combination.group(), key -> new ArrayList<>());

      if (combination.when() == WinCombinations.When.SAME_SYMBOLS) {
        rewards.add(new SameSymbols(this.config, entry.getKey(), combination.count(), combination.rewardMultiplier()));
      }
    }

    // create grouped rewards
    Map<String, Reward> groups = new HashMap<>();

    for (Map.Entry<String, List<Reward>> entry : groupRewards.entrySet()) {
      groups.put(entry.getKey(), new Group(this.config, entry.getKey(), entry.getValue()));
    }

    return groups;
  }
}
