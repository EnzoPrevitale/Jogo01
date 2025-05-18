package com.jogo.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.jogo.entities.Entity;

public class Inventory {
	private final int scale = Game.SCALE;
	private final int height = Game.HEIGHT*Game.SCALE;
	private final int width = Game.WIDTH*Game.SCALE;
	
	public int curSlotX = 0;
	public int curSlotY = 0;
	
	private final int MAX_SLOT_X = 3;
	private final int MAX_SLOT_Y = 3;
	
	public static int[][] slotID = new int[4][4];
	
	public static int amount[][] = new int[4][4];
	public int maxAmount[][] = new int[4][4];
	
	public boolean e = false;
	
	public boolean optOpen = false;
	public int curOption = 0;
	private int maxOption = 3;
	public String[] options = {"Usar","Mover","Descartar","Cancelar"};
	
	public int transID = 0;
	public int transAmount = 0;
	public boolean move = false;
	public int selSlotX = 0;
	public int selSlotY = 0;
	
	private boolean grnSlot = false;
	private int grnFrames = 0;
	private int maxGrnFrames = 100;
	
	public boolean up,down,left,right;
	public boolean swap = false;
	
	public void tick() {
		if(Game.player.health >= Game.player.maxHealth) {
			Game.player.health = Game.player.maxHealth;
		}
		
		if(swap) {
			swap = false;
			Game.gameState = "CHARACTER";
		}
		
	if(move) {
		grnFrames++;
		if(grnFrames < maxGrnFrames/2) {
			grnSlot = true;
		}else if(grnFrames >= maxGrnFrames/2) {
			grnSlot = false;
		}
		if(grnFrames > maxGrnFrames) {
			grnFrames = 0;
		}
	}
		if(move && e) {
			e = false;
			slotID[selSlotX][selSlotY] = slotID[curSlotX][curSlotY];
			amount[selSlotX][selSlotY] = amount[curSlotX][curSlotY];
			slotID[curSlotX][curSlotY] = transID;
			amount[curSlotX][curSlotY] = transAmount;
			transID = 0;
			selSlotX = 99;
			selSlotY = 99;
			move = false;
			grnFrames = 0;
		}
		if(e) {
		e = false;
		if(!optOpen && slotID[curSlotX][curSlotY] != 0) optOpen = true;
		else if(optOpen) {
		if(options[curOption] == "Usar") {
			optOpen = false;
			if(slotID[curSlotX][curSlotY] == 3) {
				Game.player.useApple(curSlotX, curSlotY);
			}else if(slotID[curSlotX][curSlotY] == 4) {
				Game.player.useGreenApple(curSlotX, curSlotY);
			}else if(slotID[curSlotX][curSlotY] == 5) {
				Game.player.useGoldenApple(curSlotX, curSlotY);
			}
		}else if(options[curOption] == "Mover") {
			transID = slotID[curSlotX][curSlotY];
			transAmount = amount[curSlotX][curSlotY];
			selSlotX = curSlotX;
			selSlotY = curSlotY;
			move = true;
			optOpen = false;
			curOption = 0;
		}else if(options[curOption] == "Descartar") {
			curOption = 0;
			optOpen = false;
			slotID[curSlotX][curSlotY] = 0;
		}else if(options[curOption] == "Cancelar" ) {
			curOption = 0;
			optOpen = false;
		}
		}
		}
		
		
		
		if(!optOpen) {
		if(up) {
			up = false;
			curSlotY--;
			if(curSlotY < 0) {
				curSlotY = MAX_SLOT_Y;
			}
		}
		if(down) {
			down = false;
			curSlotY++;
			if(curSlotY > MAX_SLOT_Y) {
				curSlotY = 0;
			}
		}
		if(right) {
			right = false;
			curSlotX++;
			if(curSlotX > MAX_SLOT_X) {
				curSlotX = 0;
			}
		}
		if(left) {
			left = false;
			curSlotX--;
			if(curSlotX < 0) {
				curSlotX = MAX_SLOT_X;
				}
			}}
			
		
		
			else if(optOpen) {
			if(down) {
				down = false;
				curOption++;
				if(curOption > maxOption) curOption = 0;
			}
			if(up) {
				up = false;
				curOption--;
				if(curOption < 0) curOption = maxOption;
			}
		}
		}
	
	public void clear(int x, int y) {
		slotID[x][y] = 0;
	}
	
