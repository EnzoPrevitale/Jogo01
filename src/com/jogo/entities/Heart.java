package com.jogo.entities;

import java.awt.image.BufferedImage;

public class Heart extends Entity {

	public Heart(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth = 0;
	}

}
