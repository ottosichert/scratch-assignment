package assignments.scratch.cli;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WonOutput(
  String[][] matrix,
  BigDecimal reward,
  @JsonProperty("applied_winning_combinations") Map<String, List<String>> appliedWinningCombinations,
  @JsonProperty("applied_bonus_symbol") String appliedBonusSymbol
) implements Output {}
