package assignments.scratch.board;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import assignments.scratch.config.Config;
import assignments.scratch.config.Probabilities;

public class Board {
  private int columns;
  private int rows;
  private String[][] symbols;
  private String bonus;

  public Board(Config config) {
    this.columns = config.columns();
    this.rows = config.rows();
    this.symbols = new String[columns][rows];
    this.populate(config);
  }

  public String getBonus() {
    return bonus;
  }

  public String getCell(int column, int row) {
    if (column < 0 || column >= this.columns || row < 0 || row >= this.rows) return null;
    return symbols[column][row];
  }

  private String resolveSymbol(Config config, Map<String, Integer> distribution) {
    List<String> thresholds = distribution.entrySet().stream()
        .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
        .collect(Collectors.toList());

    if (thresholds.isEmpty()) return null;

    Collections.shuffle(thresholds);
    return thresholds.getFirst();
  }

  private String resolveCell(Config config, int column, int row) {
    Optional<Probabilities.StandardSymbols> cell = config.probabilities().standardSymbols().stream()
        .filter(currentCell -> currentCell.column() == column && currentCell.row() == row).findFirst();
    
    if (!cell.isPresent()) return null;

    return this.resolveSymbol(config, cell.get().symbols());
  }

  private void populate(Config config) {
    // fill cells given by distribution on each coordinate
    for (int row = 0; row < this.rows; row++) {
      for (int column = 0; column < this.columns; column++) {
        this.symbols[column][row] = this.resolveCell(config, column, row);
      }
    }

    // clear one field and assign a bonus for the whole board
    Random random = new Random();
    int column = random.nextInt(this.columns);
    int row = random.nextInt(this.rows);
    this.symbols[column][row] = null;
    this.bonus = this.resolveSymbol(config, config.probabilities().bonusSymbols().symbols());
  }
}
