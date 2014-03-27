package edu.ycp.cs496.ghosts.model;
import java.util.Random;

public class Gameplay 
{
	float spawnRate;
	float rareSpawnRate;
	
	int currentScore;
	int playerHighScore;
	
	
	int aStart = 0;
	int aEnd = 4; //increment any time a new ghost is designed and implemented
	Random aRandom = new Random();
	int Min = 0;
	int Max = 4;
	int nextGhost = 0;
			
	
	boolean gameActive = false;
	int value;

	public void incrementSpawnRate(int incrementValue)
	{
		spawnRate = spawnRate + incrementValue;
	}
	
	public void decrementSpawnRate (int decrementValue)
	{
		spawnRate = spawnRate - decrementValue;
	}
	
	/**@author cflinch2
	 * Use this method with caution, it is probably better to use the incrementSpawnRate/decrementSpawnRate methods 
	 * @param newSpawnRate
	 */
	public void setSpawnRate (int newSpawnRate)
	{
		spawnRate = newSpawnRate;
	}
	
	public void incrementRareSpawnRate(int incrementValue)
	{
		rareSpawnRate += incrementValue;
	}
	
	public void decrementRareSpawnRate (int decrementValue)
	{
		rareSpawnRate -= decrementValue;
	}
	
	/**@author cflinch2
	 * Use this method with caution, it is probably better to use the incrementSpawnRate/decrementSpawnRate methods 
	 * @param newSpawnRate
	 */
	public void setRareSpawnRate (int newRareSpawnRate)
	{
		rareSpawnRate = newRareSpawnRate;
	}
	
	public int getCurrentScore()
	{
		return currentScore;
	}
	
	public void incrementCurrentScore(int incrementScoreValue)
	{
		currentScore += incrementScoreValue;
	}
	
	public void decrementCurrentScore(int decrementScoreValue)
	{
		currentScore -= decrementScoreValue;
	}
	
	public void incrementPlayerHighScore(int incrementScoreValue)
	{
		playerHighScore += incrementScoreValue;
	}
	
	/**
	 * Optional use, may not implement
	 */
	public void resetPlayerHighScore()
	{
		playerHighScore = 0;
	}
	

	
	/**@author cflinch2
	 * Selects a random number between predefined Min and Max (inclusive)
	 * 0 = Empty Space
	 * 1 = Red Ghost
	 * 2 = Yellow Ghost
	 * 3 = Stone/Wall
	 * 4 = Rare Ghost
	 * 
	 * If additional ghosts are added, the Min value must be updated in the Gameplay class.
	 * NOTE: Currently does NOT implement Rareity in any capacity
	 */
	public void updateNextGhost()
	{
		
		nextGhost = Min + (int)(Math.random() * ((Max - Min) + 1));
		
		/*
		 * First, get a random number 1 - 100,
		 * If that number is 90-99, a second number is generated, this will determine WHICH rare ghost you will get and can be updated as new ghosts are added
		 * If you get a 100, a wall spawns
		 * If you get a
		 */
	}
	
	public int getNextGhost()
	{
		return nextGhost;
	}
	
	public boolean getGameActive()
	{
		return gameActive;
	}
	
	public void setGameActive()
	{
		gameActive = !gameActive;
	}
	
 
	
}
