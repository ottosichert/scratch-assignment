package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Result(Map<String, List<String>> matches, String bonus, BigDecimal calculatedAmount) {
  public Result merge(Result other) {
    // deep merge matches and override the rest
    Map<String, List<String>> mergedRewards = new HashMap<>();

    this.matches.forEach((symbol, symbolRewards) -> mergedRewards.put(symbol, new ArrayList<>(symbolRewards)));
    other.matches.forEach((symbol, symbolRewards) -> mergedRewards.computeIfAbsent(symbol, key -> new ArrayList<>()).addAll(symbolRewards));
    
    return new Result(mergedRewards, other.bonus, other.calculatedAmount);
  }
}
