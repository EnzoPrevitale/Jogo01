package com.jogo.entities;

import java.awt.image.BufferedImage;

public class Ammo extends Entity {

	public Ammo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth = 0;
	}

}
