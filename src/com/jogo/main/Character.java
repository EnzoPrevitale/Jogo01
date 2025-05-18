package com.jogo.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;



public class Character {
	public String[] optionsY = {"HP", "SPD", "ATK", "DFS", "MGC", "OK"};
	public String[] optionsX = {"+", "-"};
	
	public boolean up,down,left,right,enter;
	
	private int currentOptionY = 0;
	private int maxOptionY = optionsY.length - 2;
	
	private int currentOptionX = 0;
	private int maxOptionX = optionsX.length - 1;
	
	public boolean swap = false;
	public boolean newGame = true;

	public void tick() {
		if(swap) {
			swap = false;
			if(!newGame) {
				Game.gameState = "INVENTORY";
			}
		}
		
		if(up) {
			Sound.select.play();
			up = false;
			currentOptionY--;
			if(currentOptionY < 0) {
				currentOptionY = maxOptionY;
			}
		}
		if(down) {
			Sound.select.play();
			down = false;
			currentOptionY++;
			if(currentOptionY > maxOptionY) {
				currentOptionY = 0;
			}
		}
		
		if(left && newGame) {
			Sound.select.play();
			left = false;
			currentOptionX--;
			if(currentOptionX < 0) {
				currentOptionX = maxOptionX;
			}
		}
		
		if(right && newGame) {
			Sound.select.play();
			right = false;
			currentOptionX++;
			if(currentOptionX > maxOptionX) {
				currentOptionX = 0;
			}
		}
		
		if(enter && newGame) {
			enter = false;
			if(optionsX[currentOptionX] == "+" && Game.player.bonusPoints > 0) {
				Sound.pickup.play();
				if(optionsY[currentOptionY] == "HP" && Game.player.maxHealth < 15) {
					Game.player.maxHealth++;
					Game.player.health = Game.player.maxHealth;
					Game.player.bonusPoints--;
				}else if(optionsY[currentOptionY] == "SPD" && Game.player.speed < 15) {
					Game.player.baseSpeed++;
					Game.player.bonusPoints--;
				}
				else if(optionsY[currentOptionY] == "ATK" && Game.player.attack < 15) {
					Game.player.attack++;
					Game.player.bonusPoints--;
				}
				else if(optionsY[currentOptionY] == "DFS" && Game.player.defense < 15) {
					Game.player.defense++;
					Game.player.bonusPoints--;
				}else if(optionsY[currentOptionY] == "MGC" && Game.player.magic < 15) {
					Game.player.magic++;
					Game.player.bonusPoints--;
				}
			}
			
			else if(optionsX[currentOptionX] == "-") {
				if(optionsY[currentOptionY] == "HP" && Game.player.maxHealth > 10) {
					Game.player.maxHealth--;
					Game.player.health = Game.player.maxHealth;
					Game.player.bonusPoints++;
					Sound.pickup.play();
				}else if(optionsY[currentOptionY] == "SPD" && Game.player.baseSpeed > 10) {
					Game.player.baseSpeed--;
					Game.player.bonusPoints++;
					Sound.pickup.play();
				}
				else if(optionsY[currentOptionY] == "ATK" && Game.player.attack > 10) {
					Game.player.attack--;
					Game.player.bonusPoints++;
					Sound.pickup.play();
				}
				else if(optionsY[currentOptionY] == "DFS" && Game.player.defense > 10) {
					Game.player.defense--;
					Game.player.bonusPoints++;
					Sound.pickup.play();
				}else if(optionsY[currentOptionY] == "MGC" && Game.player.magic > 10) {
					Game.player.magic--;
					Game.player.bonusPoints++;
					Sound.pickup.play();
				}
			}
			if(optionsY[currentOptionY] == "OK") {
				newGame = false;
				currentOptionX = 0;
				currentOptionY = 0;
				Game.gameState = "NORMAL";
				Game.menu.pause = false;
			}
		}
			
			
			
			
			else if(enter && !newGame) {
				enter = false;
				if(optionsX[currentOptionX] == "+" && Game.player.points > 0) {
					Sound.pickup.play();
					Game.player.points--;
					if(optionsY[currentOptionY] == "HP") {
						Game.player.maxHealth++;
					}else if(optionsY[currentOptionY] == "SPD") {
						Game.player.baseSpeed++;
					}
					else if(optionsY[currentOptionY] == "ATK") {
						Game.player.attack++;
					}
					else if(optionsY[currentOptionY] == "DFS") {
						Game.player.defense++;
					}else if(optionsY[currentOptionY] == "MGC") {
						Game.player.magic++;
					}
				}
			}
				
				
				
				
			
			
		
		if(Game.player.bonusPoints == 0 && newGame) {
			maxOptionY = optionsY.length - 1;
		}else {
			maxOptionY = optionsY.length - 2;
		}
		
		if(!newGame) {
			currentOptionX = 0;
			if(Game.player.iPressed) {
				Game.player.iPressed = false;
				Game.gameState = "NORMAL";
			}
		}
	}
	
