package assignments.scratch.config;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Symbol(@JsonProperty("reward_multiplier") BigDecimal rewardMultiplier, Type type) {
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
  
}