	public void render(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(0,0,0,100));
		g.fillRect(width/2,0,width/2,height);
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,46));
		g.drawString("INVENTÁRIO",width/2+25, 90);
		g.setColor(new Color(150, 0, 0));
		g.setFont(new Font("arial",Font.BOLD,36));
		g.drawString("ATRIBUTOS",width/2+65*scale, 90);
		
		for(int xx = 0; xx < 4; xx++) {
			for(int yy = 0; yy < 4; yy++) {
			if(slotID[xx][yy] == 1) {
				g.drawImage(Entity.REVOLVER_EN, width/2+6*scale+xx*29*scale, yy*scale*29+23*scale, 32*4, 32*4, null);
			}else if(slotID[xx][yy] == 2) {
				g.drawImage(Entity.MINIASSAULTRIFLE_EN, width/2+6*scale+xx*29*scale, yy*scale*29+23*scale, 32*3, 32*3, null);
			}else if(slotID[xx][yy] == 3) {
				g.drawImage(Entity.APPLE_EN, width/2+6*scale+xx*29*scale, yy*scale*29+23*scale, 32*3, 32*3, null);
			}else if(slotID[xx][yy] == 4) {
				g.drawImage(Entity.GREENAPPLE_EN, width/2+6*scale+xx*29*scale, yy*scale*29+23*scale, 32*3, 32*3, null);
			}else if(slotID[xx][yy] == 5) {
				g.drawImage(Entity.GOLDENAPPLE_EN, width/2+6*scale+xx*29*scale, yy*scale*29+23*scale, 32*3, 32*3, null);
			}
			}
		}
		
		if(optOpen) {
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,28));
		g.drawString("Usar",width/2-140,230);
		g.drawString("Mover",width/2-140,280);
		g.drawString("Descartar",width/2-140,330);
		g.drawString("Cancelar",width/2-140,380);
		if(curOption == 0) g.drawString(">",width/2-158,230);
		else if(curOption == 1) g.drawString(">",width/2-158,280);
		else if(curOption == 2) g.drawString(">",width/2-158,330);
		else if(curOption == 3) g.drawString(">",width/2-158,380);
		

		if(slotID[curSlotX] [curSlotY] == 1) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Revólver",width/2-140,180);
		}else if(slotID[curSlotX] [curSlotY] == 2) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Rifle de assalto",width/2-208,180);
		}else if(slotID[curSlotX] [curSlotY] == 3) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Maçã",width/2-140,180);
		}else if(slotID[curSlotX] [curSlotY] == 4) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Maçã verde",width/2-160,180);
		}else if(slotID[curSlotX] [curSlotY] == 5) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Maçã dourada",width/2-193,180);
		}
		}
		
		//Slots
		for(int yy = 0; yy < 4; yy++) {
		for(int xx = 0; xx < 4; xx++) {
			g.setColor(Color.white);
			for(int n = 0; n < 6; n++) {
				g.drawRect(width/2+4*scale+xx*29*scale+n, 23*scale+yy*29*scale+n, 22*Game.SCALE, 22*Game.SCALE);
				}
			if(xx < 3 && yy == 0) {
				g.setColor(Color.blue);
				for(int n = 0; n < 6; n++) {
					g.drawRect(width/2+4*scale+xx*29*scale+n, 23*scale+yy*29*scale+n, 22*Game.SCALE, 22*Game.SCALE);
				}
			}
			if(amount[xx][yy] > 1) {
				g.setFont(new Font("arial",Font.BOLD,28));
				g.setColor(Color.white);
				g.drawString(""+amount[xx][yy], (width/2+4*scale+xx*29*scale)+18*scale, (23*scale+yy*29*scale)+21*scale);
			}
		
	}
		
		}
		
		g.setColor(Color.gray);
		for(int n = 0; n < 6; n++) {
			g.drawRect(width/2+4*scale+curSlotX*29*scale+n, 23*scale+curSlotY*29*scale+n, 22*Game.SCALE, 22*Game.SCALE);
		}
		
		g.setColor(new Color(0,255,0));	
		if(grnSlot)for(int n = 0; n < 6; n++) g.drawRect(width/2+4*scale+selSlotX*29*scale+n, 23*scale+selSlotY*29*scale+n, 22*Game.SCALE, 22*Game.SCALE);
	}
}
