package assignments.scratch.config;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Symbol(
  @JsonProperty("reward_multiplier") @Nullable BigDecimal rewardMultiplier,
  Type type,
  @Nullable BigDecimal extra,
  @Nullable Impact impact
) {
  enum Type {
    STANDARD("standard"),
    BONUS("bonus");
  
    private final String value;

    private Type(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
  
  enum Impact {
    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus"),
    MISS("miss");
  
    private final String value;

    private Impact(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
  
}
