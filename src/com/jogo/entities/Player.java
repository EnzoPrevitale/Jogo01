package com.jogo.entities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogo.graphics.Spritesheet;
import com.jogo.graphics.UI;
import com.jogo.main.Game;
import com.jogo.main.Inventory;
import com.jogo.main.Sound;
import com.jogo.main.Sound.Clips;
import com.jogo.world.Camera;
import com.jogo.world.Minimap;
import com.jogo.world.World;

public class Player extends Entity{

	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	public int right_dir = 3, left_dir = 4, up_dir = 2, down_dir = 1;
	public int dir = down_dir;
	
	public Inventory inventory;
	
	
	public boolean sprint = false;
	
	public int bonusPoints = 10;
	public int points = 50;
	public static double health = 10, maxHealth = 10, maxMaxHealth = 100;
	public double baseSpeed = 10;
	public double attack = 10;
	public double defense = 10;
	public double magic = 10;
	
	public double speed = baseSpeed;
	
	public boolean isDamaged = false;
	private int damagedFrames = 0;
	
	private int frames = 0,maxFrames = 13,index = 0,maxIndex = 1;
	private boolean moved = false;
	
	private int shootFrames = 0;
	public boolean bulletLight = false;
	
	private int canShootFrames = 0;
	
	public boolean[] with = {false,false,false};
	public boolean obtained38 = false;
	public int obtRevolver = 0;
	
	public boolean withAssaultRifle = false;
	
	public int currentSlot = 0;
	public int[] fastSlotID = {0,0,0};

	public boolean iPressed = false;
	public boolean inventoryOpen = false;
	
	private boolean post38 = false;
	
	public double revolverDamage = 25*(attack/10);
	
	private boolean canShoot = true;
	
