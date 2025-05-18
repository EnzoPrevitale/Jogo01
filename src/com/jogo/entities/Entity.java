package com.jogo.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.jogo.main.Game;
import com.jogo.world.Camera;
import com.jogo.world.Node;
import com.jogo.world.Vector2i;

public class Entity {

	public static BufferedImage AMMO_EN = Game.spritesheet.getSprite(0, 32, 32, 32);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(160, 32, 32, 32);
	public static BufferedImage ENEMYFEEDBACK_EN = Game.spritesheet.getSprite(0, 224, 32, 32);
	public static BufferedImage HEART_EN = Game.spritesheet.getSprite(32, 32, 32, 32);
	public static BufferedImage REVOLVER_EN = Game.spritesheet.getSprite(64,32,32,32);
	public static BufferedImage REVOLVERHOLDRIGHT_EN = Game.spritesheet.getSprite(96, 32, 32, 32);
	public static BufferedImage REVOLVERHOLDLEFT_EN = Game.spritesheet.getSprite(128, 32, 32, 32);
	public static BufferedImage ASSAULTRIFLE_EN = Game.spritesheet.getSprite(32, 128, 32, 32);
	public static BufferedImage MINIASSAULTRIFLE_EN = Game.spritesheet.getSprite(32, 96, 23, 23);
	public static BufferedImage APPLE_EN = Game.spritesheet.getSprite(416, 128, 16, 16);
	public static BufferedImage GREENAPPLE_EN = Game.spritesheet.getSprite(432, 128, 16, 16);
	public static BufferedImage GOLDENAPPLE_EN = Game.spritesheet.getSprite(416, 144, 16, 16);
	public static BufferedImage SHOTGUN_EN = Game.spritesheet.getSprite(448, 128, 32, 32);
	
	public static BufferedImage BLUEFRAME_UI = Game.spritesheet.getSprite(128, 128, 32, 32);
	public static BufferedImage SAVE_UI = Game.spritesheet.getSprite(416, 96, 32, 32);
	
	public double x;
	public double y;
	protected int z;
	protected int width;
	protected int height;
	
	protected List<Node> path;
	
	public static int depth;
	
	private BufferedImage sprite;
	
	protected int maskx,masky,mwidth,mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public static Comparator<Entity> depthSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity e0, Entity e1) {
			if(e1.depth < e0.depth) return +1;
			if(e1.depth > e1.depth) return -1;
			return 0;
		}
	};
	
	public void tick() {
		depth = 0;
		
	}
	
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY() + e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY() + e2.masky,e2.mwidth,e2.mheight);
		
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}
		return false;
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext+maskx,ynext+masky,mwidth,mheight);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,mwidth,mheight);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	public void followPath(List<Node> path, double speed, Enemy enemy) {
		if(path != null && path.size()>0) {
				Vector2i target = path.get(path.size()-1).tile;
				if(x < target.x*32) {
					x+=speed;
				}
				else if(x > target.x*32) {
					x-=speed;
				}
				if(y < target.y*32) {
					y+=speed;
				}
				else if(y > target.y*32) {
					y-=speed;
				}
				if(x == target.x*32 && y == target.y*32) {
					path.remove(path.size() - 1);
					
				}
			}
		}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
}
