package com.jogo.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.jogo.main.Game;
import com.jogo.main.Sound;
import com.jogo.world.AStar;
import com.jogo.world.Camera;
import com.jogo.world.Node;
import com.jogo.world.Vector2i;
import com.jogo.world.World;

public class Enemy extends Entity{
	
	private double speed = 1;

	private int frames = 0,maxFrames = 13,index = 0,maxIndex = 1;
	private int maskx,masky,maskw,maskh;
	
	private boolean moved = false;
	
	public static boolean back = false;
	
	private boolean isDamaged = false;
	private int damagedFrames = 0;
	
	public BufferedImage enemyfront = Game.spritesheet.getSprite(160, 32, 32, 32);
	private BufferedImage enemyback = Game.spritesheet.getSprite(192, 32, 32, 32);
	
	private BufferedImage enemyfrontdamage = Game.spritesheet.getSprite(0, 224, 32, 32);
	private BufferedImage enemybackdamage = Game.spritesheet.getSprite(32, 224, 32, 32);
	
	public int health = 75;
	
	public int enemyID;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
	}

	public void tick() {
		depth = 9;
		
		
		
		enemyID = Game.enemies.indexOf(this);
		
		if(distance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY())<170) {
			if(!isCollidingWithPlayer()) {
				if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
					Vector2i end = new Vector2i((int)(Game.player.x/32), (int)(Game.player.y/32));
					path = AStar.findPath(Game.world, start, end);
					}
		}else 
			if(Game.rand.nextInt(100) < 10) {
				//Início da batalha
				Game.battle = true;
				Game.enemyID = enemyID;
			}
			if(Game.rand.nextInt(100) < 97) followPath(path,speed,this);
			if(Game.rand.nextInt(100) < 10) {
				Vector2i start = new Vector2i((int)(x/32), (int)(y/32));
				Vector2i end = new Vector2i((int)(Game.player.x/32), (int)(Game.player.y/32));
				path = AStar.findPath(Game.world, start, end);
			}
	}
		maskx = -16;
		masky = -16;
		maskw = 64;
		maskh = 64;
		
		/*
		if(this.distance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY())<160) {
		if (this.isCollidingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColliding((int)(x+speed), this.getY())) {
			x+=speed;
			back=false;
		}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())
				&& !isColliding((int)(x-speed), this.getY())) {
			x-=speed;
			back=false;
		}
		if((int)y<Game.player.getY()&& World.isFree(this.getX(), (int)(y+speed))
				&& !isColliding(this.getX(), (int)(y+speed))) {
			y+=speed;
			back=false;
		}else if((int)y > Game.player.getY()&& World.isFree(this.getX(), (int)(y-speed))
				&& !isColliding(this.getX(), (int)(y-speed))) {
			y-=speed;
		 	back=true;
			}
		}else {
			if(Game.rand.nextInt(100) < 10) {	
				Game.player.health -= Game.rand.nextInt(10);
				Game.player.isDamaged = true;
				Sound.hurt.play();
			}
		}
		}*/
		
		
			
			collisionBullet();
			
			if(isDamaged) {
				damagedFrames++;
				if(damagedFrames >= 5) {
					damagedFrames = 0;
					isDamaged = false;
				}
			}
			
			if(health <= 0) {
				destroy();
				Game.player.xp += 10;
				return;
			}
			}
	
		public void destroy() {
			Game.enemies.remove(this);
			Game.entities.remove(this);
		}
	
		public void collisionBullet() {
			for(int i = 0; i < Game.bullets.size(); i++ ) {
				Entity e = Game.bullets.get(i);
				if(Entity.isColliding(this, e)) {
						health -= Game.player.revolverDamage*Game.player.attack/10;
						Game.bullets .remove(i);
						isDamaged = true;
						return;	
				}
			}
		
		}
	
		public boolean isCollidingWithPlayer() {
			Rectangle enemyCurrent = new Rectangle(this.getX()+maskx,this.getY()+masky,maskw,maskh);
			Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),32,32);
			return enemyCurrent.intersects(player);
		}
	
		
		
	public void render(Graphics g) {
		super.render(g);
		
		/*
		 //Teste de Colisão
		g.setColor(Color.blue);
		g.fillRect(getX()+maskx-Camera.x,getY()+masky-Camera.y,maskw,maskh);
		*/
		if(!isDamaged) {
		g.drawImage(this.enemyfront,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(isDamaged) {
		g.drawImage(this.enemyfrontdamage,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(back && !isDamaged) {
		g.drawImage(this.enemyback,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(back && isDamaged) {
			g.drawImage(this.enemybackdamage,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
}

