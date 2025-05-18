package com.jogo.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jogo.entities.Enemy;
import com.jogo.entities.Entity;
import com.jogo.entities.Player;

public class Battle {
	
	private int width = Game.WIDTH * Game.SCALE;
	private int height = Game.HEIGHT * Game.SCALE;
	private int scale = Game.SCALE;
	
	private Enemy enemy;
	
	private boolean playerTurn = true;
	private int timer = 0;
	
	public static boolean right, left, up, down, select;
	
	private String[] options = {"Lutar", "Itens"};
	private int curOption;
	private int maxOption = options.length - 1;
	
	private int curMoveX;
	private int curMoveY;
	private int maxMove = 1;
	
	private String[][] move = new String[2][2];
	
	private boolean fight = false;
	
	public Battle(Enemy e) {
		enemy = e;
	}
	
	public void tick() {
		
		move[0][0] = Player.move[0];
		move[0][1] = Player.move[1];
		move[1][0] = Player.move[2];
		move[1][1] = Player.move[3];
		
		//Opções
		
				if(right) {
					Sound.select.play();
					right = false;
					if(!fight) {
					curOption++;
					if(curOption > maxOption) {
						curOption = 0;
					}
					}
					
					else if(fight) {
						curMoveY++;
						if(curMoveY > maxMove) {
							curMoveY = 0;
						}
					}
				}
				
				
				
				if(left) {
					Sound.select.play();
					left = false;
					if(!fight) {
					curOption--;
					if(curOption < 0) {
						curOption = maxOption;
					}
					}
					
					else if(fight) {
						curMoveY--;
						if(curMoveY < 0) {
							curMoveY = maxMove;
						}
					}
				}
				
				
				
				if(up) {
					Sound.select.play();
					up = false;
					
					if(fight) {
						curMoveX--;
						if(curMoveX < 0) {
							curMoveX = maxMove;
						}
					}
				}
				
				
				
				if(down) {
					Sound.select.play();
					down = false;
					
					if(fight) {
						curMoveX++;
						if(curMoveX > maxMove) {
							curMoveX = 0;
						}
					}
				}
				
				
				
				if(select) {
					select = false;
					if(options[curOption] == "Lutar" && !fight) {
						fight = true;
					}
					
					if(fight) {
					//Golpe
					}
				}
				
				
		
		if(enemy.health <= 0) {
			//Inimigo derrotado
			Game.gameState = "NORMAL";
		}
		if(Game.player.health <= 0) {
			//Jogador derrotado
			Game.gameState = "GAME_OVER";
		}
		
		
		
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.fillRect(0, 425, width, height);
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,36));
		g.drawString("BATALHA", 50, 558);
		g.setColor(Color.white);
		g.drawString(""+enemy.health, 50, 50);
		
		if(playerTurn && !fight) {
			//Opções
			
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,30));
			g.drawString("LUTAR", 120*scale, 558);
			g.setFont(new Font("arial",Font.BOLD,30));
			g.drawString("ITENS", 180*scale, 558);
			
			if(options[curOption] == "Lutar") {
				g.drawString(">", 115*scale, 558);
			} else if (options[curOption] == "Itens") {
				g.drawString(">", 175*scale, 558);
			}
		}
		
		else if(playerTurn && fight) {
			//Ataques
			
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,30));
			
			if(Game.player.move[0] != "NONE") g.drawString(Game.player.move[0], 120*scale, 518);
			if(Game.player.move[1] != "NONE") g.drawString(Game.player.move[1], 180*scale, 518);
			if(Game.player.move[2] != "NONE") g.drawString(Game.player.move[2], 120*scale, 588);
			if(Game.player.move[3] != "NONE") g.drawString(Game.player.move[3], 180*scale, 588);
			
			
			if(curMoveX == 0 && curMoveY == 0) {
				g.drawString(">", 115*scale, 518);
			}else if(curMoveX == 0 && curMoveY == 1){
				g.drawString(">", 175*scale, 518);
			}else if(curMoveX == 1 && curMoveY == 0) {
				g.drawString(">", 115*scale, 588);
			}else if(curMoveX == 1 && curMoveY == 1) {
				g.drawString(">", 175*scale, 588);
			}
		}
	}
	
	
	
}
