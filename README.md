# Scratch assignment

This repository contains an implementation of a probability-based game "Scratch". It's built on JDK 21 and uses Gradle.

The instructions can be found in [ASSIGNMENT.md](ASSIGNMENT.md).

## Usage

First, build the project:

```sh
$ ./gradlew build
```

Then, run the JAR file:

```sh
$ java -jar app/build/libs/app.jar
Scratch CLI
    Calculate total reward based on probabilities and betting amount

Usage:
    java -jar scratch.jar --config config.json --betting-amount 100

--config <path>
    Relative path to JSON config file

--betting-amount <decimal>
    Initial betting amount

--help
    Prints this dialog
```

For example with the configuration from the assignment:

```json
$ java -jar app/build/libs/app.jar --config app/src/test/resources/config.json --betting-amount 100
{
  "applied_winning_combinations" : {
    "E" : [ "same_symbols_diagonally_left_to_right", "same_symbol_5_times" ],
    "F" : [ "same_symbol_3_times" ]
  },
  "applied_bonus_symbol" : null,
  "matrix" : [ [ "E", "E", "F" ], [ "F", "E", "F" ], [ "E", "MISS", "E" ] ],
  "reward" : 3150.0
}
```

## Configuration

The board is set up with symbols, probabilities, rewards and bonuses. The schema as defined in the assignment is implemented using POJOs that are converted to a JSON schema (see [app/src/test/resources/schema.json](app/src/test/resources/schema.json)).

The winning logic is represented in the `Reward` interface, which is implemented by all types including symbols, win combinations and bonuses.

Inside the `Game` class, a tree of `Rewards` is generated which can look like this:

```jsx
<Bonus>
  <Win>
    <Group name="same_symbols">
      <SameSymbols name="same_symbol_3_times" count={3} rewardMultiplier={1} />
      <SameSymbols name="same_symbol_4_times" count={4} rewardMultiplier={1.5} />
      {/* ... */}
    </Group>
    <Group name="horizontally_linear_symbols">
      <LinearSymbols name="same_symbols_horizontally" rewardMultiplier={2} covered_Areas={[
        ["0:0", "0:1", "0:2"],
        ["1:0", "1:1", "1:1"],
        ["2:0", "2:1", "2:2"]
      ]} />
      {/* ... */}
    </Group>
  </Win>
</Bonus>
```

At the end, this tree is being evaluated by calling the `calculate(board, symbols, bettingAmount)` method on the root `Reward` instance.

## Assumptions

Various statements can be interpreted in different ways, so this is a list of decisions taken during the first implementation. Later revisions can adapt or revert certain assumptions.

- There can only be one bonus on the board
- The matrix, winning combinations and bonuses have at least one element
- Linear symbols can take any shape and don't have to be continuous
- Cell inputs are in correct form ("x:y")
- Betting amount can be any decimal value