	public void close() {
		currentOptionX = 0;
		currentOptionY = 0;
	}
	
	public void render(Graphics g) {
		
		if(newGame) {
		g.setColor(Color.black);
		g.fillRect(0,0,Game.WIDTH*Game.SCALE,Game.HEIGHT*Game.SCALE);
		
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,36));
		g.drawString("ATRIBUTOS",  50, (Game.HEIGHT*Game.SCALE)/2 - 250);
		
		
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,28));
		g.drawString("PONTOS DE ATRIBUTO: " + Game.player.bonusPoints, 700, (Game.HEIGHT*Game.SCALE)/2 - 250);
		
		/*HP*/
		g.setColor(Color.blue);
		g.drawString("+", 50, (Game.HEIGHT*Game.SCALE)/2 - 150);
		g.drawString("-", 100, (Game.HEIGHT*Game.SCALE)/2 - 150);
		g.setColor(Color.white);
		g.drawString("| Vida (HP) - "+ (int)Game.player.maxHealth, 130, (Game.HEIGHT*Game.SCALE)/2 - 150);
		g.setColor(Color.blue);
		if(optionsY[currentOptionY] == "HP" && optionsX[currentOptionX] == "+") {
			g.drawString(">", 30, (Game.HEIGHT*Game.SCALE)/2 - 150);
		}else if(optionsY[currentOptionY] == "HP" && optionsX[currentOptionX] == "-") {
			g.drawString(">", 80, (Game.HEIGHT*Game.SCALE)/2 - 150);
		}
		
		/*SPD*/
		g.setColor(Color.blue);
		g.drawString("+", 50, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.drawString("-", 100, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.setColor(Color.white);
		g.drawString("| Velocidade (SPD) - "+ (int)Game.player.baseSpeed, 130, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.setColor(Color.blue);
		if(optionsY[currentOptionY] == "SPD" && optionsX[currentOptionX] == "+") {
			g.drawString(">", 30, (Game.HEIGHT*Game.SCALE)/2 - 100);
		}else if(optionsY[currentOptionY] == "SPD" && optionsX[currentOptionX] == "-") {
			g.drawString(">", 80, (Game.HEIGHT*Game.SCALE)/2 - 100);
		}
		
		/*ATK*/
		g.setColor(Color.blue);
		g.drawString("+", 50, (Game.HEIGHT*Game.SCALE)/2 - 50);
		g.drawString("-", 100, (Game.HEIGHT*Game.SCALE)/2 - 50);
		g.setColor(Color.white);
		g.drawString("| Ataque (ATK) - "+ (int)Game.player.attack, 130, (Game.HEIGHT*Game.SCALE)/2 - 50);
		g.setColor(Color.blue);
		if(optionsY[currentOptionY] == "ATK" && optionsX[currentOptionX] == "+") {
			g.drawString(">", 30, (Game.HEIGHT*Game.SCALE)/2 - 50);
		}else if(optionsY[currentOptionY] == "ATK" && optionsX[currentOptionX] == "-") {
			g.drawString(">", 80, (Game.HEIGHT*Game.SCALE)/2 - 50);
		}
		
		/*DFS*/
		g.setColor(Color.blue);
		g.drawString("+", 50, (Game.HEIGHT*Game.SCALE)/2);
		g.drawString("-", 100, (Game.HEIGHT*Game.SCALE)/2);
		g.setColor(Color.white);
		g.drawString("| Defesa (DFS) - "+ (int)Game.player.defense, 130, (Game.HEIGHT*Game.SCALE)/2);
		g.setColor(Color.blue);
		if(optionsY[currentOptionY] == "DFS" && optionsX[currentOptionX] == "+") {
			g.drawString(">", 30, (Game.HEIGHT*Game.SCALE)/2);
		}else if(optionsY[currentOptionY] == "DFS" && optionsX[currentOptionX] == "-") {
			g.drawString(">", 80, (Game.HEIGHT*Game.SCALE)/2);
		}
		
		/*MGC*/
		g.setColor(Color.blue);
		g.drawString("+", 50, (Game.HEIGHT*Game.SCALE)/2 + 50);
		g.drawString("-", 100, (Game.HEIGHT*Game.SCALE)/2 + 50);
		g.setColor(Color.white);
		g.drawString("| Magia (MGC) - "+ (int)Game.player.magic, 130, (Game.HEIGHT*Game.SCALE)/2 + 50);
		g.setColor(Color.blue);
		if(optionsY[currentOptionY] == "MGC" && optionsX[currentOptionX] == "+") {
			g.drawString(">", 30, (Game.HEIGHT*Game.SCALE)/2 + 50);
		}else if(optionsY[currentOptionY] == "MGC" && optionsX[currentOptionX] == "-") {
			g.drawString(">", 80, (Game.HEIGHT*Game.SCALE)/2 + 50);
		}
		
		if(Game.player.bonusPoints == 0) {
			g.setColor(Color.yellow);
			g.drawString("INICIAR JOGO!", 130, (Game.HEIGHT*Game.SCALE)/2 + 200);
			if(optionsY[currentOptionY] == "OK") {
				g.drawString(">", 110, (Game.HEIGHT*Game.SCALE)/2 + 200);
			}
		}
	}
		
		
		
		
		
		else if(!newGame) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(Game.WIDTH*Game.SCALE/2,0,Game.WIDTH*Game.SCALE/2,Game.HEIGHT*Game.SCALE);
			
			g.setColor(new Color(150, 0, 0));
			g.setFont(new Font("arial",Font.BOLD,36));
			g.drawString("INVENTÁRIO",(Game.WIDTH*Game.SCALE)/2+25, 90);
			g.setColor(Color.red);
			g.setFont(new Font("arial",Font.BOLD,46));
			g.drawString("ATRIBUTOS",(Game.WIDTH*Game.SCALE)/2+65*Game.SCALE, 90);
			
			g.setColor(Color.white);
			g.setFont(new Font("arial",Font.BOLD,28));
			g.drawString("PONTOS DE ATRIBUTO: " + Game.player.points, Game.WIDTH*Game.SCALE/2 + 25, 130);
			
			/*HP*/
			g.setColor(new Color(135,206,250));
			g.drawString("+", Game.WIDTH*Game.SCALE/2 + 50, (Game.HEIGHT*Game.SCALE)/2 - 150);
			g.setColor(Color.white);
			g.drawString("| Vida (HP) - " + (int)Game.player.maxHealth, Game.WIDTH*Game.SCALE/2 + 100, (Game.HEIGHT*Game.SCALE)/2 - 150);
			g.setColor(new Color(135,206,250));
			if(optionsY[currentOptionY] == "HP") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 + 30, (Game.HEIGHT*Game.SCALE)/2 - 150);
			}
			
			/*SPD*/
			g.setColor(new Color(135,206,250));
			g.drawString("+", Game.WIDTH*Game.SCALE/2 + 50, (Game.HEIGHT*Game.SCALE)/2 - 100);
			g.setColor(Color.white);
			g.drawString("| Velocidade (SPD) - " + (int)Game.player.baseSpeed, Game.WIDTH*Game.SCALE/2 + 100, (Game.HEIGHT*Game.SCALE)/2 - 100);
			g.setColor(new Color(135,206,250));
			if(optionsY[currentOptionY] == "SPD") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 + 30, (Game.HEIGHT*Game.SCALE)/2 - 100);
			}
			
			/*ATK*/
			g.setColor(new Color(135,206,250));
			g.drawString("+", Game.WIDTH*Game.SCALE/2 + 50, (Game.HEIGHT*Game.SCALE)/2 - 50);
			g.setColor(Color.white);
			g.drawString("| Ataque (ATK) - " + (int)Game.player.attack, Game.WIDTH*Game.SCALE/2 + 100, (Game.HEIGHT*Game.SCALE)/2 - 50);
			g.setColor(new Color(135,206,250));
			if(optionsY[currentOptionY] == "ATK" && optionsX[currentOptionX] == "+") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 + 30, (Game.HEIGHT*Game.SCALE)/2 - 50);
			}
			
			/*DFS*/
			g.setColor(new Color(135,206,250));
			g.drawString("+", Game.WIDTH*Game.SCALE/2 + 50, (Game.HEIGHT*Game.SCALE)/2);
			g.setColor(Color.white);
			g.drawString("| Defesa (DFS) - "+ (int)Game.player.defense, Game.WIDTH*Game.SCALE/2 + 100, (Game.HEIGHT*Game.SCALE)/2);
			g.setColor(new Color(135,206,250));
			if(optionsY[currentOptionY] == "DFS") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 + 30, (Game.HEIGHT*Game.SCALE)/2);
			}
			
			/*MGC*/
			g.setColor(new Color(135,206,250));
			g.drawString("+", Game.WIDTH*Game.SCALE/2 + 50, (Game.HEIGHT*Game.SCALE)/2 + 50);
			g.setColor(Color.white);
			g.drawString("| Magia (MGC) - "+ (int)Game.player.magic, Game.WIDTH*Game.SCALE/2 + 100, (Game.HEIGHT*Game.SCALE)/2 + 50);
			g.setColor(new Color(135,206,250));
			if(optionsY[currentOptionY] == "MGC") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 + 30, (Game.HEIGHT*Game.SCALE)/2 + 50);
			}
		}
	}
	
}
