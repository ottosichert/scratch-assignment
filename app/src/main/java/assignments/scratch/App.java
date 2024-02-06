package assignments.scratch;

import assignments.scratch.cli.CLI;
import assignments.scratch.cli.Input;
import assignments.scratch.cli.Output;
import assignments.scratch.game.Game;

public class App {
  public static void main(String[] args) {
    CLI cli = new CLI();
    Input input = cli.parse(args);

    if (input == null) {
      cli.help();
      return;
    }

    Game game = new Game(input.config());
    Output output = game.play(input.bettingAmount());

    cli.print(output);
  }
}
