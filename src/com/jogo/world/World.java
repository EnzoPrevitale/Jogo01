package com.jogo.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jogo.entities.*;
import com.jogo.graphics.Spritesheet;
import com.jogo.main.Game;
import com.jogo.main.Sound;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 32;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int currentPixel = pixels[xx+(yy*map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
					switch(currentPixel)
					{
					case 0xFFFF0000:
						//Inimigo
						Enemy en = new Enemy(xx*32,yy*32,32,32,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					break;
					case 0xFF000000:
						//Chão
						int flower = new Random().nextInt(200);
						if(flower > 10)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
						else if(flower >= 7 && flower <= 10)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_1);
						else if(flower > 4 && flower < 7)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_2);
						else if(flower > 2 && flower <= 4)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_3);
						else if(flower <= 2)
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_4);
					break;
					
					
					case 0xff0081CC:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER);
					break;
					case 0xff005587:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_DOWN_LEFT);
					break;
					case 0xff003D60:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_DOWN_RIGHT);
					break;
					case 0xff001A28:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_UP_RIGHT);
					break;
					case 0xff006AA8:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_LEFT);
					break;
					case 0xff006CAA:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_DOWN);
					break;
					case 0xff006196:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_UP);
					break;
					case 0xff0068A0:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_RIGHT);
					break;
					case 0xff001521:
						//Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*32,yy*32,Tile.TILE_WATER_UP_LEFT);
					break;
					
					
					case 0XFF1D6000:
						//Grama Alta
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_TALL_GRASS);
					break;
					case 0xFFFFFFFF:
						//Parede
						int apple = new Random().nextInt(100);
						int goldenApple = new Random().nextInt(1000000);
						
						if(apple > 30)
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_TREE);
						else if(apple >= 20 && apple <= 30)
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_1);
						else if(apple >= 10 && apple <= 20)
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_2);
						else if(apple > 2 && apple < 10)
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_3);
						else if(apple > 0 && apple <= 2)
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GREEN_APPLE_TREE_1);
						else if(apple == 0) {
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GREEN_APPLE_TREE_2);
						}
						
						if(goldenApple == 1) {
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GOLDEN_APPLE_TREE);
						System.out.println("Uma maçã dourada foi gerada!");
						}
					break;
					case 0xFF2200ff:
					
					
						//Player
						Game.player.setX(xx*32);
						Game.player.setY(yy*32);
						
						/*
						 * 
						WIDTH = 100;
						HEIGHT = 100;
						tiles = new Tile[WIDTH*HEIGHT];
						
						for(int xx = 0; xx < WIDTH; xx++) {
							for(int yy = 0; yy < HEIGHT; yy++) {
								int type = Game.rand.nextInt(100);
								
								if(type <= 20) {
									int apple = new Random().nextInt(100);
									int goldenApple = new Random().nextInt(1000000);
									
									if(apple > 30)
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_TREE);
									else if(apple >= 20 && apple <= 30)
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_1);
									else if(apple >= 10 && apple <= 20)
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_2);
									else if(apple > 2 && apple < 10)
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_APPLE_TREE_3);
									else if(apple > 0 && apple <= 2)
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GREEN_APPLE_TREE_1);
									else if(apple == 0) {
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GREEN_APPLE_TREE_2);
									}
									
									if(goldenApple == 1) {
									tiles[xx + (yy*WIDTH)] = new WallTile(xx*32,yy*32,Tile.TILE_GOLDEN_APPLE_TREE);
									System.out.println("Uma maçã dourada foi gerada!");
									}
								}
								else if (type > 20 && type <= 85){
									int flower = new Random().nextInt(200);
									if(flower > 10)
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									else if(flower >= 7 && flower <= 10)
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_1);
									else if(flower > 4 && flower < 7)
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_2);
									else if(flower > 2 && flower <= 4)
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_3);
									else if(flower <= 2)
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_FLOWER_4);
								}
								else if (type > 85) {
									int entity = Game.rand.nextInt(100);
									
									if(entity == 1) {
									//Inimigo
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Enemy en = new Enemy(xx*32,yy*32,32,32,Entity.ENEMY_EN);
									Game.entities.add(en);
									Game.enemies.add(en);
								}
								else if (entity > 1 && entity <= 10) {
									//Munição
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new Ammo(xx*32,yy*32,32,32,Entity.AMMO_EN));
								}
								else if(entity > 10 && entity <= 20) {
									//Maçã
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new Apple(xx*32+8,yy*32+8,16,16,Entity.APPLE_EN));
								}
								else if(entity > 20 && entity <= 25) {
									//Maçã verde
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new GreenApple(xx*32+8,yy*32+8,16,16,Entity.GREENAPPLE_EN));
								}
								else if(entity == 26) {
									//Maçã dourada
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new GoldenApple(xx*32+8,yy*32+8,16,16,Entity.GOLDENAPPLE_EN));
								}
								else if(entity == 27) {
									//Rifle
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new AssaultRifle(xx*32,yy*32,32,32,Entity.ASSAULTRIFLE_EN));
								}
								else if(entity == 28){
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
									Game.entities.add(new Gun38(xx*32,yy*32,32,32,Entity.REVOLVER_EN));
								}else {
									tiles[xx + (yy*WIDTH)] = new FloorTile(xx*32,yy*32,Tile.TILE_GRASS);
								}
							}
						}
						}
						
						int dir = 0;
						int xx = 0, yy = 0;
						
						for(int n = 0; n < 5; n++) {
						for(int i = 0; i < 200; i++) {
							tiles[xx+yy*WIDTH] = new Tile(xx*32, yy*32, FloorTile.TILE_GRASS);
							if(dir == 0) {
								//direita
								if(xx < WIDTH) {
									xx++;
								}
							}
							
							else if(dir == 1) {
								//esquerda
								if(xx > 0) {
									xx--;
								}
							}
							
							else if(dir == 2) {
								//baixo
								if(yy < HEIGHT) {
									yy++;
								}
							}
							
							else if(dir == 3) {
								//cima
								if(yy > 0) {
									yy--;
								}
							}
							
							if(Game.rand.nextInt(100) < 30) {
								dir = Game.rand.nextInt(4);
							}
							
							
							
						}
						}
						*
						*/
						
					break;
				
					case 0xFFFBFF00:
						//Munição
						Game.entities.add(new Ammo(xx*32,yy*32,32,32,Entity.AMMO_EN));
					break;
					case 0xFF1AFF00:
						//38
						Game.entities.add(new Gun38(xx*32,yy*32,32,32,Entity.REVOLVER_EN));
					break;
					case 0xFFFF8400:
						//Maçã
						Game.entities.add(new Apple(xx*32+8,yy*32+8,16,16,Entity.APPLE_EN));
					break;
					case 0xffBBD3B1:
						//Maçã Verde
						Game.entities.add(new GreenApple(xx*32+8,yy*32+8,16,16,Entity.GREENAPPLE_EN));
					break;
					case 0xffFFF34F:
						//Maçã Dourada
						Game.entities.add(new GoldenApple(xx*32+8,yy*32+8,16,16,Entity.GOLDENAPPLE_EN));
					break;
					case 0XFF9C099:
						//Boss Ceifador
						
					break;
					case 0XFF602828:
						//Rifle
						Game.entities.add(new AssaultRifle(xx*32,yy*32,32,32,Entity.ASSAULTRIFLE_EN));
					break;
					case 0XFFCE5656:
						//Shotgun
						Game.entities.add(new Shotgun(xx*32,yy*32,32,32,Entity.SHOTGUN_EN));
					break;
					
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext/TILE_SIZE;
		int y1 = yNext/TILE_SIZE;
		
		int x2 = (xNext+TILE_SIZE-1)/TILE_SIZE;
		int y2 = yNext/TILE_SIZE;
		
		int x3 = xNext/TILE_SIZE;
		int y3 = (yNext+TILE_SIZE-1)/TILE_SIZE;
		
		int x4 = (xNext+TILE_SIZE-1)/TILE_SIZE;
		int y4 = (yNext+TILE_SIZE-1)/TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)] instanceof WallTile)||
			(tiles[x2+(y2*World.WIDTH)] instanceof WallTile)||
			(tiles[x3+(y3*World.WIDTH)] instanceof WallTile)||
			(tiles[x4+(y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean collision(Rectangle r) {
		if(r.intersects(WallTile.collisionRect)) {
			return true;
		}
		return false;
	}
	
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.bullets.clear();
		
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.bullets = new ArrayList<Bullet>();
		
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0, 0, 32, 32,Game.spritesheet.getSprite(0, 0, 32, 32));
		Game.world = new World("/"+level);
		Game.entities.add(Game.player);
		Game.sound = new Sound();
		return;
	}
	
	public void renderMinimap() {
		int xPlayer = Game.player.getX()/32;
		int yPlayer = Game.player.getY()/32;
		
		for(int n = 0; n < Game.minimapPixels.length; n++) {
			Game.minimapPixels[n] = 0x303030;
		}
		for(int xx = 0; xx < 20; xx++) {
			for(int yy = 0; yy < 20; yy++) {
				if(tiles[xx+yy*20] instanceof WallTile) {
					Game.minimapPixels[xx-Minimap.x+yy*20-Minimap.y] = 0x808080;
				}
				if(tiles[xx+yy*HEIGHT] instanceof WaterTile) {
					Game.minimapPixels[xx-Minimap.x+yy*20-Minimap.y] = 0x0081CC;
				}
			}
		}
		for(int n = 0; n < Game.enemies.size(); n++) {
			int xEnemy = Game.enemies.get(n).getX()/32;	
			int yEnemy = Game.enemies.get(n).getY()/32;
			Game.minimapPixels[(xEnemy-Minimap.x)+(yEnemy*20-Minimap.y)] = 0xff0000;
		}
		Game.minimapPixels[(xPlayer-Minimap.x)+(yPlayer*20-Minimap.y)] = 0x0000ff;
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x/32;
		int ystart = Camera.y/32;
		
		int xfinal = xstart + (Game.WIDTH/32);
		int yfinal = ystart + (Game.HEIGHT/32);
		
		for(int xx = xstart; xx <= xfinal+1; xx++) {
			for(int yy = ystart; yy <= yfinal+1; yy++) {
				if(xx<0||yy<0||xx>=WIDTH||yy>=HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
