package assignments.scratch.config;

import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Config(
  @Nullable Integer columns,
  @Nullable Integer rows,
  Map<String, Symbol> symbols,
  Probabilities probabilities,
  @JsonProperty("win_combinations") Map<String, WinCombinations> winCombinations
) {}
