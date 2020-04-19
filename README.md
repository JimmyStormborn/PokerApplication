# PokerApplication
## Overview

Console based java poker application.

Play poker through windows command prompt against AI opponents.

A seperate goal is to find out the probability each possible poker hand has to win if no player folds.

To write this code I used Java 8 1.8.0_191 and the NetBeans 8.2 IDE. 

## Installation:

### For Windows
  1. Ensure you have the latest version of java installed, atleast java 1.8.0.
  2. Download the build folder from the project.
  3. Open command prompt
  4. To run the program type "java -jar 'path/build/PokerApp.jar'", where path = the path name for where you downloaded the build folder.
  5. To play a game, enter the command 'p', every other command will not work except play and quit, unless you are me.
  
## How it Works
To find the probabilities it runs the program like normal except it has three computer players who always call. It finds all the possible hands you can have in poker starting from 2S2C to ASAH, it only checks the hands with a spade because it doesn't need to check for each suit since it won't have an impact on the probabilities and so we have less hands to check to make the process more efficient and the data easier to understand.

Next it runs the poker round for each hand to find out if the hand wins. The player who is given the hand is randomised and the dealer is randomised to decrease biases. The round returns whether the hand wins or not, if it wins it adds it to the hand's total number of wins. It repeats this 1000 times to find out the probability for the hand, the more times it does this the more accurate the data is.

After each it has found the probability of a hand, it writes it to a text file for storage. From here I take the data and put it into a speadsheet to be able to visualise the data.

There is some margin of error, since the randomness is sudo random it doesn't represent the randomness of a poker game accurately, therefore the probabilities are probably slightly higher or lower than a real game. 
  
## Goal
The goal of this program is to find out likelihood each hand has to win where every player plays till the end of the round. To find the probabilities of each hand would help a player decide how good theie hand is before the flop.

## Hypothesis
Each pair should have a better chance of winning and the higher the card value the better chance of winning. Also when a player has cards of the same suit they should have a slightly better chance of winning.
  
## Graph of the probabilities
![Probability Graph](./Probabilities_Graph.png)

## Explaining the Data
The worst starting hand is a 2 and a 3, which has around 24% chance of winning. The best starting hand is two Aces, which has around 80% chance of winning. 

There is a large increase in the chance of winning when the player has a pair in their starting hand, around 20% higher for each hand. This is because they start with a hand better than a high card, also they have a higher chance to get a three of a kind or a full house.

The probability of a hand winning increases as the value of the cards go up as the value of the cards go up. This is just common sence since you would have a better chance of winning if you had good cards.

There are also small spikes when a hand has two cards with the same suit, about a 5% increase. This is because the player has a better chance of having a flush draw.

You should put a bet in if you have a pair in your hand and you should not bet much money in if both of your cards are less than a 10, however if you have cards with the same suit then you may think about betting more money than otherwise. I didn't see any obvious increase for a straight draw, most likely because it is not a big enough increase in winning than having better cards.

### Other packages used
JSON Simple https://code.google.com/archive/p/json-simple/ 

Made by James Bird-Sycamore
Last Updated: 20/04/2020
