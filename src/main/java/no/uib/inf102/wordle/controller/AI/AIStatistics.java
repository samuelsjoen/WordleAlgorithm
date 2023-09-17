package no.uib.inf102.wordle.controller.AI;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This class records various statistics of a strategy used for guessing Wordle
 * answers.
 */
public class AIStatistics {

	private final String strategyName;

	private int numGames = 0;
	private int totalGuesses;
	private int wins;
	private int maxGuesses;
	private int failures;
	public Map<Integer,Integer> gamesCompletedIn = new HashMap<>();

	/**
	 * Create a statistics object for an AI Strategy.
	 * 
	 * @param strategyName the name of the strategy
	 */
	public AIStatistics(String strategyName) {
		this.strategyName = strategyName;
	}

	/**
	 * Records a game with the given guesses to the total guess statistic. If the
	 * total guesses made were less than 7, count the game as a win. Otherwise,
	 * count as loss.
	 * 
	 * @param guesses
	 */
	public void addGame(int guesses) {
		numGames++;
		totalGuesses += guesses;
		increase(gamesCompletedIn,guesses);
		
		if (guesses <= 6)
			wins++;
		maxGuesses = Math.max(maxGuesses, guesses);
	}

	/**
	 * Increases the value of a given key by 1
	 * If key is not present in map, the value of this key is sat to 1
	 * @param map a map from anything to an Integer
	 * @param key 
	 */
	public static <K> void increase(Map<K, Integer> map, K key) {
		map.put(key, map.getOrDefault(key,0)+1);
	}

	/**
	 * Get the average guess count of the wordle games recorded in this object.
	 * 
	 * @return
	 */
	public double getAverage() {
		if (failures == 0)
			return totalGuesses / (double) numGames;
		else
			return Double.NaN;
	}

	/**
	 * Records a game as a failure.
	 */
	public void failed() {
		failures++;
		numGames++;
	}

	/**
	 * 
	 * @return the total number of games played
	 */
	public int getNumGames() {
		return numGames;
	}

	/**
	 * 
	 * @return the total guesses made
	 */
	public int getTotalGuesses() {
		return totalGuesses;
	}

	/**
	 * 
	 * @return the total wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * 
	 * @return the maximum number of guesses made in one game before winning
	 */
	public int getMaxGuesses() {
		return maxGuesses;
	}

	/**
	 * 
	 * @return the total number of failures
	 */
	public int getFailures() {
		return failures;
	}
	
	public Map<Integer, Integer> getDistribution() {
		return gamesCompletedIn;
	}

	/**
	 * 
	 * @return the name of the strategy being recorded
	 */
	public String getStrategyName() {
		return strategyName;
	}

    /**
     * Prints statistical information (like average guesses and max guesses) about
     * an AI's performance over many Wordle games.
     * 
     * @param stats the stats to print
     */
    public static void printResult(AIStatistics stats) {
        DecimalFormat formatter = new DecimalFormat("0.000");
        if (stats.getFailures() > 0)
            System.out.printf("%-25s %15s guesses%n", stats.getStrategyName() + ":", "Too many");
        else {
            String formattedCount = formatter.format(stats.getAverage());
            // System.out.printf("%-25s %15s guesses %n", guesserName + ":",
            // formattedCount);
            System.out.printf("%-25s %15s guesses %5d/%d won   max %d%n", stats.getStrategyName() + ":", formattedCount,
                    stats.getWins(), stats.getNumGames(), stats.getMaxGuesses());
        }
    }

    
    /**
     * Prints a histogram showing for each number of guesses how many 
     * percent of the games was solved with that number of guesses
     * @param stats
     */
    public static void printHistogram(AIStatistics stats) {
    	if(stats.getNumGames()==0)
    		return;
    	System.out.println("Histogram for "+stats.getStrategyName());
    	int n = stats.getNumGames();
    	for(int i=1; i<=6; i++) {
    		int p = stats.getDistribution().getOrDefault(i,0)*100/n;
    		System.out.println(i+": "+"=".repeat(p));
    	}
    }
}
