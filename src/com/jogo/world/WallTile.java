package com.jogo.world;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class WallTile extends Tile{
	public static Rectangle collisionRect;
	
	public WallTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		collisionRect = new Rectangle(x,y,32,32);
	}
}
