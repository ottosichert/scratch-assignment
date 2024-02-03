package assignments.scratch.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Probabilities(
  @JsonProperty("standard_symbols") List<StandardSymbols> standardSymbols,
  @JsonProperty("bonus_symbols") BonusSymbols bonusSymbols
) {
  public record StandardSymbols(int column, int row, Map<String, Integer> symbols) {}

  public record BonusSymbols(Map<String, Integer> symbols) {}
}
