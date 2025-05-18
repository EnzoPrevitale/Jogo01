package com.jogo.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jogo.main.Game;

public class Tile {

	//Floresta Fantasma
	public static BufferedImage TILE_GRASS = Game.spritesheet.getSprite(224, 32, 32, 32);
	public static BufferedImage TILE_FLOWER_1 = Game.spritesheet.getSprite(128, 256, 32, 32);
	public static BufferedImage TILE_FLOWER_2 = Game.spritesheet.getSprite(128, 320, 32, 32);
	public static BufferedImage TILE_FLOWER_3 = Game.spritesheet.getSprite(128, 352, 32, 32);
	public static BufferedImage TILE_FLOWER_4 = Game.spritesheet.getSprite(192, 352, 32, 32);
	
	public static BufferedImage TILE_TREE = Game.spritesheet.getSprite(32, 64, 32, 32);
	public static BufferedImage TILE_APPLE_TREE_1 = Game.spritesheet.getSprite(128, 288, 32, 32);
	public static BufferedImage TILE_APPLE_TREE_2 = Game.spritesheet.getSprite(0, 96, 32, 32);
	public static BufferedImage TILE_APPLE_TREE_3 = Game.spritesheet.getSprite(0, 64, 32, 32);
	public static BufferedImage TILE_GOLDEN_APPLE_TREE = Game.spritesheet.getSprite(160, 352, 32, 32);
	public static BufferedImage TILE_GREEN_APPLE_TREE_1 = Game.spritesheet.getSprite(224, 352, 32, 32);
	public static BufferedImage TILE_GREEN_APPLE_TREE_2 = Game.spritesheet.getSprite(256, 352, 32, 32);
	
	public static BufferedImage TILE_TALL_GRASS = Game.spritesheet.getSprite(0, 128, 32, 32);
	
	public static BufferedImage TILE_WATER = Game.spritesheet.getSprite(192, 288, 32, 32);
	
	public static BufferedImage TILE_WATER_UP_RIGHT = Game.spritesheet.getSprite(224, 256, 32, 32);
	public static BufferedImage TILE_WATER_UP_LEFT = Game.spritesheet.getSprite(160, 256, 32, 32);
	
	public static BufferedImage TILE_WATER_DOWN_RIGHT = Game.spritesheet.getSprite(224, 320, 32, 32);
	public static BufferedImage TILE_WATER_DOWN_LEFT = Game.spritesheet.getSprite(160, 320, 32, 32);
	
	public static BufferedImage TILE_WATER_RIGHT = Game.spritesheet.getSprite(224, 288, 32, 32);
	public static BufferedImage TILE_WATER_LEFT = Game.spritesheet.getSprite(160, 288, 32, 32);
	
	public static BufferedImage TILE_WATER_UP = Game.spritesheet.getSprite(160+32, 256, 32, 32);
	public static BufferedImage TILE_WATER_DOWN = Game.spritesheet.getSprite(160+32, 256+64, 32, 32);
	
	//Cidade Abandonada
	public static BufferedImage TILE_STREET_SIDE = Game.spritesheet.getSprite(288, 32, 32, 32);
	public static BufferedImage TILE_STREET_UP = Game.spritesheet.getSprite(288 + 32, 32, 32, 32);
	
	public static BufferedImage TILE_HALF_STREET_DOWN = Game.spritesheet.getSprite(288, 0, 32, 32);
	public static BufferedImage TILE_HALF_STREET_LEFT = Game.spritesheet.getSprite(256, 32, 32, 32);
	public static BufferedImage TILE_HALF_STREET_RIGHT = Game.spritesheet.getSprite(352, 32, 32, 32);
	public static BufferedImage TILE_HALF_STREET_UP = Game.spritesheet.getSprite(288, 64, 32, 32);
	
	public static BufferedImage TILE_CORNER_STREET_DOWN_RIGHT = Game.spritesheet.getSprite(256, 0, 32, 32);
	public static BufferedImage TILE_CORNER_STREET_DOWN_LEFT = Game.spritesheet.getSprite(352, 0, 32, 32);
	public static BufferedImage TILE_CORNER_STREET_UP_RIGHT = Game.spritesheet.getSprite(256, 64, 32, 32);
	
	public static BufferedImage TILE_SIDEWALK_UP_RIGHT = Game.spritesheet.getSprite(320, 0, 32, 32);
	public static BufferedImage TILE_SIDEWALK_DOWN_LEFT = Game.spritesheet.getSprite(384, 0, 32, 32);
	public static BufferedImage TILE_SIDEWALK_UP_LEFT = Game.spritesheet.getSprite(384, 32, 32, 32);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	 
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
