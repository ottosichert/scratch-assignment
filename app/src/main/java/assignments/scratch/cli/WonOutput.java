package assignments.scratch.cli;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record WonOutput(
  String[][] matrix,
  BigDecimal reward,
  Map<String, List<String>> appliedWinningCombinations,
  String appliedBonusSymbol
) implements Output {}
