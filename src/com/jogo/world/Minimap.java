package com.jogo.world;

public class Minimap {
	
	public static int x;
	public static int y;
	
	public static int clamp(int current,int min, int max) {
		if(current < min) {
			current = min;
		}
		
		if(current > max) {
			current = max;
		}
		
		return current;
	}
}
