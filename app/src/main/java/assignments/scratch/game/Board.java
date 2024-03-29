package assignments.scratch.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
  private Map<String, List<int[]>> symbolCells;
  private Random prng;

  public Board(Config config, Random prng) {
    this.columns = config.getColumns();
    this.rows = config.getRows();
    this.symbols = new String[columns][rows];
    this.symbolCells = new HashMap<>();
    this.prng = prng;
    this.populate(config);
  }

  public Board(Config config) {
    this(config, new Random());
  }

  public String getBonus() {
    return bonus;
  }

  public String[][] getSymbols() {
    return symbols;
  }

  public Map<String, List<int[]>> getSymbolCells() {
    return symbolCells;
  }

  public String getCell(int column, int row) {
    if (column < 0 || column >= this.columns || row < 0 || row >= this.rows) return null;
    return symbols[column][row];
  }

  public void setCell(int column, int row, String symbol) {
    if (symbol == null) return;
    this.symbolCells.computeIfAbsent(symbol, key -> new ArrayList<>()).add(new int[]{ column, row });
    this.symbols[column][row] = symbol;
  }

  private String resolveSymbol(Config config, Map<String, Integer> distribution) {
    // pick one element based on weighted distribution
    List<String> thresholds = distribution.entrySet().stream()
        .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
        .collect(Collectors.toList());

    if (thresholds.isEmpty()) return null;

    Collections.shuffle(thresholds, this.prng);
    return thresholds.getFirst();
  }

  private String resolveCell(Config config, int column, int row) {
    Optional<Probabilities.StandardSymbols> cell = config.probabilities().standardSymbols().stream()
        .filter(currentCell -> currentCell.column() == column && currentCell.row() == row).findFirst();
    
    if (!cell.isPresent()) return null;

    return this.resolveSymbol(config, cell.get().symbols());
  }

  private void populate(Config config) {
    // assign location for bonus
    int bonusColumn = this.prng.nextInt(this.columns);
    int bonusRow = this.prng.nextInt(this.rows);
    this.bonus = this.resolveSymbol(config, config.probabilities().bonusSymbols().symbols());
    this.setCell(bonusColumn, bonusRow, this.bonus);

    // fill cells given by distribution on each coordinate
    for (int row = 0; row < this.rows; row++) {
      for (int column = 0; column < this.columns; column++) {
        if (this.bonus != null && column == bonusColumn && row == bonusRow) continue;

        String symbol = this.resolveCell(config, column, row);
        this.setCell(column, row, symbol);
      }
    }
  }
}
