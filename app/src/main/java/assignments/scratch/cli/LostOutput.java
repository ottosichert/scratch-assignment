package assignments.scratch.cli;

import java.math.BigDecimal;

public record LostOutput(
  String[][] matrix,
  BigDecimal reward
) implements Output {}
