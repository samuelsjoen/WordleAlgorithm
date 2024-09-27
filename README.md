# Wordle Algorithm
By Samuel Sjøen

The following repository contains a Wordle-like game developed by Martin Vatshelle and the teaching team of the course INF102 autumn 2023 at University of Bergen with the exception of the method WordleAnswer::matchWord which I have implemented.

Using the game as a basis I've implemented several AI's to solve the game. This includes:
RandomStrategy: a random strategy AI that simply guesses a random word every time.
EliminateStrategy: a eliminate strategy AI that seeks to eliminate all non possible words based on the feedback we get from our previous guesses. 
FrequencyStrategy: a frequency strategy AI that seeks to guess words based on the highest expected number of green matches among the words that are still possible answers at the given stage of the game.
MyStrategy: an improved version of frequency strategy.

To run the game, run WordleMain. By default this will run the MyStrategy AI, which can be changed to a different AI in the WordleAIController file. If you want to play the game without AI, comment out line 20 in WordleMain and un-comment line 19.
