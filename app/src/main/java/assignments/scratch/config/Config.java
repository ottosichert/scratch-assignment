package assignments.scratch.config;

import java.util.Map;

public record Config(int columns, int rows, Map<String, Symbol> symbols) {
  
}
