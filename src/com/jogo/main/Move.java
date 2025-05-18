package com.jogo.main;

public class Move {
	
	double damage, stamina, magic;
	String effect;
	
	
	public Move(String name) {
		if(name == "Soco") {
			damage = 1.5*Game.player.attack;
		}
	}
	
}
