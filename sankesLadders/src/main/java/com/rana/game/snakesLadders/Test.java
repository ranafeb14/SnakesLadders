package com.rana.game.snakesLadders;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	public static void main(String[] args) {
		 SnakesLadders sn = new SnakesLadders();
		
		/*ExecutorService serv = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) {
			serv.execute(sn);
		}
		serv.shutdown();*/
		
		Thread t1 = new Thread(sn);
		Thread t2 = new Thread(sn);
		
		t1.setName("Alice");
		t2.setName("Bob");
		
		t1.start();
		t2.start();
		
	}
}
