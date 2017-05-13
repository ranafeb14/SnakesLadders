package com.rana.game.snakesLadders;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {

	public static Integer getRandomNumber(Integer min, Integer max){

		return  ThreadLocalRandom.current().nextInt(min, max+1);


	}
}