	private int ww = 0;
	private int zz = 0;
	private int zzz = 0;
	private int aa = 0;
	private boolean h = false;
	private boolean alreadyHas;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] rightPlayer38;
	private BufferedImage[] leftPlayer38;
	private BufferedImage[] downPlayer38;
	private BufferedImage[] upPlayer38;
	
	private BufferedImage[] rightPlayerShoot;
	private BufferedImage[] leftPlayerShoot;
	private BufferedImage[] downPlayerShoot;
	
	private BufferedImage[] damagedPlayerRight;
	private BufferedImage[] damagedPlayerLeft;
	private BufferedImage[] damagedPlayerUp;
	private BufferedImage[] damagedPlayerDown;
	
	private BufferedImage[] damagedPlayerRight38;
	private BufferedImage[] damagedPlayerLeft38;
	private BufferedImage[] damagedPlayerUp38;
	private BufferedImage[] damagedPlayerDown38;
	
	private BufferedImage[] damagedPlayerRightShoot;
	private BufferedImage[] damagedPlayerLeftShoot;
	private BufferedImage[] damagedPlayerDownShoot;
	
	private BufferedImage[] rightPlayerAssaultRifle;
	private BufferedImage[] leftPlayerAssaultRifle;
	private BufferedImage[] downPlayerAssaultRifle;
	private BufferedImage[] upPlayerAssaultRifle;
	
	private BufferedImage[] rightPlayerAssaultRifleShoot;
	private BufferedImage[] leftPlayerAssaultRifleShoot;
	private BufferedImage[] downPlayerAssaultRifleShoot;
	
	private BufferedImage[] damagedRightPlayerAssaultRifleShoot;
	private BufferedImage[] damagedLeftPlayerAssaultRifleShoot;
	private BufferedImage[] damagedDownPlayerAssaultRifleShoot;
	
	private BufferedImage[] damagedRightPlayerAssaultRifle;
	private BufferedImage[] damagedLeftPlayerAssaultRifle;
	private BufferedImage[] damagedDownPlayerAssaultRifle;
	private BufferedImage[] damagedUpPlayerAssaultRifle;
	
	private boolean rPressed = false;
	private int reloadFrames = 0;
	public boolean isReloading = false;
	
	public int ammo[] = {6,30};
	/*public int revolverAmmo = 6;
	public int assaultAmmo = 30;*/
	public int revolverAmmoLeft = 0;
	
	public boolean shoot = false;
	public boolean mouseShoot = false;
	
	public boolean assaultShoot = false;
	public boolean canAssaultShoot = true;
	public boolean postAssaultShoot = false;
	public int canAssaultShootFrames = 0;
	public int assaultShootFrames = 0;
	
	public boolean rightButtonPressed;
	
	public int mx, my;
	public double mxA, myA;
	
	public boolean jump = false;	
	public boolean isJumping = false;	
	public int z = 0;	
	public int jumpFrames = 30, jumpCur = 0;
	public boolean jumpUp = false, jumpDown = false;
	public final int jumpSpd = 2;
	public boolean canJump = true;
	public boolean afterJump = false;
	
	public static int xp = 0;
	public static int level = 1;
	public static int levelUpXp = 90 + 10*level;
	
	public static boolean battle = false;
	public static String[] move = new String[4];
	
	private Graphics g;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		move[0] = "0";
		move[1] = "1";
		move[2] = "2";
		move[3] = "3";
		
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		upPlayer = new BufferedImage[2];
		downPlayer = new BufferedImage[2];
		
		rightPlayer38 = new BufferedImage[2];
		leftPlayer38 = new BufferedImage[2];
		downPlayer38 = new BufferedImage[2];
		upPlayer38 = new BufferedImage[2];
		
		rightPlayerAssaultRifle = new BufferedImage[2];
		leftPlayerAssaultRifle = new BufferedImage[2];
		downPlayerAssaultRifle = new BufferedImage[2];
		upPlayerAssaultRifle = new BufferedImage[2];
		
		rightPlayerAssaultRifleShoot = new BufferedImage[2];
		leftPlayerAssaultRifleShoot = new BufferedImage[2];
		downPlayerAssaultRifleShoot = new BufferedImage[2];
		
		damagedRightPlayerAssaultRifleShoot = new BufferedImage[2];
		damagedLeftPlayerAssaultRifleShoot = new BufferedImage[2];
		damagedDownPlayerAssaultRifleShoot = new BufferedImage[2];
		
		rightPlayerShoot = new BufferedImage[2];
		leftPlayerShoot = new BufferedImage[2];
		downPlayerShoot = new BufferedImage[2];
		
		damagedPlayerRight = new BufferedImage[2];
		damagedPlayerLeft = new BufferedImage[2];
		damagedPlayerUp = new BufferedImage[2];
		damagedPlayerDown = new BufferedImage[2];
		
		damagedPlayerRight38 = new BufferedImage[2];
		damagedPlayerLeft38 = new BufferedImage[2];
		damagedPlayerUp38 = new BufferedImage[2];
		damagedPlayerDown38 = new BufferedImage[2];
		
		damagedPlayerRightShoot = new BufferedImage[2];
		damagedPlayerLeftShoot = new BufferedImage[2];
		damagedPlayerDownShoot = new BufferedImage[2];
		
		damagedRightPlayerAssaultRifle = new BufferedImage[2];
		damagedLeftPlayerAssaultRifle = new BufferedImage[2];
		damagedDownPlayerAssaultRifle = new BufferedImage[2];
		damagedUpPlayerAssaultRifle = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(192 + (i*32),0,32,32);
		}
		for(int i = 0; i < 2; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(128 + (i*32),0,32,32);
		}
		for (int i = 0; i < 2; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(64 + (i*32), 0, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 0, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			leftPlayer38[i] = Game.spritesheet.getSprite(128 + (i*32), 64, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			rightPlayer38[i] = Game.spritesheet.getSprite(192 + (i*32), 64, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			downPlayer38[i] = Game.spritesheet.getSprite(64 + (i*32), 64, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			upPlayer38[i] = Game.spritesheet.getSprite(64 + (i*32), 128, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			leftPlayerShoot[i] = Game.spritesheet.getSprite(128 + (i*32), 64 + 32, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			rightPlayerShoot[i] = Game.spritesheet.getSprite(192 + (i*32), 64 + 32, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			downPlayerShoot[i] = Game.spritesheet.getSprite(64 + (i*32), 64 + 32, 32, 32);
		}
		
		
		for(int i = 0; i < 2; i++) {
			damagedPlayerRight38[i] = Game.spritesheet.getSprite(192 + (i*32),192,32,32);
		}
		for(int i = 0; i < 2; i++) {
			damagedPlayerLeft38[i] = Game.spritesheet.getSprite(128 + (i*32),192,32,32);
		}
		for (int i = 0; i < 2; i++) {
			damagedPlayerUp38[i] = Game.spritesheet.getSprite(0 + (i*32), 192, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedPlayerDown38[i] = Game.spritesheet.getSprite(64 + (i*32), 192, 32, 32);
		}
		
		
		for(int i = 0; i < 2; i++) {
			damagedPlayerRightShoot[i] = Game.spritesheet.getSprite(192 + (i*32),224,32,32);
		}
		for(int i = 0; i < 2; i++) {
			damagedPlayerLeftShoot[i] = Game.spritesheet.getSprite(128 + (i*32),224,32,32);
		}
		for (int i = 0; i < 2; i++) {
			damagedPlayerDownShoot[i] = Game.spritesheet.getSprite(64 + (i*32), 224, 32, 32);
		}
		
		
		for(int i = 0; i < 2; i++) {
			damagedPlayerRight[i] = Game.spritesheet.getSprite(192 + (i*32),160,32, 2);
		}
		for(int i = 0; i < 2; i++) {
			damagedPlayerLeft[i] = Game.spritesheet.getSprite(128 + (i*32),160,32,32);
		}
		for (int i = 0; i < 2; i++) {
			damagedPlayerUp[i] = Game.spritesheet.getSprite(64 + (i*32), 160, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedPlayerDown[i] = Game.spritesheet.getSprite(0 + (i*32), 160, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			leftPlayerAssaultRifle[i] = Game.spritesheet.getSprite(192 + i*32, 128, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			rightPlayerAssaultRifle[i] = Game.spritesheet.getSprite(256 + i*32, 128, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			downPlayerAssaultRifle[i] = Game.spritesheet.getSprite(128 + i*32, 128, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			upPlayerAssaultRifle[i] = Game.spritesheet.getSprite(320 + i*32, 128, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			leftPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(320 + i*32, 160, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			rightPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(384 + i*32, 160, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			downPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(256 + i*32, 160, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			damagedLeftPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(320 + i*32, 192, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedRightPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(384 + i*32, 192, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedDownPlayerAssaultRifleShoot[i] = Game.spritesheet.getSprite(256 + i*32, 192, 32, 32);
		}
		
		
		for (int i = 0; i < 2; i++) {
			damagedLeftPlayerAssaultRifle[i] = Game.spritesheet.getSprite(320 + i*32, 224, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedRightPlayerAssaultRifle[i] = Game.spritesheet.getSprite(384 + i*32, 224, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedDownPlayerAssaultRifle[i] = Game.spritesheet.getSprite(256 + i*32, 224, 32, 32);
		}
		for (int i = 0; i < 2; i++) {
			damagedUpPlayerAssaultRifle[i] = Game.spritesheet.getSprite(448 + i*32, 224, 32, 32);
		}
		
		
		inventory = new Inventory();
	}

	public void tick() {
		depth = 10;
		
		
		
		for(int xx = 0; xx < 4; xx++) {
			for(int yy = 0; yy < 4; yy++) {
				if(inventory.slotID[xx][yy] == 0) {
					inventory.maxAmount[xx][yy] = 0;
				}
				
				else if(inventory.slotID[xx][yy] == 1) {
					inventory.maxAmount[xx][yy] = 1;
				}
				
				else if(inventory.slotID[xx][yy] == 2) {
					inventory.maxAmount[xx][yy] = 1;
				}
				
				else if(inventory.slotID[xx][yy] == 3) {
					inventory.maxAmount[xx][yy] = 10;
				}
				
				else if(inventory.slotID[xx][yy] == 4) {
					inventory.maxAmount[xx][yy] = 10;
				}
				
				else if(inventory.slotID[xx][yy] == 5) {
					inventory.maxAmount[xx][yy] = 5;
				}
			}
		}
		
		if(rPressed) {
			rPressed = false;
			if(with[1] && ammo[0] < 6) {
				
			}
		}
		
		
		if(maxHealth > maxMaxHealth) {
			maxHealth = maxMaxHealth;
		}
		
		if(health > maxHealth) {
			health = maxHealth;
		}
		
		for(int n = 0; n < 3; n++) {
		fastSlotID[n] = inventory.slotID[n][0];
		}
		
		if(inventoryOpen) {
			Game.gameState = "INVENTORY";
		}
		
		if(fastSlotID[currentSlot] == 0) {
			with[0] = true;
			with[1] = false;
			with[2] = false;
		}else if(fastSlotID[currentSlot] == 1) {
			with[0] = false;
			with[1] = true;
			with[2] = false;
		}else if(fastSlotID[currentSlot] == 2) {
			with[0] = false;
			with[1] = false;
			with[2] = true;
		}else {
			with[0] = true;
			with[1] = false;
			with[2] = false;
		}
		
		if(jump && canJump) {	
			if(isJumping == false) {
			jump = false;
			isJumping = true;
			jumpUp = true;
			Sound.jump.play();
			}
		}
			if(isJumping) {
					if(jumpUp) {
						jumpCur += jumpSpd;
						
					}else if(jumpDown) {
						jumpCur-=2;
						if(jumpCur <=0) {
							isJumping = false;
							jumpUp = false;
							jumpDown = false;
							
							
						}
					}
					z = jumpCur;
					if(jumpCur >= jumpFrames) {
						jumpUp = false;
						jumpDown = true;
					}
			}
		moved = false;
		
		if(sprint) {
			speed = baseSpeed*1.8;
		}
		else if(!sprint) {
			speed = baseSpeed;
		}
		
  		if(right && World.isFree((int)(x+0.1*speed),this.getY())) {
			moved = true;
			dir = right_dir;
			x += 0.1 * speed;
		}
  		else if(left && World.isFree((int)(x-0.1*speed),this.getY())) {
				moved = true;
				dir = left_dir;
				x -= 0.1 * speed;
			}
		if(up && World.isFree(this.getX(),(int)(y-0.1*speed))) {
			moved = true;
			dir = up_dir;
			y -= 0.1 * speed;
		}else if(down && World.isFree(this.getX(),(int)(y+0.1*speed))) {
			moved = true;
			dir = down_dir;
			y += 0.1 * speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		if(health <= 0) {
			health = 0;
			Game.gameState = "GAME_OVER";
		}
			checkLevelUp();
			checkAmmo();
			checkHeart();
			checkApple();
			checkGreenApple();
			checkGoldenApple();
			check38();
			checkAssaultRifle();
			checkInventory();
			
			if(post38) {
				Game.saveGame = true;
				post38 = false;
			}
			
			if(mouseShoot && with[1] && ammo[0] > 0 && canShoot) {
			
				double angle = (Math.atan2(my - (this.getY() + 16 - Camera.y -z), mx - (this.getX() + 16 - Camera.x)));
				double dx = Math.cos(angle);
				double dy = -Math.sin(angle);
				
				if(this.dir == this.up_dir && angle <= -0.785398 && angle >= -2.35626) {
					shoot(dx,dy);
				}else if(this.dir == left_dir && angle <= -2.35626 && angle >= -3.14159265358979323846 || dir == left_dir && angle >= 2.35626 && angle <= 3.14159265358979323846) {
					shoot(dx,dy);
				}else if(this.dir == this.down_dir && angle >= 0.785398 && angle <= 2.35626) {
					shoot(dx,dy);
				}else if(this.dir == right_dir && angle >= -0.785398 && angle <= 0 || dir == right_dir && angle <= 0.785398 && angle >= 0) {
					shoot(dx,dy);
				}else {
					mouseShoot = false;
				}
			}
			
			

				if(rightButtonPressed && with[2] && ammo[1] > 0) {
					if(assaultShootFrames == 0) {
						double angle1 = ((Math.atan2(myA - (y + 16 - Camera.y), mxA - (x + 16 - Camera.x))));
						double dx1 = Math.cos(angle1);
						double dy1 = -Math.sin(angle1);
						
						if(this.dir == this.up_dir && angle1 <= -0.785398 && angle1 >= -2.35626) {
							assaultShoot(dx1,dy1);
						}else if(this.dir == left_dir && angle1 <= -2.35626 && angle1 >= -3.14159265358979323846 || dir == left_dir && angle1 >= 2.35626 && angle1 <= 3.14159265358979323846) {
							assaultShoot(dx1,dy1);
						}else if(this.dir == this.down_dir && angle1 >= 0.785398 && angle1 <= 2.35626) {
							assaultShoot(dx1,dy1);
						}else if(this.dir == right_dir && angle1 >= -0.785398 && angle1 <= 0 || dir == right_dir && angle1 <= 0.785398 && angle1 >= 0) {
							assaultShoot(dx1,dy1);
						}
					}
					assaultShootFrames++;
					if(assaultShootFrames <= 15) {
						assaultShoot = false;
					} else {
						assaultShoot = true;
						assaultShootFrames = 0;
						if(assaultShoot && with[2] && ammo[1] > 0 && canAssaultShoot) {
							double angle = ((Math.atan2(myA - (y + 16 - Camera.y), mxA - (x + 16 - Camera.x))));
							double dx = Math.cos(angle);
							double dy = -Math.sin(angle);
							
							if(this.dir == this.up_dir && angle <= -0.785398 && angle >= -2.35626) {
								assaultShoot(dx,dy);
							}else if(this.dir == left_dir && angle <= -2.35626 && angle >= -3.14159265358979323846 || dir == left_dir && angle >= 2.35626 && angle <= 3.14159265358979323846) {
								assaultShoot(dx,dy);
							}else if(this.dir == this.down_dir && angle >= 0.785398 && angle <= 2.35626) {
								assaultShoot(dx,dy);
							}else if(this.dir == right_dir && angle >= -0.785398 && angle <= 0 || dir == right_dir && angle <= 0.785398 && angle >= 0) {
								assaultShoot(dx,dy);
							}else {
								assaultShoot = false;
							}
						}
					}
					
				}else {
					assaultShootFrames = 0;
					assaultShoot = false;
				}
	
				if(!canAssaultShoot) {
					assaultShoot = false;
					canAssaultShootFrames++;
					if(canAssaultShootFrames >= 50) {
						canAssaultShoot = true;
						canAssaultShootFrames = 0;
					}
				}
				
			if(bulletLight) {
				shootFrames++;
				if(shootFrames == 5) {
					shootFrames = 0;
					bulletLight = false;
					if(with[1]) ammo[0] -= 1;
					else if(with[2]) ammo[1] -= 1;
				}
				}
			
			if(!canShoot) {
				shoot = false;
				mouseShoot = false;
				canShootFrames++;
				if(canShootFrames >= 50) {
					canShoot = true;
					canShootFrames = 0;
				}
			}
			
			if(isDamaged) {
				damagedFrames++;
				if(damagedFrames >= 5) {
					damagedFrames = 0;
					isDamaged = false;
				}
			}
			
			Minimap.x = Minimap.clamp(this.getX() - 20/2, 0, 20);
			Minimap.y = Minimap.clamp(this.getY() - 20/2, 0, 20);
		
			Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*32 - Game.WIDTH);
			Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*32 - Game.HEIGHT);
	}
	
		public void checkLevelUp() {
			if(xp >= levelUpXp) {
				xp = xp-=levelUpXp;
				level++;
				points += 1;
				Sound.levelUp.play();
				levelUpXp = 90 + 10*level;
			}
		}
	
	public void checkAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Ammo) {
				if(Entity.isColliding(this, e)) {
					Sound.ammoPickup.play();
					ammo[0]+=1;
					if(ammo[0] > 6) {
					ammo[0] = 6;
					revolverAmmoLeft++;
					}
					Game.entities.remove(i);
				}
			}
			
		}
	}
	public void checkHeart() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Heart) {
				if(Entity.isColliding(this, e)) {		
					if(Game.player.health != 100) {
					Sound.heartPickup.play();
					health+=10;
					Game.entities.remove(i);
					if(Game.player.health >= this.maxHealth) {
						Game.player.health = this.maxHealth;
					}
					}
				}
			}
		}
	}

	public void useApple(int x, int y) {
		health += 2;
		if(health >= maxHealth) {
			health = maxHealth;
		}
		
		inventory.amount[x][y]--;
		if(inventory.amount[x][y] < 1) {
			inventory.clear(x, y);
		}
	}
	
	public void useGreenApple(int x, int y) {
		health += 5;
		if(health >= maxHealth) {
			health = maxHealth;
		}
		
		inventory.amount[x][y]--;
		if(inventory.amount[x][y] < 1) {
			inventory.clear(x, y);
		}
	}
	
	public void useGoldenApple(int x, int y) {
		health += 100;
		maxHealth++;
		xp += 50;
		
		inventory.amount[x][y]--;
		if(inventory.amount[x][y] < 1) {
			inventory.clear(x, y);
		}
	}
		
	public void check38() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Gun38) {
				if(Entity.isColliding(this, e)) {
					for(int xx = 0; xx <= 3; xx++) {
						for(int yy = 0; yy <= 3; yy++) {
							if(inventory.slotID[yy][xx] == 0) {
								zz = yy;
								break;
							}
						}
						if(inventory.slotID[zz][xx] == 0) {
							Sound.gunPickup.play();
							inventory.slotID[zz][xx] = 1;
							Game.entities.remove(i);
							inventory.amount[zz][xx]++;
							zz = 0;
							break;
						}
					}
						}
					}
				}
			}
	
	public void checkInventory() {
		for(int xx = 0; xx <= 3; xx++) {
			for(int yy = 0; yy <= 3; yy++) {
				if(inventory.amount[yy][xx] <= 0) {
					inventory.clear(yy, xx);
				}
			}
		}
	}
	
	public void checkApple() {
		boolean v = false;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Apple) {
				
				if(Entity.isColliding(this, e) && !v) {
					
					for(int xx = 0; xx < 4; xx++) {
						for(int yy = 0; yy < 4; yy++) {
							
							if(inventory.slotID[yy][xx] == 3 && inventory.amount[yy][xx] < inventory.maxAmount[yy][xx]) {
								v = true;
								Sound.gunPickup.play();
								inventory.amount[yy][xx]++;
								Game.entities.remove(i);
								break;
							}else {
								continue;
										}
									}
								}
							}
						}
					}
		
					for(int i = 0; i < Game.entities.size(); i++) {
						Entity e = Game.entities.get(i);
						if(e instanceof Apple) {
							if(Entity.isColliding(this, e)) {
								for(int xx = 0; xx <= 3; xx++) {
									for(int yy = 0; yy <= 3; yy++) {
										if(inventory.slotID[yy][xx] == 0 && !v) {
											zz = yy;
											break;
										}
									}
									
									if(inventory.slotID[zz][xx] == 0 && !v) {
										Sound.gunPickup.play();
										inventory.slotID[zz][xx] = 3;
										inventory.amount[zz][xx]++;
										Game.entities.remove(i);
										v = true;
										zz = 0;
										break;
										}
									}
								}
							}
						}
					}
	
	public void checkGreenApple() {
		boolean v = false;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GreenApple) {
				
				if(Entity.isColliding(this, e)) {
					
					for(int xx = 0; xx < 4; xx++) {
						for(int yy = 0; yy < 4; yy++) {
							
							if(inventory.slotID[yy][xx] == 4 && inventory.amount[yy][xx] < inventory.maxAmount[yy][xx] && !v) {
								v = true;
								Sound.gunPickup.play();
								inventory.amount[yy][xx]++;
								Game.entities.remove(i);
								break;
							}
									}
								}
							}
						}
					}
		
					for(int i = 0; i < Game.entities.size(); i++) {
						Entity e = Game.entities.get(i);
						if(e instanceof GreenApple) {
							if(Entity.isColliding(this, e)) {
								for(int xx = 0; xx <= 3; xx++) {
									for(int yy = 0; yy <= 3; yy++) {
										if(inventory.slotID[yy][xx] == 0 && !v) {
											zz = yy;
											break;
										}
									}
									
									if(inventory.slotID[zz][xx] == 0 && !v) {
										Sound.gunPickup.play();
										inventory.slotID[zz][xx] = 4;
										inventory.amount[zz][xx]++;
										Game.entities.remove(i);
										v = true;
										zz = 0;
										break;
										}
									}
								}
							}
						}
			}
	
	public void checkGoldenApple() {
		boolean v = false;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GoldenApple) {
				
				if(Entity.isColliding(this, e)) {
					
					for(int xx = 0; xx < 4; xx++) {
						for(int yy = 0; yy < 4; yy++) {
							
							if(inventory.slotID[yy][xx] == 5 && inventory.amount[yy][xx] < inventory.maxAmount[yy][xx] && !v) {
								v = true;
								Sound.gunPickup.play();
								inventory.amount[yy][xx]++;
								Game.entities.remove(i);
								break;
							}
									}
								}
							}
						}
					}
		
					for(int i = 0; i < Game.entities.size(); i++) {
						Entity e = Game.entities.get(i);
						if(e instanceof GoldenApple) {
							if(Entity.isColliding(this, e)) {
								for(int xx = 0; xx <= 3; xx++) {
									for(int yy = 0; yy <= 3; yy++) {
										if(inventory.slotID[yy][xx] == 0 && !v) {
											zz = yy;
											break;
										}
									}
									
									if(inventory.slotID[zz][xx] == 0 && !v) {
										Sound.gunPickup.play();
										inventory.slotID[zz][xx] = 5;
										inventory.amount[zz][xx]++;
										Game.entities.remove(i);
										v = true;
										zz = 0;
										break;
										}
									}
								}
							}
						}
			}
	
	public void checkAssaultRifle() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof AssaultRifle) {
				if(Entity.isColliding(this, e)) {
					for(int xx = 0; xx <= 3; xx++) {
						for(int yy = 0; yy <= 3; yy++) {
							if(inventory.slotID[yy][xx] == 0) {
								zz = yy;
								break;
							}
						}
						if(inventory.slotID[zz][xx] == 0) {
							Sound.gunPickup.play();
							inventory.slotID[zz][xx] = 2;
							Game.entities.remove(i);
							inventory.amount[zz][xx]++;
							zz = 0;
							break;
						}
					}
						}
					}
				}
			}
	
	public void checkAmmo2() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Gun38) {
				if(Entity.isColliding(this, e)) {
					
						}
					}				
				}
			}
		
	public void shoot(double dx, double dy) {
		Sound.shoot.play();
		mouseShoot= false;
		bulletLight = true;
		canShoot = false;
		
		int px = 0;
		int py = 0;
		if(dir == up_dir && ammo[0] > 0) {
			px = 6;
			py = 16 - z;
		}else if(dir == down_dir && ammo[0] > 0) {
			px = 20;
			py = 17 - z;
		}
		else if(dir == right_dir && ammo[0] > 0) {
			px = 27;
			py = 16 - z;
			
		}else if(dir == left_dir && ammo[0] > 0) {
			px = -1;
			py = 16 - z;
		}
		
		Bullet bullet = new Bullet(this.getX()+px, this.getY()+py, Bullet.width, Bullet.height, null, dx, dy);
		Game.bullets.add(bullet);
	}
	
	public void assaultShoot(double dx, double dy) {
		bulletLight = true;
		
		int px = 0;
		int py = 0;
		if(dir == up_dir && ammo[1] > 0) {
			px = 6;
			py = 16 - z;
		}else if(dir == down_dir && ammo[1] > 0) {
			px = 15;
			py = 17 - z;
		}
		else if(dir == right_dir && ammo[1] > 0) {
			px = 27;
			py = 16 - z;
			
		}else if(dir == left_dir && ammo[1] > 0) {
			px = -1;
			py = 16 - z;
		}
		
		Bullet bullet = new Bullet(this.getX()+px, this.getY()+py, Bullet.width, Bullet.height, null, dx, dy);
		Game.bullets.add(bullet);
		Sound.shoot.play();
	}
	
	public void damage(double damage) {
		Game.player.health -= damage/defense;
		Game.player.isDamaged = true;
		Sound.hurt.play();
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval(this.getX() - Camera.x + 10, this.getY() + 22 - Camera.y, 12, 12);
		}
		
		if(dir == right_dir && with[0] && !isDamaged) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == right_dir && with[0] && isDamaged) {
			g.drawImage(this.damagedPlayerRight[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
		else if(dir == left_dir && with[0] && !isDamaged) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == left_dir && with[0] && isDamaged) {
			g.drawImage(this.damagedPlayerLeft[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
		else if(dir == up_dir) {
			if(with[1] && !isDamaged) {
				g.drawImage(upPlayer38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}else if(dir == up_dir && with[1] && isDamaged) {
				g.drawImage(this.damagedPlayerUp38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}else if(with[0] && !isDamaged) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}else if(dir == up_dir && with[0] && isDamaged) {
				g.drawImage(this.damagedPlayerUp[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}
		}
		else if(dir == down_dir && with[0] && !isDamaged) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[0] && isDamaged) {
			g.drawImage(this.damagedPlayerDown[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
		
		
			if(dir == right_dir && with[1]) {
				if(bulletLight && !isDamaged) {
					g.drawImage(rightPlayerShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
				}else if(dir == right_dir && with[1] && !bulletLight && isDamaged) {
					g.drawImage(this.damagedPlayerRight38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
				}else if(!bulletLight && !isDamaged) {
			g.drawImage(rightPlayer38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}else if(dir == right_dir && with[1] && bulletLight && isDamaged) {
				g.drawImage(this.damagedPlayerRightShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
			}
	}
			
		else if(dir == left_dir && with[1]) {
		if(bulletLight && !isDamaged) {
			g.drawImage(leftPlayerShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == left_dir && with[1] && bulletLight && isDamaged) {
			g.drawImage(this.damagedPlayerLeftShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(!bulletLight && !isDamaged) {
			g.drawImage(leftPlayer38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == left_dir && with[1] && !bulletLight && isDamaged) {
			g.drawImage(this.damagedPlayerLeft38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
	}
		
		else if (dir == down_dir && with[1]){
		if(bulletLight) {
			g.drawImage(downPlayerShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[1] && bulletLight && isDamaged) {
			g.drawImage(this.damagedPlayerDownShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(!bulletLight && !isDamaged) {
			g.drawImage(downPlayer38[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[1] && !bulletLight && isDamaged) {
			g.drawImage(this.damagedPlayerDownShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
	}
			
			
		if(dir == left_dir && with[2] && !isDamaged && !bulletLight) {
			g.drawImage(leftPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == right_dir && with[2] && !bulletLight && !isDamaged) {
			g.drawImage(rightPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[2] && !bulletLight && !isDamaged) {
			g.drawImage(downPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == up_dir && with[2] && !isDamaged && !isDamaged) {
			g.drawImage(upPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == left_dir && with[2] && bulletLight && !isDamaged) {
			g.drawImage(leftPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == right_dir && with[2] && bulletLight && !isDamaged) {
			g.drawImage(rightPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[2] && bulletLight && !isDamaged) {
			g.drawImage(downPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
		
		
		else if(dir == left_dir && with[2] && isDamaged && !bulletLight) {
			g.drawImage(damagedLeftPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == right_dir && with[2] && !bulletLight && isDamaged) {
			g.drawImage(damagedRightPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == up_dir && with[2] && !bulletLight && isDamaged) {
			g.drawImage(damagedUpPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[2] && !bulletLight && isDamaged) {
			g.drawImage(damagedDownPlayerAssaultRifle[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
		
		else if(dir == left_dir && with[2] && bulletLight && isDamaged) {
			g.drawImage(damagedLeftPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == right_dir && with[2] && bulletLight && isDamaged) {
			g.drawImage(damagedRightPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}else if(dir == down_dir && with[2] && bulletLight && isDamaged) {
			g.drawImage(damagedDownPlayerAssaultRifleShoot[index], this.getX() - Camera.x, this.getY() - Camera.y-z, null);
		}
				
}
	
}
