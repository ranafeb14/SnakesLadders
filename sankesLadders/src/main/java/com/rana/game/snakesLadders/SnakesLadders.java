package com.rana.game.snakesLadders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;


public class SnakesLadders implements Runnable
{
	private static Map<String, String> ladderMap;
	private static Map<String, String> snakeMap;
	private static AtomicBoolean userWon = new AtomicBoolean(false);
	private static Object lock = new Object();
	
	static{
		Properties prop;
		try {
			prop = new Properties();
			prop.load(SnakesLadders.class.getResourceAsStream("ladder.properties"));
			
			ladderMap = new HashMap(prop);
			prop = new Properties();
			
			prop.load(SnakesLadders.class.getResourceAsStream("ladder.properties"));
			snakeMap = new HashMap(prop);
			System.out.println("Ladder Positions :"+ladderMap);
			System.out.println("Snakes Positions :"+snakeMap);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGame()
	{
		int currentPosition = 0;
		int randomNum = 0;

		String name = Thread.currentThread().getName();
		synchronized (lock) {

			while(currentPosition < 100 && !userWon.get()) {
				try {
					System.out.println("User "+name+" current position: "+currentPosition);
					randomNum = playDice();
					currentPosition = (currentPosition+randomNum > 100? currentPosition: currentPosition+randomNum);
					//currentPosition = 1;
					String currPosStr = String.valueOf(currentPosition);
					if(snakeMap.containsKey(currPosStr)) {
						System.err.println("Oops!! Snake bitten for User: "+name +" currentPosition: "+currentPosition);
						currentPosition = Integer.parseInt(snakeMap.get(currPosStr).toString());
					}
					else if(ladderMap.containsKey(currPosStr)) {
						System.err.println("Hurray got a ladder: "+name +" currentPosition: "+currentPosition);
						currentPosition = Integer.parseInt(ladderMap.get(currPosStr).toString());
					}
					System.out.println("User "+name+" updated position: "+currentPosition);
					if(currentPosition == 100) {
						userWon.set(true);
						System.out.println("====================================================================");
						System.out.println("User " +name+" wins");
						System.out.println("====================================================================");
						lock.notifyAll();
					} else {
						lock.notifyAll();
						lock.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}


	private static Integer playDice(){
		Integer randomNumber = NumberUtils.getRandomNumber(1, 6);
		System.out.println("Playing Dice for user "+Thread.currentThread().getName()+" dice value : "+randomNumber);
		return randomNumber;
	}

	public void run() {
		startGame();

	}
}
