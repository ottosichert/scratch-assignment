package assignments.scratch.game;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record Result(Map<String, List<String>> rewards, String bonus, BigDecimal calculatedAmount) {}
