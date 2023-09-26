package no.uib.inf102.wordle.controller.AI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleWord;

/**
 * This class, when its main method is run, shows the performance of the
 * strategies listed in it.
 */
public class AIPerformance {

    public static final int N_GAMES = 200;
    public static final int MAX_N_GUESSES = 20;
    static long seed = 14212l;

    public static void main(String[] args) {
        // Strategies
        List<IStrategy> strategies = new ArrayList<>();
        strategies.add(new RandomStrategy());
        strategies.add(new EliminateStrategy());
        strategies.add(new FrequencyStrategy());
        strategies.add(new MyStrategy());

        Map<IStrategy, AIStatistics> stats = new HashMap<>();
        System.out.println("\nStrategies running...");
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        for (IStrategy strategy : strategies) {
            stats.put(strategy, runWordleGames(strategy));
        }

        System.out.println(
                "---------------------------------------------------------------------------------------------\n");
        System.out.printf("After %s Wordle games the strategies got the following"
                + " average guessing counts:%n", N_GAMES);
        System.out.println(
                "---------------------------------------------------------------------------------------------");
        for (IStrategy strategy : strategies) {
            AIStatistics.printResult(stats.get(strategy));
        }
        for (IStrategy strategy : strategies) {
            AIStatistics.printHistogram(stats.get(strategy));
        }
        System.out.println();
    }

    /**
     * Plays Wordle <code>nGames</code> times with the given strategy
     * and returns the total number of guesses made by that strategy.
     * 
     * @param strategy the strategy playing the <code>nGames</code> games.
     * @return the total number of guesses made across <code>nGames</code> games.
     */
    public static AIStatistics runWordleGames(IStrategy strategy) {
        String strategyName = strategy.getClass().getSimpleName();
        AIStatistics stats = new AIStatistics(strategyName);
        //set a seed so that all strategies are given the same set of words
        Random rnd = new Random(seed);

        for (int i = 0; i < N_GAMES; i++) {
            try {
                WordleAnswer answer = new WordleAnswer(rnd);
                int guesses = runWordleGame(strategy, answer);
                stats.addGame(guesses);
                printProgress(strategyName, i + 1, true);
            } catch (IllegalStateException e) {
                printProgress(strategyName, i + 1, false);
                stats.failed();
            }
            strategy.reset();
        }
        System.out.println();
        return stats;
    }

    /**
     * The given strategy guesses a word until the correct is found
     * 
     * @param strategy the given strategy guessing the word
     * @param answer   the answer of this game
     * @return total number of guesses
     */
    public static int runWordleGame(IStrategy strategy, WordleAnswer answer) {
        String guess = strategy.makeGuess(null);
        WordleWord feedback = answer.makeGuess(guess);

        int guessCount = 1;
        while (!feedback.allMatch()) {
            guess = strategy.makeGuess(feedback);
            feedback = answer.makeGuess(guess);
            guessCount++;

            if (guessCount > MAX_N_GUESSES) {
                throw new IllegalStateException("Too many guesses.");
            }
        }

        return guessCount;
    }

    /**
     * Prints a progress bar for the given strategy showing how many games have been
     * completed.
     * 
     * @param strategyName The given strategy
     * @param game         The current game
     * @param gameWon      If the game was won
     */
    public static void printProgress(String strategyName, int game, boolean gameWon) {
        float length = 50f;
        float progress = ((float) game / N_GAMES) * length;
        String progressString = "=".repeat((int) progress);
        System.out.printf("\b\r%-25s [%-50s] (%4s /%5s) | Latest game: %s", strategyName + ":", progressString, game,
                N_GAMES,
                gameWon ? "won" : "lost");
    }
}
