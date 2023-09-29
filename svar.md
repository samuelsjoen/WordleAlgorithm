# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k^2)
    * *matchWord begins with several minor operations which yield a time complexity of O(1). The first notable operation is the creation of a hashmap of all chars in answer and the amount of times the appear in the word with the method createCharMap. This method loops over all chars in the word. This equals to a time complexity O(k) where k is the number of chars in the word. Subsequently theres one more operation with time complexity O(1) before we appear at another for loop which goes over all chars in the guess word and in the worst case scenario, for each char it loops over all chars in answer word with the method getAnswerType(). This results in a time complexity O(k^2) since both words have the number of chars k. The final time complexity for matchWord will therefore be the largest between several O(1), O(k) and O(k^2), which is O(k^2).*

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O((m^2)*(k^2))
    * *eliminateWords iterates over all words in possibleAnswers and uses the method isPossibleWord for each word to check if the word is possible before removing said word from the list if it is not, with the method remove. isPossibleWord uses matchWord and therefore has the time complexity O(k^2). The remove method has a time complexity of O(m) where m is all the words in possibleAnswers. Finally the outer loop through possibleAnswers also yields a time complexity of O(m). So in conclusion we have a loop O(m), where within we have a loop O(k^2) where within we have in the worst case scenario another loop O(m). This finally yields a total time complexity of O(m^2*k^2)* 

## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O((m^2)*(k^2))
    * *makeGuess starts by using the eliminateWords method from WordleWordList which has the time complexity of O(m^2*k^2). It then runs an operation to get possibleWords which has a time complexity of O(1). Finally it runs the method AIUtils.getBestWord on possibleAnswers. 
    
    *The first notable operation in getBestWord is a call to makeHashMap with possibleAnswers. makeHashMap iterates through every word in possibleAnswers and in a nested loop iterates through every character in each word. The nested loop has a time complexity of O(k) and the outer loop a time complexity of O(m), therefore the total time complexity for makeHashMap is O(m*k). The next notable operation in getBestWord is a for loop iterating through all words in possibleAnswers. This again has the time complexity O(m). Within this loop is a call to the method getScore which iterates through every character in each word. Within this loop there is also a use of the method contains on a list of used chars which in the worst case scenario will have the length k where k is the amount of chars in a word. This results in a time complexity of O(k^2), which means the total time complexity for this operation is O(m*k^2).* 
    
    *So within getBestWord there are several operations O(1), one with O(m*k) and finally one with O(m*k^2). The largest between these is O(m*k^2) which means the final time complexity of getBestWord is O(m*k^2). However this is dwarfed by the eliminateWords method, meaning the final time complexity of makeGuess will be O(m*2*k^2)*



# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

*In FrequencyStrategy, the method getBestWord finds the word with the highest score from all words in the list of possible words. The score is based on the regularity of each character that appears in all words. For example the chars 'a' or 'e' will have very high scores, and chars like 'x' or 'z' will have very low score. To avoid characters appearing too many times in the same word, the score is halved if the character is repeated. For example the character 'e' will have a very high score, meaning without halving the score, the AI would favor words like "eerie" which isn't the most ideal word due to the amount of repeat characters.*

*With my strategy I decided essentially to slightly improve this by using the index of each character as well. So the method getBetterBestWord finds the word with highest score from all words in the list of possible words. The score is again based on regularity of apperance, but closer confined to each index. For example the char 'a' will have a higher score at say index 1 than 0. This shaves off a bit of average at the expense of a longer runtime due to the increased complexity of making the hashmap.*

*Also with my strategy, in case the answer has not been found by the third guess, the subsequent guesses will be as unique as possible compared to the previous guess. This way, the list of possible words is reduced as much as possible due to testing more characters, narrowing it down for the fifth and sixth guess. This seems to cut down the average slightly. (Initially I tried using the indexed approach to get the word for this part of the strategy, but found that the original approach from FrequencyStrategy worked better. This seems to be the case because the indexed approach might put too many restraints on the final word, so that unique characters are not as well achieved.) At first I tried doing this on the second guess instead, so that by the third guess the most amount of characters would have been guessed, so that the position would be better for the third guess. However this sacrifices the chance of getting it in two guesses slightly, which ends up hurting the average to the point that the improved third guess doesn't improve the average. To clarify, the guessed 4 and on will still be possible words and therefore will often be the correct word, however they might have a lower score at the sacrifice of more unique characters.*

*So in conclusion my strategy goes through every word in the list of possible words, assigns a score to each character at each index and returns the word with the best score. Finally if the word has not been found by the third guess, the fourth will be as unique as possible, reducing the size of the list of possible words for a better chance at the fifth and sixth guess. In total these two improvements seems to cut the average from FrequencyStrategy down to 3.475 from 3.785. Since this approach is very general and not specific to the word list possibleAnswers, it should in theory work well on different lists*
