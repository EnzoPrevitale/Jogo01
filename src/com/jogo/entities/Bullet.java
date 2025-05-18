package com.jogo.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jogo.main.Game;
import com.jogo.world.Camera;
import com.jogo.world.Tile;
import com.jogo.world.World;

public class Bullet extends Entity{

	private double dx;
	private double dy;
	private double spd = 10;
	public static int width = 5;
	public static int height = 5;
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		depth = 9;
		x+=dx*spd;
		y-=dy*spd;
		
		if(distance(this.getX(),this.getY(),Game.player.getX(),Game.player.getY()) >= Game.WIDTH) {
			Game.entities.remove(this);
			Game.bullets.remove(this);
		}
	}
	
	public void render(Graphics g) {
		if(Game.player.dir == Game.player.right_dir || Game.player.dir == Game.player.left_dir) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		}else if(Game.player.dir == Game.player.up_dir || Game.player.dir == Game.player.down_dir) {
			g.setColor(Color.yellow);
			g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
			}
		
	}
}
