package assignments.scratch.config;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WinCombinations(
  @JsonProperty("reward_multiplier") BigDecimal rewardMultiplier,
  @Nullable Integer count,
  String group,
  @Nullable @JsonProperty("covered_areas") List<List<String>> coveredAreas,
  When when
) {
  public enum When {
    SAME_SYMBOLS("same_symbols"),
    LINEAR_SYMBOLS("linear_symbols");
  
    private final String value;

    private When(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

}
