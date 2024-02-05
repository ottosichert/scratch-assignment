package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Result(Map<String, List<String>> rewards, String bonus, BigDecimal calculatedAmount) {
  public Result merge(Result other) {
    Map<String, List<String>> mergedRewards = new HashMap<>();

    this.rewards.forEach((symbol, symbolRewards) -> mergedRewards.put(symbol, List.copyOf(symbolRewards)));
    other.rewards.forEach((symbol, symbolRewards) -> mergedRewards.computeIfAbsent(symbol, key -> new ArrayList<>()).addAll(symbolRewards));
    
    return new Result(mergedRewards, other.bonus, other.calculatedAmount);
  }
}
