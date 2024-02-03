package assignments.scratch.config;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Config(
  int columns,
  int rows,
  Map<String, Symbol> symbols,
  Probabilities probabilities,
  @JsonProperty("win_combinations") Map<String, WinCombinations> winCombinations
) {}
